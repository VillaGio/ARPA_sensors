library(rgdal)
library(plyr)
library(tidyverse) # tidy data wrangling
library(vroom)     # fats reading/importing csv data
library(sf)        # read spatial data
library(tigris)    # geojoin

setwd(dirname(rstudioapi::getActiveDocumentContext()$path))

#################### shapef: SHAPEFILES FOR POLYGONS ####################

shapef <- st_read("./ProvCM01012022/ProvCM01012022_WGS84.shp")
shapef <- shapef[, c(9,13)]



#################### stationsS: SENSORS STATIONS INFO ####################

stationsS<-read.csv("..../Data engineering/Data/Stations/stationsS.csv")
stationsS$NomeTipoSensore <- as.factor(stationsS$NomeTipoSensore)


#################### sens_aggrDay: AGGREGATE DF ON DATE ONLY FOR PLOT REPRESENTATION OF POLLUTANTS ####################

#### 1) AGGREGATE DF ON DAY AND IDSENSORE

#Base df
dati_day <- setNames(data.frame(matrix(ncol = 7, nrow = 0)), c("idesensore", "dataora", "NomeTipoSensore", "valore", "anno"))


#Single years csv
for(year in 2021:2011){
  path = paste("../../Data engineering/Data/Sensors/modifiedForBulk/mod_", year, ".csv", sep="")
  
  dati <- read.csv(path)
  dati_merged <- merge(dati, stationsS, by.x="idsensore", by.y="IdSensore")
  dati_filtered <- dati_merged[dati_merged$NomeTipoSensore=="Particelle sospese PM2.5" | dati_merged$NomeTipoSensore=="PM10" | 
                                 dati_merged$NomeTipoSensore=="PM10 (SM2005)" | dati_merged$NomeTipoSensore=="Ozono" | dati_merged$NomeTipoSensore=="Biossido di Azoto" |
                                 dati_merged$NomeTipoSensore=="Ossidi di Azoto" | dati_merged$NomeTipoSensore=="Monossido di Carbonio",]
  rm(dati_merged)
  dati_filtered <- dati_filtered[dati_filtered$valore > 0,]
  
  dati_filtered <- dati_filtered %>% mutate(dataora = substr(dataora, 1,10))
  
  dati_grouped <- dati_filtered %>%
    group_by(idsensore, dataora, NomeTipoSensore) %>%
    summarise_at(vars(valore), list(valore = mean))
  
  rm(dati_filtered)
  
  dati_grouped["anno"] = year
  
  dati_grouped <- dati_grouped %>% mutate(NomeTipoSensoreENG =
                                    case_when(NomeTipoSensore == "Particelle sospese PM2.5" ~ "PM2.5",
                                              NomeTipoSensore == "PM10 (SM2005)" ~ "PM10",
                                              NomeTipoSensore == "PM10" ~ "PM10",
                                              NomeTipoSensore == "Ozono" ~ "O3 - Ozone",
                                              NomeTipoSensore == "Biossido di Azoto" ~ "NO2 - Nitrogen Dioxide",
                                              NomeTipoSensore == "Ossidi di Azoto" ~ "NO - Nitric Oxide",
                                              NomeTipoSensore == "Monossido di Carbonio" ~ "CO - Carbon Monoxide"
                                    ))
  
  dati_day <- rbind(dati_day, dati_grouped)
  
  rm(dati_grouped)
  
  print(year)
}


write.csv(dati_day, "./data/dati_daily.csv", row.names = FALSE)


#Multiple years csv
dati_day <- read.csv("./data/dati_daily.csv")

for (year in c(2010, 2007, 2004)){
  
  path = paste("../../Data engineering/Data/Sensors/modifiedForBulk/mod_", year, ".csv", sep="")
  
  dati <- read.csv(path)
  dati_merged <- merge(dati, stationsS, by.x="idsensore", by.y="IdSensore")
  rm(dati)
  dati_filtered <- dati_merged[dati_merged$NomeTipoSensore=="Particelle sospese PM2.5" | dati_merged$NomeTipoSensore=="PM10" | 
                                 dati_merged$NomeTipoSensore=="PM10 (SM2005)" | dati_merged$NomeTipoSensore=="Ozono" | dati_merged$NomeTipoSensore=="Biossido di Azoto" |
                                 dati_merged$NomeTipoSensore=="Ossidi di Azoto" | dati_merged$NomeTipoSensore=="Monossido di Carbonio",]
  
  rm(dati_merged)
  dati_filtered <- dati_filtered[dati_filtered$valore > 0,]
  dati_filtered <- dati_filtered %>% mutate(dataora = substr(dataora, 1,10))
  
  dati_filtered <- dati_filtered %>% mutate (anno = as.numeric(format(as.Date(dati_filtered$dataora, format = "%Y-%m-%d"), format = "%Y")))
  
  dati_grouped <- dati_filtered %>%
    group_by(idsensore, dataora, NomeTipoSensore, anno) %>%
    summarise_at(vars(valore), list(valore = mean))
  
  rm(dati_filtered)
  dati_grouped <- dati_grouped %>% mutate(NomeTipoSensoreENG =
                                            case_when(NomeTipoSensore == "Particelle sospese PM2.5" ~ "PM2.5",
                                                      NomeTipoSensore == "PM10 (SM2005)" ~ "PM10",
                                                      NomeTipoSensore == "PM10" ~ "PM10",
                                                      NomeTipoSensore == "Ozono" ~ "O3 - Ozone",
                                                      NomeTipoSensore == "Biossido di Azoto" ~ "NO2 - Nitrogen Dioxide",
                                                      NomeTipoSensore == "Ossidi di Azoto" ~ "NO - Nitric Oxide",
                                                      NomeTipoSensore == "Monossido di Carbonio" ~ "CO - Carbon Monoxide"
                                            ))

  
  dati_day <- rbind(dati_day, dati_grouped)
  
  print(year)
}


write.csv(dati_day, "./data/dati_daily.csv", row.names = FALSE)



##### 2) AGGREGATE DF ON DATE ONLY FOR PLOT REPRESENTATION OF POLLUTANTS 
dati_day <- read.csv("./data/dati_daily.csv")

sens_aggrDay <- dati_day %>% 
  group_by(dataora, NomeTipoSensoreENG, anno) %>%
  summarise_at(vars(valore), list(valore = mean))

write.csv(sens_aggrDay, "./data/sens_aggrDay.csv", row.names = FALSE)


#################### sensors_map: AGGREGATE DF ON YEAR FOR MAP REPRESENTATION OF POLLUTANTS ####################

#Base df
sensors_map <- setNames(data.frame(matrix(ncol = 6, nrow = 0)), c("idesensore", "Comune", "Provincia", "NomeTipoSensore", "valore", "anno"))


#Single years csv
for(year in 2021:2011){
  path = paste("../../Data engineering/Data/Sensors/modifiedForBulk/mod_", year, ".csv", sep="")
  
  dati <- read.csv(path)
  dati_merged <- merge(dati, stationsS, by.x="idsensore", by.y="IdSensore")
  dati_filtered <- dati_merged[dati_merged$NomeTipoSensore=="Particelle sospese PM2.5" | dati_merged$NomeTipoSensore=="PM10" | 
                                 dati_merged$NomeTipoSensore=="PM10 (SM2005)" | dati_merged$NomeTipoSensore=="Ozono" | dati_merged$NomeTipoSensore=="Biossido di Azoto" |
                                 dati_merged$NomeTipoSensore=="Ossidi di Azoto" | dati_merged$NomeTipoSensore=="Monossido di Carbonio",]
  dati_filtered <- dati_filtered[dati_filtered$valore > 0,]
  
  
  dati_grouped <- dati_filtered %>%
    group_by(idsensore, Comune, Provincia, NomeTipoSensore) %>%
    summarise_at(vars(valore), list(valore = mean))
  
  dati_grouped["anno"] = year
  
  sensors_map <- rbind(sensors_map)
  
  sensors_map <- sensors_map %>% mutate(NomeTipoSensoreENG =
                                    case_when(NomeTipoSensore == "Particelle sospese PM2.5" ~ "PM2.5",
                                              NomeTipoSensore == "PM10 (SM2005)" ~ "PM10",
                                              NomeTipoSensore == "PM10" ~ "PM10",
                                              NomeTipoSensore == "Ozono" ~ "O3 - Ozone",
                                              NomeTipoSensore == "Biossido di Azoto" ~ "NO2 - Nitrogen Dioxide",
                                              NomeTipoSensore == "Ossidi di Azoto" ~ "NO - Nitric Oxide",
                                              NomeTipoSensore == "Monossido di Carbonio" ~ "CO - Carbon Monoxide"
                                    ))
  
  sensors_map <- sensors_map %>% mutate(ProvinciaLong = 
                                    case_when(
                                      Provincia == "BG" ~ "Bergamo",
                                      Provincia == "BS" ~ "Brescia",
                                      Provincia == "CO" ~ "Como",
                                      Provincia == "CR" ~ "Cremona",
                                      Provincia == "LC" ~ "Lecco",
                                      Provincia == "LO" ~ "Lodi",
                                      Provincia == "MN" ~ "Mantova",
                                      Provincia == "MI" ~ "Milano",
                                      Provincia == "MB" ~ "Monza e Brianza",
                                      Provincia == "PV" ~ "Pavia",
                                      Provincia == "SO" ~ "Sondrio",
                                      Provincia == "VA" ~ "Varese"
                                    ))
  
  print(year)
}


write.csv(dati_all, "./data/sensors_map.csv", row.names = FALSE)



#Multiple years csv
dati_all <- read.csv("./data/sensors_map.csv")

for (year in c(2010, 2007, 2004)){
  
  path = paste("../../Data engineering/Data/Sensors/modifiedForBulk/mod_", year, ".csv", sep="")
  
  dati <- read.csv(path)
  dati_merged <- merge(dati, stationsS, by.x="idsensore", by.y="IdSensore")
  rm(dati)
  dati_filtered <- dati_merged[dati_merged$NomeTipoSensore=="Particelle sospese PM2.5" | dati_merged$NomeTipoSensore=="PM10" | 
                                 dati_merged$NomeTipoSensore=="PM10 (SM2005)" | dati_merged$NomeTipoSensore=="Ozono" | dati_merged$NomeTipoSensore=="Biossido di Azoto" |
                                 dati_merged$NomeTipoSensore=="Ossidi di Azoto" | dati_merged$NomeTipoSensore=="Monossido di Carbonio",]
  
  rm(dati_merged)
  dati_filtered <- dati_filtered[dati_filtered$valore > 0,]
  dati_filtered$dataora <- format(as.Date(dati_filtered$dataora, format = "%Y-%m-%d"), format = "%Y")
  
  dati_grouped <- dati_filtered %>%
    group_by(idsensore, dataora, Comune, Provincia, NomeTipoSensore) %>%
    summarise_at(vars(valore), list(valore = mean))
  
  dati_grouped["anno"] = as.numeric(dati_grouped$dataora)
  
  
  dati_grouped <- dati_grouped %>% mutate(NomeTipoSensoreENG =
                                            case_when(NomeTipoSensore == "Particelle sospese PM2.5" ~ "PM2.5",
                                                      NomeTipoSensore == "PM10 (SM2005)" ~ "PM10",
                                                      NomeTipoSensore == "PM10" ~ "PM10",
                                                      NomeTipoSensore == "Ozono" ~ "O3 - Ozone",
                                                      NomeTipoSensore == "Biossido di Azoto" ~ "NO2 - Nitrogen Dioxide",
                                                      NomeTipoSensore == "Ossidi di Azoto" ~ "NO - Nitric Oxide",
                                                      NomeTipoSensore == "Monossido di Carbonio" ~ "CO - Carbon Monoxide"
                                            ))
  
  dati_grouped <- dati_grouped %>% mutate(ProvinciaLong = 
                                            case_when(
                                              Provincia == "BG" ~ "Bergamo",
                                              Provincia == "BS" ~ "Brescia",
                                              Provincia == "CO" ~ "Como",
                                              Provincia == "CR" ~ "Cremona",
                                              Provincia == "LC" ~ "Lecco",
                                              Provincia == "LO" ~ "Lodi",
                                              Provincia == "MN" ~ "Mantova",
                                              Provincia == "MI" ~ "Milano",
                                              Provincia == "MB" ~ "Monza e Brianza",
                                              Provincia == "PV" ~ "Pavia",
                                              Provincia == "SO" ~ "Sondrio",
                                              Provincia == "VA" ~ "Varese"
                                            ))
  
  sensors_map <- rbind(sensors_map, dati_grouped)
  
  print(year)
}


write.csv(sensors_map, "./data/sensors_map.csv", row.names = FALSE)


#Join with shape file
sensors_map <- read.csv( "./data/sensors_map.csv")
sensors_map <- geo_join(shapef, sensors_map, "SIGLA", "Provincia", how = "inner")


#Drop extra provinces: VR, RO, NO
sensors_map <- sensors_map %>% drop_na(ProvinciaLong)


#Save df for shiny app 
save(sensors_map, file = "./data/sensors_map.RData")

#################### stations_sens_map: MODIFIED DF FOR MAP REPRESENTATION OF SENSORS STATIONS ####################

#Add columns
stations_sens_map <- stationsS[, c(1,2,4,5,7,9,14,15)]
stations_sens_map <- stations_sens_map %>% mutate(ProvinciaLong = 
                          case_when(
                            Provincia == "BG" ~ "Bergamo",
                            Provincia == "BS" ~ "Brescia",
                            Provincia == "CO" ~ "Como",
                            Provincia == "CR" ~ "Cremona",
                            Provincia == "LC" ~ "Lecco",
                            Provincia == "LO" ~ "Lodi",
                            Provincia == "MN" ~ "Mantova",
                            Provincia == "MI" ~ "Milano",
                            Provincia == "MB" ~ "Monza e Brianza",
                            Provincia == "PV" ~ "Pavia",
                            Provincia == "SO" ~ "Sondrio",
                            Provincia == "VA" ~ "Varese"
                          ))

stations_sens_map <- stations_sens_map %>% mutate(NomeTipoSensoreENG =
                          case_when(NomeTipoSensore == "Particelle sospese PM2.5" ~ "PM2.5",
                                    NomeTipoSensore == "PM10 (SM2005)" ~ "PM10",
                                    NomeTipoSensore == "PM10" ~ "PM10",
                                    NomeTipoSensore == "Ozono" ~ "Ozone",
                                    NomeTipoSensore == "Biossido di Azoto" ~ "Nitrogen Dioxide",
                                    NomeTipoSensore == "Ossidi di Azoto" ~ "Nitric Oxide",
                                    NomeTipoSensore == "Monossido di Carbonio" ~ "Carbon Monoxide",
                                    NomeTipoSensore == "Ammoniaca" ~ "Ammonia",
                                    NomeTipoSensore == "Arsenico" ~ "Arsenic",
                                    NomeTipoSensore == "Benzene" ~ "Benzene",
                                    NomeTipoSensore == "Benzopirene" ~ "Benzopyrene",
                                    NomeTipoSensore == "Cadmio" ~ "Cadmium",
                                    NomeTipoSensore == "BlackCarbon" ~ "Black Carbon",
                                    NomeTipoSensore == "Nikel" ~ "Nickel",
                                    NomeTipoSensore == "Monossido di Azoto" ~ "Nitrogen Monoxide",
                                    NomeTipoSensore == "Piombo" ~ "Lead"
                          ))

#Drop extra pollutants
stations_sens_map <- stations_sens_map %>% drop_na(NomeTipoSensoreENG)


#Add colors for palette
stations_sens_map <-stations_sens_map %>% mutate(color =
                          case_when(NomeTipoSensore == "Particelle sospese PM2.5" ~ "#C51B7D",
                                    NomeTipoSensore == "PM10 (SM2005)" ~ "#1A9850",
                                    NomeTipoSensore == "PM10" ~ "#1A9850",
                                    NomeTipoSensore == "Ozono" ~ "#B2182B",
                                    NomeTipoSensore == "Biossido di Azoto" ~ "#FFFF99",
                                    NomeTipoSensore == "Ossidi di Azoto" ~ "#CAB2D6",
                                    NomeTipoSensore == "Monossido di Carbonio" ~ "#E31A1C",
                                    NomeTipoSensore == "Ammoniaca" ~"#A6CEE3",
                                    NomeTipoSensore == "Arsenico" ~ "#1F78B4",
                                    NomeTipoSensore == "Benzene" ~ "#B2DF8A",
                                    NomeTipoSensore == "Benzopirene" ~ "#6a3d9a",
                                    NomeTipoSensore == "Cadmio" ~ "#FB9A99",
                                    NomeTipoSensore == "BlackCarbon" ~ "#33A02C",
                                    NomeTipoSensore == "Nikel" ~ "#FF7F00",
                                    NomeTipoSensore == "Monossido di Azoto" ~ "#B15928",
                                    NomeTipoSensore == "Piombo" ~ "#FDBF6F"))



#Join with shape file
stations_sens_map <- geo_join(shapef, stations_sens_map, "SIGLA", "Provincia", how = "inner")

#Drop extra provinces: VR, RO, NO
stations_sens_map <- stations_sens_map %>% drop_na(ProvinciaLong)


#Save for shiny app
save(stations_sens_map, file = "./data/stations_sens_map.RData")




#################### sens_utils: VALUES FOR COLOR SCALE in SENSORS_MAP ###########################

sens_utils <- data.frame(matrix(ncol = 6, nrow = 2))

for (inq in unique(df_all$NomeTipoSensoreENG)){
  print(inq)
  m <- round_any(max(df_all$valore[df_all$NomeTipoSensoreENG == inq]), 5)
  sens_utils[inq] = m
}
sens_utils <- sens_utils[, 7:12]
sens_utils$`CO - Carbon Monoxide`<-3.1
sens_utils[2,] <- c("YlOrBr", "PuBu", "Greys", "YlOrRd", "OrRd", "Oranges")
write.csv(sens_utils, "./data/sens_utils.csv", row.names = FALSE)




#################### sens_aggrDay_utils: VALUES FOR COLOR SCALE in SENS_AGGRDAY ###########################

sens_aggrDay_utils <- data.frame(matrix(ncol = 6, nrow = 2))

for (inq in unique(sens_aggrDay$NomeTipoSensoreENG)){
  print(inq)
  m <- round_any(max(sens_aggrDay$valore[sens_aggrDay$NomeTipoSensoreENG == inq]),2)
  sens_aggrDay_utils[inq] = m
}
sens_aggrDay_utils <- sens_aggrDay_utils[1, 7:12]
sens_aggrDay_utils[2,] <- c("Greys", "YlOrBr", "YlOrRd", "PuBu","OrRd", "Oranges")
write.csv(sens_aggrDay_utils, "./data/sens_aggrDay_utils.csv", row.names = FALSE)




#################### OLD sens_day_utils: VALUES FOR COLOR SCALE in DATI_DAY ###########################

sens_day_utils <- data.frame(matrix(ncol = 6, nrow = 2))

for (inq in unique(dati_day$NomeTipoSensoreENG)){
  print(inq)
  m <- round_any(max(dati_day$valore[dati_day$NomeTipoSensoreENG == inq]), 5)
  sens_day_utils[inq] = m
}
sens_day_utils <- sens_day_utils[1, 7:12]
sens_day_utils[2,] <- c("YlOrBr", "PuBu", "Greys", "YlOrRd", "OrRd", "Oranges")
write.csv(sens_day_utils, "./data/sens_day_utils.csv", row.names = FALSE)




#################### OLD DF FOR CORRELATION AMONG POLLUTANTS ###########################
for (i in 1:length(unique(sens_aggrDay$NomeTipoSensoreENG))){
  inq = unique(sens_aggrDay$NomeTipoSensoreENG)[i]
  if(i == 1){
    p <- sens_aggrDay %>%
      filter(NomeTipoSensoreENG==inq) %>%
      select(dataora, anno, valore)
    names(p)[names(p) == "valore" ] <- inq
    names(p) <-gsub(" .*$", "", names(p))
    df_corr_poll <- p
  }
  else{
    p <- sens_aggrDay %>%
      filter(NomeTipoSensoreENG==inq) %>%
      select(dataora, anno, valore)
    names(p)[names(p) == "valore" ] <- inq
    names(p) <-gsub(" .*$", "", names(p))
    df_corr_poll <- merge(df_corr_poll, p, by.x = c("dataora", "anno"), by.y = c("dataora", "anno"))
  }
}

# PM2.5 had too few values in year prior to 2006, just drop the years or analysis will be biased
df_corr_poll <- df_corr_poll%>% filter(anno != 2001 & anno !=2005)

# Save for shiny app
save(df_corr_poll,file = "./data/df_corr_poll.RData")



#################### OLD DF FOR CORRELATION AMONG YEARS ###########################
df_year <- sens_aggrDay
df_year$dataora <- sub(".*?-",'',df_year$dataora)   

for (i in 6:21){
  a = unique(df_year$anno)[i]
  if(i == 6){
    p <- df_year %>%
      filter(anno==a) %>%
      select(NomeTipoSensoreENG, dataora, valore)
    names(p)[names(p) == "valore" ] <- a
    df_corr_year <- p
  }

else if(i >6){
    p <- df_year %>%
      filter(anno==a) %>%
      select(NomeTipoSensoreENG, dataora, valore)
    names(p)[names(p) == "valore" ] <- a
    df_corr_year <- merge(df_corr_year, p, by.x = c("NomeTipoSensoreENG", "dataora" ), by.y = c("NomeTipoSensoreENG", "dataora"))
  }
}

df_corr_year$NomeTipoSensoreENG <- gsub(" .*$", "", df_corr_year$NomeTipoSensoreENG)

# Save for shiny app
save(df_corr_year,file = "./data/df_corr_year.RData")
