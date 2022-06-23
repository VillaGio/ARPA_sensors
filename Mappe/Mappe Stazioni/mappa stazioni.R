library(shape)
library(maptools)
library(rgdal)
library(spatstat)


lomb <- readOGR('C:\\Users\\stefa\\OneDrive\\Desktop\\Tesi\\REGIONE_LOMBARDIA\\Province_2020.shp', verbose=T)
lomb <- spTransform(lomb, CRS("+proj=longlat +ellps=WGS84 +datum=WGS84"))
stazioni<-read.csv('C:\\Users\\stefa\\OneDrive\\Desktop\\Tesi\\DatiGrezzi\\Stazioni_qualit__dell_aria.csv')
stazioni$NomeTipoSensore <- as.factor(stazioni$NomeTipoSensore)
nlevels(stazioni$NomeTipoSensore)
table(stazioni$NomeTipoSensore)

##pm10
plot(lomb, main="Stazioni per la misurazione di PM10 in lombaridia", col="darkolivegreen1")
points(stazioni$lng[stazioni$NomeTipoSensore=="PM10" | stazioni$NomeTipoSensore=="PM10 (SM2005)"],stazioni$lat[stazioni$NomeTipoSensore=="PM10" | stazioni$NomeTipoSensore=="PM10 (SM2005)"], col='blue',pch=19)
leg.txt<-"PM10"
legend(9.2,44.92,leg.txt,bty="n",pch=19,col="blue")

#Biossido di azoto
plot(lomb, main="Stazioni per la misurazione del Biossido di Azoto in lombaridia", col="darkolivegreen1")
points(stazioni$lng[stazioni$NomeTipoSensore=="Biossido di Azoto"],stazioni$lat[stazioni$NomeTipoSensore=="Biossido di Azoto"], col='blue',pch=19)
leg.txt<-"Biossido di Azoto"
legend(9.2,44.92,leg.txt,bty="n",pch=19,col="blue")

#Ossido di azoto
plot(lomb, main="Stazioni per la misurazione di Ossidi di Azoto in lombaridia", col="darkolivegreen1")
points(stazioni$lng[stazioni$NomeTipoSensore=="Ossidi di Azoto"],stazioni$lat[stazioni$NomeTipoSensore=="Ossidi di Azoto"], col='blue',pch=19)
leg.txt<-"Ossidi di Azoto"
legend(9.2,44.92,leg.txt,bty="n",pch=19,col="blue")

#Monossido di Carbonio
plot(lomb, main="Stazioni per la misurazione di Monossido di Carbonio in lombaridia", col="darkolivegreen1")
points(stazioni$lng[stazioni$NomeTipoSensore=="Monossido di Carbonio"],stazioni$lat[stazioni$NomeTipoSensore=="Monossido di Carbonio"], col='blue',pch=19)
leg.txt<-"Monossido di Carbonio"
legend(9.2,44.92,leg.txt,bty="n",pch=19,col="blue")

#Ozono
plot(lomb, main="Stazioni per la misurazione di Ozono in lombaridia", col="darkolivegreen1")
points(stazioni$lng[stazioni$NomeTipoSensore=="Ozono"],stazioni$lat[stazioni$NomeTipoSensore=="Ozono"], col='blue',pch=19)
leg.txt<-"Ozono"
legend(9.2,44.92,leg.txt,bty="n",pch=19,col="blue")

#Particelle sospese PM2.5
plot(lomb, main="Stazioni per la misurazione di Particelle sospese PM2.5 in lombaridia", col="darkolivegreen1")
points(stazioni$lng[stazioni$NomeTipoSensore=="Particelle sospese PM2.5"],stazioni$lat[stazioni$NomeTipoSensore=="Particelle sospese PM2.5"], col='blue',pch=19)
leg.txt<-"Particelle sospese PM2.5"
legend(9.2,44.92,leg.txt,bty="n",pch=19,col="blue")

#Particolato Totale Sospeso
plot(lomb, main="Stazioni per la misurazione di Particolato Totale Sospeso in lombaridia", col="darkolivegreen1")
points(stazioni$lng[stazioni$NomeTipoSensore=="Particolato Totale Sospeso"],stazioni$lat[stazioni$NomeTipoSensore=="Particolato Totale Sospeso"], col='blue',pch=19)
leg.txt<-"Particolato Totale Sospeso"
legend(9.2,44.92,leg.txt,bty="n",pch=19,col="blue")

#Altre sostanze.1
plot(lomb, main="Stazioni per la misurazione della concentrazione di Ammoniaca, Arsenico, Benzene, Benzopirene in lombaridia", col="darkolivegreen1")
points(stazioni$lng[stazioni$NomeTipoSensore=="Ammoniaca" | stazioni$NomeTipoSensore=="Arsenico" | stazioni$NomeTipoSensore=="Benzene" | stazioni$NomeTipoSensore=="Benzo(a)pirene"],stazioni$lat[stazioni$NomeTipoSensore=="Ammoniaca" | stazioni$NomeTipoSensore=="Arsenico" | stazioni$NomeTipoSensore=="Benzene" | stazioni$NomeTipoSensore=="Benzo(a)pirene"], col=c("blue","red","pink","yellow"),pch=19,cex=1.3)
leg.txt<-c("Ammoniaca", "Arsenico", "Benzene", "Benzopirene")
legend(10.6,46.23,leg.txt,bty="n",pch=c(19,19,19,19),col=c("blue","red","pink","yellow"))

#Altre sostanze.2
plot(lomb, main="Stazioni per la misurazione della concentrazione di Carbonio Elementare, Cadmio, Monossido di Azoto, Nikel, Piombo in lombaridia", col="darkolivegreen1")
points(stazioni$lng[stazioni$NomeTipoSensore=="BlackCarbon" | stazioni$NomeTipoSensore=="Cadmio" | stazioni$NomeTipoSensore=="Monossido di Azoto" | stazioni$NomeTipoSensore=="Nikel" | stazioni$NomeTipoSensore=="Piombo"],stazioni$lat[stazioni$NomeTipoSensore=="BlackCarbon" | stazioni$NomeTipoSensore=="Cadmio" | stazioni$NomeTipoSensore=="Monossido di Azoto" | stazioni$NomeTipoSensore=="Nikel" | stazioni$NomeTipoSensore=="Piombo"], col=c("blue","red","pink","yellow","forestgreen"),pch=19,cex=1.3)
leg.txt<-c("Carbonio Elementare", "Cadmio", "Monossido di Azoto", "Nikel","Piombo")
legend(10.6,46.23,leg.txt,bty="n",pch=c(19,19,19,19,19),col=c("blue","red","pink","yellow","forestgreen"))





