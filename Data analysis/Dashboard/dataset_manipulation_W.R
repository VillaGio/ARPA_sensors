library(rgdal)
library(plyr)
library(dplyr)     # for pipe operator & data manipulations
library(tidyverse) # tidy data wrangling
library(vroom)     # fats reading/importing csv data
library(sf)        # read spatial data
library(tigris)    # geojoin

setwd(dirname(rstudioapi::getActiveDocumentContext()$path))


#################### shapef: SHAPEFILES FOR POLYGONS ####################

shapef <- st_read("./ProvCM01012022/ProvCM01012022_WGS84.shp")
shapef <- shapef[, c(9,13)]



#################### stationsW: WEATHER STATIONS INFO ####################

stationsW<-read.csv("../../Data engineering/Data/Stations/stationsW.csv")
stationsW$Tipologia <- as.factor(stationsW$Tipologia)



#################### stations_weather_map: MODIFIED DF FOR MAP REPRESENTATION OF SENSORS STATIONS ####################

#Add columns
stations_weather_map <- stationsW[, c(1,2,4,5,7,13,14,15)]
stations_weather_map <- stations_weather_map %>% mutate(ProvinciaLong = 
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

stations_weather_map <- stations_weather_map %>% mutate(TipologiaENG =
                                                    case_when(Tipologia == "Altezza Neve" ~ "Snow Level",
                                                              Tipologia == "Direzione Vento" ~ "Wind Direction",
                                                              Tipologia == "Livello Idrometrico" ~ "Hydrometric Level",
                                                              Tipologia == "Precipitazione" ~ "Rain",
                                                              Tipologia == "Radiazione Globale" ~ "Global Radiation",
                                                              Tipologia == "Temperatura" ~ "Temperature",
                                                              str_detect(Tipologia, "Umidit") ~ "Relative Humidity",
                                                              str_detect(Tipologia, "Velocit") ~ "Wind Speed"
                                                    ))


#Add colors for palette
stations_weather_map <-stations_weather_map  %>% mutate(color =
                                    case_when(Tipologia == "Altezza Neve" ~ "#FB9A99",
                                              Tipologia == "Direzione Vento" ~ "#FDBF6F",
                                              Tipologia == "Livello Idrometrico" ~ "#1F78B4",
                                              Tipologia == "Precipitazione" ~ "#B2DF8A",
                                              Tipologia == "Radiazione Globale" ~ "#A6CEE3",
                                              Tipologia == "Temperatura" ~ "#E31A1C",
                                              str_detect(Tipologia, "Umidit") ~ "#33A02C",
                                              str_detect(Tipologia, "Velocit") ~ "#FF7F00"
                                              ))

#Join with shape file
stations_weather_map <- geo_join(shapef, stations_weather_map, "SIGLA", "Provincia", how = "inner")


#Drop extra province: AL
stations_weather_map <- stations_weather_map %>% drop_na(ProvinciaLong)

#Save for shiny app
save(stations_weather_map, file = "./data/stations_weather_map.RData")



