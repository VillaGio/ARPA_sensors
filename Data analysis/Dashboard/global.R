################# LOAD DATA #################
setwd(dirname(rstudioapi::getActiveDocumentContext()$path))

# Load shapefile for maps
shapef <- st_read("./ProvCM01012022/ProvCM01012022_WGS84.shp")
shapef <- shapef[, c(9,13)]


# Load datasets for tab DATA
sensors <- na.omit(read.csv("./data/sensors2022.csv"))
weather <- na.omit(read.csv("./data/weather2022.csv"))
stations_sens <- read.csv("./data/stations_sens.csv")
stations_weather <- read.csv("./data/stations_weather.csv")


# Load datasets for MAPS
#load("./data/sensors_map.RData")
load("./data/stations_sens_map.RData")
load("./data/stations_weather_map.RData")
sensors_map <- read.csv("./data/sensors_map.csv")
sensors_map <- geo_join(shapef, sensors_map, "SIGLA", "SIGLA", how = "inner")
sensors_map <- sensors_map %>% drop_na(ProvinciaLong)

# Load utilities datasets
sens_utils <- read.csv("./data/sens_utils.csv")
colnames(sens_utils) <- c("NO2 - Nitrogen Dioxide", "O3 - Ozone", "CO - Carbon Monoxide", "NO - Nitric Oxide", "PM10", "PM2.5")

sens_aggrDay_utils <- read.csv("./data/sens_aggrDay_utils.csv")
colnames(sens_aggrDay_utils) <- c("CO - Carbon Monoxide", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide", "O3 - Ozone", "PM10", "PM2.5")


# Load datasets for visualizations
sens_aggrDay <- read.csv("./data/sens_aggrDay.csv")
sens_aggrDay$meseGiorno<-format(as.Date(sens_aggrDay$dataora,format="%Y-%m-%d"), format = "%m-%d")


# Load pretrained model for predictions
model <- readRDS("./data/model.rds")


