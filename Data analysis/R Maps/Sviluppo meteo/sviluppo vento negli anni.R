require(maptools)
library(gstat);
library(ggplot2)
library(rgdal)
library(dplyr)

lomb <- readOGR("C:\\Users\\stefa\\Tesi\\REGIONE_LOMBARDIA\\Province_2020.shp", verbose=T)
lomb <- spTransform(lomb, CRS("+proj=longlat +ellps=WGS84 +datum=WGS84"))

stazioni<-read.csv('stazioni_meteo.csv')
stazioni$tipologia <- as.factor(stazioni$tipologia)

dati2021<-read.csv("dati_vento\\vento2021.csv")

d2021_g <- d2021_grouped <- dati2021 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(Valore), list(name = mean))

## COstruisCO la mappa

coordinates(d2021_grouped)=c("lng","lat")

bbox(lomb)
x=seq(bbox(lomb)[1,1],bbox(lomb)[1,2],length.out=100) 
y=seq(bbox(lomb)[2,1],bbox(lomb)[2,2],length.out=100) 
grid=expand.grid(x=x,y=y); head(grid); plot(grid)
coordinates(grid) = ~x+y
gridded(grid) = TRUE; 
plot(grid)

idw.p=idw(formula=log(name) ~ 1, locations=d2021_grouped , newdata=grid, 
          nmax = 30, idp = 0.5)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")

plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Map of wind speed in Lombardy in 2021")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2",  high="#ba8509", limits=c(-1, 2), breaks=seq(-1, 2,by=0.5))+coord_equal() + title + labs(fill = "Logarithm of speed in m/s") 

# 2019 --------------------------------------------------------------------


dati2019<-read.csv("dati_vento\\vento2019.csv")

d2019_g <- d2019_grouped <- dati2019 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(Valore), list(name = mean))

coordinates(d2019_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2019_grouped , newdata=grid, 
          nmax = 30, idp = 0.5)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")

plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Map of wind speed in Lombardy in 2019")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2",  high="#ba8509", limits=c(-1, 2), breaks=seq(-1, 2,by=0.5))+coord_equal() + title + labs(fill = "Logarithm of speed in m/s") 

# 2017 --------------------------------------------------------------------


dati2017<-read.csv("dati_vento\\vento2017.csv")

d2017_g <- d2017_grouped <- dati2017 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(Valore), list(name = mean))

coordinates(d2017_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2017_grouped , newdata=grid, 
          nmax = 30, idp = 0.5)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")

plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Map of wind speed in Lombardy in 2017")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2",  high="#ba8509", limits=c(-1, 2), breaks=seq(-1, 2,by=0.5))+coord_equal() + title + labs(fill = "Logarithm of speed in m/s") 

# 2015 --------------------------------------------------------------------


dati2015<-read.csv("dati_vento\\vento2015.csv")

d2015_g <- d2015_grouped <- dati2015 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(Valore), list(name = mean))

coordinates(d2015_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2015_grouped , newdata=grid, 
          nmax = 30, idp = 0.5)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")

plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Map of wind speed in Lombardy in 2015")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2",  high="#ba8509", limits=c(-1, 2), breaks=seq(-1, 2,by=0.5))+coord_equal() + title + labs(fill = "Logarithm of speed in m/s") 

# 2013 --------------------------------------------------------------------


dati2013<-read.csv("dati_vento\\vento2013.csv")

d2013_g <- d2013_grouped <- dati2013 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(Valore), list(name = mean))

coordinates(d2013_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2013_grouped , newdata=grid, 
          nmax = 50, idp = 0.5)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")

plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Map of wind speed in Lombardy in 2013")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2",  high="#ba8509", limits=c(-1.5, 2), breaks=seq(-1, 2,by=0.5))+coord_equal() + title + labs(fill = "Logarithm of speed in m/s") 

# 2011 --------------------------------------------------------------------


dati2011<-read.csv("dati_vento\\vento2011.csv")

d2011_g <- d2011_grouped <- dati2011 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(Valore), list(name = mean))

coordinates(d2011_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2011_grouped , newdata=grid, 
          nmax = 30, idp = 0.5)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")

plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Map of wind speed in Lombardy in 2011")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2",  high="#ba8509", limits=c(-1, 2), breaks=seq(-1, 2,by=0.5))+coord_equal() + title + labs(fill = "Logarithm of speed in m/s") 
