################# LOAD DATA #################
setwd(dirname(rstudioapi::getActiveDocumentContext()$path))

sensori <- na.omit(read.csv("./data/sensori_2022.csv"))
stazioni <- read.csv("./data/stazioni.csv")
dati_small <- read.csv("./data/dati_small.csv")
dati_small$meseGiorno<-format(as.Date(dati_small$dataora,format="%Y-%m-%d"), format = "%m-%d")

df_max_small <- read.csv("./data/df_max_small.csv")
colnames(df_max_small) <- c("CO - Carbon Monoxide", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide", "O3 - Ozone", "PM10", "PM2.5")

load("./data/inquinanti.RData")
load("./data/df_max.RData")
load("./data/staz.RData")
load("./data/df_corr_poll.RData")
load("./data/df_corr_year.RData")
colnames(df_max) <- c("NO2 - Nitrogen Dioxide", "O3 - Ozone", "CO - Carbon Monoxide", "NO - Nitric Oxide", "PM10", "PM2.5")

