require(maptools)
library(gstat);
library(ggplot2)

lomb <- readOGR('C:\\Users\\stefa\\OneDrive\\Desktop\\Tesi\\REGIONE_LOMBARDIA\\Province_2020.shp', verbose=T)
lomb <- spTransform(lomb, CRS("+proj=longlat +ellps=WGS84 +datum=WGS84"))

stazioni<-read.csv('C:\\Users\\stefa\\OneDrive\\Desktop\\Tesi\\DatiGrezzi\\Stazioni_qualit__dell_aria.csv')
stazioni$NomeTipoSensore <- as.factor(stazioni$NomeTipoSensore)

##scariCO e raggruppo i dati
dati2021<-read.csv("DatiAriaCsvMod\\2021.csv")
d2021 <- merge(dati2021, stazioni, by.x="idsensore", by.y="IdSensore")
d2021CO <- d2021[d2021$NomeTipoSensore=="Monossido di Carbonio" ,]
d2021CO <- d2021CO[d2021CO$valore > 0,]

rm(d2021)
rm(dati2021)

d2021CO_g <- d2021CO_grouped <- d2021CO %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## COstruisCO la mappa

coordinates(d2021CO_grouped)=c("lng","lat")

bbox(lomb)
x=seq(bbox(lomb)[1,1],bbox(lomb)[1,2],length.out=100) 
y=seq(bbox(lomb)[2,1],bbox(lomb)[2,2],length.out=100) 
grid=expand.grid(x=x,y=y); head(grid); plot(grid)
coordinates(grid) = ~x+y
gridded(grid) = TRUE; 
plot(grid)

## calCOlo del miglior numero di vicini

# ris=NULL  # inizializzazione dell'oggetto (matrice) che COnterr? l'output finale
# idp=2     # valore dell'esponente usato nell'IWD
# for(nneigh in seq(1,20,by=1)){ # la griglia di valori deve essere modificata in base all'obiettivo
#   fit=vector()
#   for(i in 1:nrow(d2021CO_g))  {
#     datcv=d2021CO_g[-i,]     # elimina riga i-esima
#     coordinates(datcv)=c("lng","lat") # trasforma il dataset residuo COme uno spatial dataframe
#     nd=d2021CO_g[i,c("lng","lat")]          # salva la riga i-esima in un dataframe di una sola riga per il calCOlo dell'interpolatore
#     coordinates(nd)=c("lng","lat")    # e lo trasforma in uno spatial dataframe
#     fit=c(fit,
#           idw(formula=log(name) ~ 1, locations=datcv, # effettua l'interpolazione sulla riga i-esima e acCOda le previsioni in fit
#               newdata=nd, nmax = nneigh, idp = idp)$var1.pred)
#   }
#   ris=rbind(ris,
#             c(vicinato=nneigh, R=cor(fit,log(d2021CO_g[,"name"])),
#               MSE=mean((fit-log(d2021CO_g[,"name"]))^2)
#             ))
# }
# plot(ris[,"vicinato"],ris[,"R"],xlab="numero vicini",ylab="R",type="l")

coordinates(d2021CO_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2021CO_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
#lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Concentration map of CO in Lombardy in 2021")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f",limits=c(-1.25, 0.5), breaks=seq(-1.25, 0.5,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di CO") 


## 10 e 2 ci piacciono COme valori di vicinato ed esponente

## 2019

dati2019<-read.csv("DatiAriaCsvMod\\2019.csv")
d2019 <- merge(dati2019, stazioni, by.x="idsensore", by.y="IdSensore")
d2019CO <- d2019[d2019$NomeTipoSensore=="Monossido di Carbonio",]
d2019CO <- d2019CO[d2019CO$valore > 0,]

rm(d2019)
rm(dati2019)

d2019CO_g <- d2019CO_grouped <- d2019CO %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## COstruisCO la mappa

coordinates(d2019CO_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2019CO_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
#lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Concentration map of CO in Lombardy in 2019")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f",limits=c(-1.25, 0.5), breaks=seq(-1.25, 0.5,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di CO") 


## 2017

dati2017<-read.csv("DatiAriaCsvMod\\2017.csv")
d2017 <- merge(dati2017, stazioni, by.x="idsensore", by.y="IdSensore")
d2017CO <- d2017[d2017$NomeTipoSensore=="Monossido di Carbonio",]
d2017CO <- d2017CO[d2017CO$valore > 0,]

rm(d2017)
rm(dati2017)

d2017CO_g <- d2017CO_grouped <- d2017CO %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## COstruisCO la mappa

coordinates(d2017CO_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2017CO_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
#lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Concentration map of CO in Lombardy in 2017")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f",limits=c(-1.25, 0.5), breaks=seq(-1.25, 0.5,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di CO") 

## 2015

dati2015<-read.csv("DatiAriaCsvMod\\2015.csv")
d2015 <- merge(dati2015, stazioni, by.x="idsensore", by.y="IdSensore")
d2015CO <- d2015[d2015$NomeTipoSensore=="Monossido di Carbonio",]
d2015CO <- d2015CO[d2015CO$valore > 0,]

rm(d2015)
rm(dati2015)

d2015CO_g <- d2015CO_grouped <- d2015CO %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## COstruisCO la mappa

coordinates(d2015CO_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2015CO_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
#lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Concentration map of CO in Lombardy in nel 2015")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f",limits=c(-1.25, 0.5), breaks=seq(-1.25, 0.5,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di CO") 
#FEEBE2

## 2013

dati2013<-read.csv("DatiAriaCsvMod\\2013.csv")
d2013 <- merge(dati2013, stazioni, by.x="idsensore", by.y="IdSensore")
d2013CO <- d2013[d2013$NomeTipoSensore=="Monossido di Carbonio",]
d2013CO <- d2013CO[d2013CO$valore > 0,]

rm(d2013)
rm(dati2013)

d2013CO_g <- d2013CO_grouped <- d2013CO %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## COstruisCO la mappa

coordinates(d2013CO_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2013CO_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
#lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Concentration map of CO in Lombardy in nel 2013")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f",limits=c(-1.25, 0.5), breaks=seq(-1.25, 0.5,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di CO") 

## 2011

dati2011<-read.csv("DatiAriaCsvMod\\2011.csv")
d2011 <- merge(dati2011, stazioni, by.x="idsensore", by.y="IdSensore")
d2011CO <- d2011[d2011$NomeTipoSensore=="Monossido di Carbonio",]
d2011CO <- d2011CO[d2011CO$valore > 0,]

rm(d2011)
rm(dati2011)

d2011CO_g <- d2011CO_grouped <- d2011CO %>%
  group_by(idsensore, lat, lng) %>%
  summarise_at(vars(valore), list(name = mean))

## COstruisCO la mappa

coordinates(d2011CO_grouped)=c("lng","lat")

idw.p=idw(formula=log(name) ~ 1, locations=d2011CO_grouped , newdata=grid, 
          nmax = 10, idp = 2)
idw.o=as.data.frame(idw.p)
names(idw.o)[1:3]<-c("long","lat","logp.pred")


plot<-ggplot(data=idw.o,aes(x=long,y=lat))  
#lomb_line <- fortify(lomb)
#plot iniziale
layer1<-c(geom_tile(data=idw.o,aes(fill=logp.pred))) #layer valori stimati per pixel
layer2<-c(geom_path(data=lomb_line,aes(long, lat, group = group),colour = "grey20", size=1)) # layer COn COnfini COmune
title<-ggtitle("Concentration map of CO in Lombardy in 2011")
plot+layer1+layer2+scale_fill_gradient(low="#FEEBE2", high="#03730f",limits=c(-1.25, 0.5), breaks=seq(-1.25, 0.5,by=0.25))+coord_equal() + title + labs(fill = "Logaritmo della concentrazione di CO") 

