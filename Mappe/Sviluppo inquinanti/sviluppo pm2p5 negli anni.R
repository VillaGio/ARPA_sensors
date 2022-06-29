lomb <- readOGR('C:\\Users\\stefa\\OneDrive\\Desktop\\Tesi\\REGIONE_LOMBARDIA\\Province_2020.shp', verbose=T)
lomb <- spTransform(lomb, CRS("+proj=longlat +ellps=WGS84 +datum=WGS84"))

stazioni<-read.csv('C:\\Users\\stefa\\OneDrive\\Desktop\\Tesi\\DatiGrezzi\\Stazioni_qualit__dell_aria.csv')
stazioni$NomeTipoSensore <- as.factor(stazioni$NomeTipoSensore)

##scarico e raggruppo i dati
dati2021<-read.csv("DatiAriaCsvMod\\2021.csv")
d2021 <- merge(dati2021, stazioni, by.x="idsensore", by.y="IdSensore")
d2021pm2p5 <- d2021[d2021$NomeTipoSensore=="Particelle sospese PM2.5",]
d2021pm2p5 <- d2021pm2p5[d2021pm2p5$valore > 0,]

rm(d2021)
rm(dati2021)

d2021pm2p5_g <- d2021pm2p5_grouped <- d2021pm2p5 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## costruisco la mappa

coordinates(d2021pm2p5_grouped)=c("lng","lat")

bbox(lomb)
x=seq(bbox(lomb)[1,1],bbox(lomb)[1,2],length.out=100) 
y=seq(bbox(lomb)[2,1],bbox(lomb)[2,2],length.out=100) 
grid=expand.grid(x=x,y=y); head(grid); plot(grid)
coordinates(grid) = ~x+y
gridded(grid) = TRUE; 
plot(grid)

idw.p=idw(formula=log(name) ~ 1, locations=d2021pm2p5_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer con confini comune
title<-ggtitle("Mappa della concentrazione pm2.5 in Lombardia nel 2021")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f", limits=c(2.25, 4), breaks=seq(2.25,4,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di pm2.5") 

##2019

##scarico e raggruppo i dati
dati2019<-read.csv("DatiAriaCsvMod\\2019.csv")
d2019 <- merge(dati2019, stazioni, by.x="idsensore", by.y="IdSensore")
d2019pm2p5 <- d2019[d2019$NomeTipoSensore=="Particelle sospese PM2.5",]
d2019pm2p5 <- d2019pm2p5[d2019pm2p5$valore > 0,]

rm(d2019)
rm(dati2019)

d2019pm2p5_g <- d2019pm2p5_grouped <- d2019pm2p5 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## costruisco la mappa

coordinates(d2019pm2p5_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2019pm2p5_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer con confini comune
title<-ggtitle("Mappa della concentrazione pm2.5 in Lombardia nel 2019")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f", limits=c(2.25, 4), breaks=seq(2.25,4,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di pm2.5") 

## 2017

##scarico e raggruppo i dati
dati2017<-read.csv("DatiAriaCsvMod\\2017.csv")
d2017 <- merge(dati2017, stazioni, by.x="idsensore", by.y="IdSensore")
d2017pm2p5 <- d2017[d2017$NomeTipoSensore=="Particelle sospese PM2.5",]
d2017pm2p5 <- d2017pm2p5[d2017pm2p5$valore > 0,]

rm(d2017)
rm(dati2017)

d2017pm2p5_g <- d2017pm2p5_grouped <- d2017pm2p5 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## costruisco la mappa

coordinates(d2017pm2p5_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2017pm2p5_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer con confini comune
title<-ggtitle("Mappa della concentrazione pm2.5 in Lombardia nel 2017")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f", limits=c(2.25, 4), breaks=seq(2.25,4,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di pm2.5") 

## 2015

##scarico e raggruppo i dati
dati2015<-read.csv("DatiAriaCsvMod\\2015.csv")
d2015 <- merge(dati2015, stazioni, by.x="idsensore", by.y="IdSensore")
d2015pm2p5 <- d2015[d2015$NomeTipoSensore=="Particelle sospese PM2.5",]
d2015pm2p5 <- d2015pm2p5[d2015pm2p5$valore > 0,]

rm(d2015)
rm(dati2015)

d2015pm2p5_g <- d2015pm2p5_grouped <- d2015pm2p5 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## costruisco la mappa

coordinates(d2015pm2p5_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2015pm2p5_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer con confini comune
title<-ggtitle("Mappa della concentrazione pm2.5 in Lombardia nel 2015")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f", limits=c(2.25, 4), breaks=seq(2.25,4,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di pm2.5") 

## 2013

##scarico e raggruppo i dati
dati2013<-read.csv("DatiAriaCsvMod\\2013.csv")
d2013 <- merge(dati2013, stazioni, by.x="idsensore", by.y="IdSensore")
d2013pm2p5 <- d2013[d2013$NomeTipoSensore=="Particelle sospese PM2.5",]
d2013pm2p5 <- d2013pm2p5[d2013pm2p5$valore > 0,]

rm(d2013)
rm(dati2013)

d2013pm2p5_g <- d2013pm2p5_grouped <- d2013pm2p5 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## costruisco la mappa

coordinates(d2013pm2p5_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2013pm2p5_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer con confini comune
title<-ggtitle("Mappa della concentrazione pm2.5 in Lombardia nel 2013")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f", limits=c(2.25, 4), breaks=seq(2.25,4,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di pm2.5") 

## 2011

##scarico e raggruppo i dati
dati2011<-read.csv("DatiAriaCsvMod\\2011.csv")
d2011 <- merge(dati2011, stazioni, by.x="idsensore", by.y="IdSensore")
d2011pm2p5 <- d2011[d2011$NomeTipoSensore=="Particelle sospese PM2.5",]
d2011pm2p5 <- d2011pm2p5[d2011pm2p5$valore > 0,]

rm(d2011)
rm(dati2011)

d2011pm2p5_g <- d2011pm2p5_grouped <- d2011pm2p5 %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## costruisco la mappa

coordinates(d2011pm2p5_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2011pm2p5_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer con confini comune
title<-ggtitle("Mappa della concentrazione pm2.5 in Lombardia nel 2011")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f", limits=c(2.25, 4), breaks=seq(2.25,4,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di pm2p5") 
