################# LOAD DATA #################
setwd(dirname(rstudioapi::getActiveDocumentContext()$path))


# Load datasets for tab DATA
sensors <- na.omit(read.csv("./data/sensors2022.csv"))
weather <- na.omit(read.csv("./data/weather2022.csv"))
stations_sens <- read.csv("./data/stations_sens.csv")
stations_weather <- read.csv("./data/stations_weather.csv")


# Load datasets for MAPS
load("./data/sensors_map.RData")
load("./data/stations_sens_map.RData")
load("./data/stations_weather_map.RData")

# Load utilities datasets
sens_utils <- read.csv("./data/sens_utils.csv")
colnames(sens_utils) <- c("NO2 - Nitrogen Dioxide", "O3 - Ozone", "CO - Carbon Monoxide", "NO - Nitric Oxide", "PM10", "PM2.5")

sens_aggrDay_utils <- read.csv("./data/sens_aggrDay_utils.csv")
colnames(sens_aggrDay_utils) <- c("CO - Carbon Monoxide", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide", "O3 - Ozone", "PM10", "PM2.5")


# Load datasets for visualizations
sens_aggrDay <- read.csv("./data/sens_aggrDay.csv")
sens_aggrDay$meseGiorno<-format(as.Date(sens_aggrDay$dataora,format="%Y-%m-%d"), format = "%m-%d")




