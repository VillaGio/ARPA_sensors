library(rgdal)
library(plyr)
library(tidyverse) #tidy data wrangling
library(vroom) # fats reading/importing csv data
library(sf) #spatial data
library(tigris) #geojoin

setwd(dirname(rstudioapi::getActiveDocumentContext()$path))

#################### SHAPEFILES FOR POLYGONS ####################

shapef <- st_read("C:\\Users\\giorg\\OneDrive\\Desktop\\Final Thesis\\shiny\\arpa_maps\\Limiti01012022\\Limiti01012022\\ProvCM01012022\\ProvCM01012022_WGS84.shp")
shapef <- shapef[, c(9,13)]



#################### STATIONS INFO ####################

stazioni<-read.csv('C:\\Users\\giorg\\OneDrive\\Desktop\\Final Thesis\\ARPA_sensors\\Mappe\\REGIONE_LOMBARDIA\\Stazioni_qualit__dell_aria.csv')
stazioni$NomeTipoSensore <- as.factor(stazioni$NomeTipoSensore)



#################### AGGREGATE DF ON DAY AND IDSENSORE FOR REPRESENTATION OF IN HISTOGRAMS, BOXPLOTS, ETC  ####################

#Base df
dati_day <- setNames(data.frame(matrix(ncol = 7, nrow = 0)), c("idesensore", "dataora", "NomeTipoSensore", "valore", "anno"))


#Single years csv
for(year in 2021:2011){
  path = paste("C:\\Users\\giorg\\OneDrive\\Desktop\\Final Thesis\\ARPA_sensors\\dati_sensori_1968_2020\\mod_", year, ".csv", sep="")
  
  dati <- read.csv(path)
  dati_merged <- merge(dati, stazioni, by.x="idsensore", by.y="IdSensore")
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
  
  path = paste("C:\\Users\\giorg\\OneDrive\\Desktop\\Final Thesis\\ARPA_sensors\\dati_sensori_1968_2020\\mod_", year, ".csv", sep="")
  
  dati <- read.csv(path)
  dati_merged <- merge(dati, stazioni, by.x="idsensore", by.y="IdSensore")
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



#################### AGGREGATE DF ON DATE ONLY FOR PLOT REPRESENTATION OF POLLUTANTS ####################
dati_day <- read.csv("./data/dati_daily.csv")

dati_small <- dati_day %>% 
  group_by(dataora, NomeTipoSensoreENG, anno) %>%
  summarise_at(vars(valore), list(valore = mean))

write.csv(dati_small, "./data/dati_small.csv", row.names = FALSE)


#################### AGGREGATE DF ON YEAR FOR MAP REPRESENTATION OF POLLUTANTS ####################

#Base df
dati_all <- setNames(data.frame(matrix(ncol = 6, nrow = 0)), c("idesensore", "Comune", "Provincia", "NomeTipoSensore", "valore", "anno"))


#Single years csv
for(year in 2021:2011){
  path = paste("C:\\Users\\giorg\\OneDrive\\Desktop\\Final Thesis\\ARPA_sensors\\dati_sensori_1968_2020\\mod_", year, ".csv", sep="")
  
  dati <- read.csv(path)
  dati_merged <- merge(dati, stazioni, by.x="idsensore", by.y="IdSensore")
  dati_filtered <- dati_merged[dati_merged$NomeTipoSensore=="Particelle sospese PM2.5" | dati_merged$NomeTipoSensore=="PM10" | 
                                 dati_merged$NomeTipoSensore=="PM10 (SM2005)" | dati_merged$NomeTipoSensore=="Ozono" | dati_merged$NomeTipoSensore=="Biossido di Azoto" |
                                 dati_merged$NomeTipoSensore=="Ossidi di Azoto" | dati_merged$NomeTipoSensore=="Monossido di Carbonio",]
  dati_filtered <- dati_filtered[dati_filtered$valore > 0,]
  
  
  dati_grouped <- dati_filtered %>%
    group_by(idsensore, Comune, Provincia, NomeTipoSensore) %>%
    summarise_at(vars(valore), list(valore = mean))
  
  dati_grouped["anno"] = year
  
  dati_all <- rbind(dati_all, dati_grouped)
  
  dati_all <- dati_all %>% mutate(NomeTipoSensoreENG =
                                    case_when(NomeTipoSensore == "Particelle sospese PM2.5" ~ "PM2.5",
                                              NomeTipoSensore == "PM10 (SM2005)" ~ "PM10",
                                              NomeTipoSensore == "PM10" ~ "PM10",
                                              NomeTipoSensore == "Ozono" ~ "O3 - Ozone",
                                              NomeTipoSensore == "Biossido di Azoto" ~ "NO2 - Nitrogen Dioxide",
                                              NomeTipoSensore == "Ossidi di Azoto" ~ "NO - Nitric Oxide",
                                              NomeTipoSensore == "Monossido di Carbonio" ~ "CO - Carbon Monoxide"
                                    ))
  
  dati_all <- dati_all %>% mutate(ProvinciaLong = 
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


write.csv(dati_all, "./data/dati_all.csv", row.names = FALSE)


#Multiple years csv
dati_all <- read.csv("./data/dati_all.csv")

for (year in c(2010, 2007, 2004)){
  
  path = paste("C:\\Users\\giorg\\OneDrive\\Desktop\\Final Thesis\\ARPA_sensors\\dati_sensori_1968_2020\\mod_", year, ".csv", sep="")
  
  dati <- read.csv(path)
  dati_merged <- merge(dati, stazioni, by.x="idsensore", by.y="IdSensore")
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
  
  dati_all <- rbind(dati_all, dati_grouped)
  
  print(year)
}


write.csv(dati_all, "./data/dati_all.csv", row.names = FALSE)


#Join with shape file
dati_all <- read.csv( "./data/dati_all.csv")
df_all <- geo_join(shapef, dati_all, "SIGLA", "Provincia", how = "inner")


#Drop extra provinces: VR, RO, NO
df_all <- df_all %>% drop_na(ProvinciaLong)


#Save df for shiny app 
save(df_all, file = "./data/df_all.RData")




#################### MODIFIED DF FOR MAP REPRESENTATION OF STATIONS ####################

#Add columns
staz <- stazioni[, c(1,2,4,5,7,9,14,15)]
staz <- staz %>% mutate(ProvinciaLong = 
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

staz <- staz %>% mutate(NomeTipoSensoreENG =
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
staz <- staz %>% drop_na(NomeTipoSensoreENG)


#Add colors for palette
staz <- staz %>% mutate(color =
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
staz <- geo_join(shapef, staz, "SIGLA", "Provincia", how = "inner")

#Drop extra provinces: VR, RO, NO
staz <- staz %>% drop_na(ProvinciaLong)


#Save for shiny app
save(staz, file = "./data/stazioni.RData")




#################### VALUES FOR COLOR SCALE in DF_ALL ###########################

df_max <- data.frame(matrix(ncol = 6, nrow = 2))

for (inq in unique(df_all$NomeTipoSensoreENG)){
  print(inq)
  m <- round_any(max(df_all$valore[df_all$NomeTipoSensoreENG == inq]), 5)
  df_max[inq] = m
}
df_max <- df_max[, 7:12]
df_max$`CO - Carbon Monoxide`<-3.1
df_max[2,] <- c("YlOrBr", "PuBu", "Greys", "YlOrRd", "OrRd", "Oranges")
write.csv(df_max, "./data/df_max.csv", row.names = FALSE)



#################### VALUES FOR COLOR SCALE in DATI_DAY ###########################

df_max_day <- data.frame(matrix(ncol = 6, nrow = 2))

for (inq in unique(dati_day$NomeTipoSensoreENG)){
  print(inq)
  m <- round_any(max(dati_day$valore[dati_day$NomeTipoSensoreENG == inq]), 5)
  df_max_day[inq] = m
}
df_max_day <- df_max_day[1, 7:12]
df_max_day[2,] <- c("YlOrBr", "PuBu", "Greys", "YlOrRd", "OrRd", "Oranges")
write.csv(df_max_day, "./data/df_max_day.csv", row.names = FALSE)



#################### VALUES FOR COLOR SCALE in DATI_SMALL ###########################

df_max_small <- data.frame(matrix(ncol = 6, nrow = 2))

for (inq in unique(dati_small$NomeTipoSensoreENG)){
  print(inq)
  m <- round_any(max(dati_small$valore[dati_small$NomeTipoSensoreENG == inq]),2)
  df_max_small[inq] = m
}
df_max_small <- df_max_small[1, 7:12]
df_max_small[2,] <- c("Greys", "YlOrBr", "YlOrRd", "PuBu","OrRd", "Oranges")
write.csv(df_max_small, "./data/df_max_small.csv", row.names = FALSE)



#################### DF FOR CORRELATION AMONG POLLUTANTS ###########################
for (i in 1:length(unique(dati_small$NomeTipoSensoreENG))){
  inq = unique(dati_small$NomeTipoSensoreENG)[i]
  if(i == 1){
    p <- dati_small %>%
      filter(NomeTipoSensoreENG==inq) %>%
      select(dataora, anno, valore)
    names(p)[names(p) == "valore" ] <- inq
    names(p) <-gsub(" .*$", "", names(p))
    df_corr_poll <- p
  }
  else{
    p <- dati_small %>%
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



#################### DF FOR CORRELATION AMONG YEARS ###########################
df_year <- dati_small
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
