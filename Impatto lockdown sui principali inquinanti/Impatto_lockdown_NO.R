library(readr)
library(imputeTS)
library(pracma)
library(dplyr)
library(ggplot2)
library(xts)
library(TSstudio)
library(forecast)
library(aTSA)
library(KFAS)
library(splines)

dati<-read.csv(file ="C:\\Users\\stefa\\Tesi\\serie_NO.csv")
summary(dati)
dati$Data<-as.Date(dati$Data, format =  "%Y-%m-%d")
serie<-xts(dati$Valore, dati$Data)

post_lockdown<-vector()
for (i in seq(1,length(dati$Data),1)){
  if (dati$Data[i]>"2020-03-09") post_lockdown[i]=1
  else post_lockdown[i]=0
  
}
dati$post_lockdown<-post_lockdown
summary(dati)

dati_impatto<-dati[dati$Anno>=2018,]
length(dati_impatto$Data)


lambdal<-BoxCox.lambda(serie, method = "loglik") # -0.1
lambdag<-BoxCox.lambda(serie, method = "guerrero") # -0.24 direi di usare il logaritmo

logserie <- log(serie)
hist(logserie) # non c'è male

ts_plot(logserie)
adf.test(as.ts(logserie)) # si ha

fltr <- c(1/2, rep(1, times = 100), 1/2)/101
trend_100 <- stats::filter(as.vector(dati_impatto$Valore), filter = fltr, method = "convo", 
                           sides = 2)
ts_info(trend_100)
trend_100<-xts(trend_100, dati_impatto$Data)
ts_info(trend_100)
ts_plot(trend_100, title = "Trend con media mobile a 100 unit?")

no_trend<-dati_impatto$Valore-trend_100
ts_plot(no_trend)
length(no_trend)
mean(na.omit(no_trend))

mean(na.omit(no_trend[1461-662:1461]))
mean(na.omit(no_trend[0:1461-662]))

mod<-lm(Valore~Data+post_lockdown, data=dati)
summary(mod)
plot(dati$Data,dati$Valore, type='l')
abline(mod)

dati_prelock<-dati[dati$post_lockdown==0,]
mod_pre<-lm(Valore~Data, data=dati_prelock)
mod_pre

dati_postlock<-dati[dati$post_lockdown==1,]
pre<-predict(mod_pre, data.frame(Data=dati_postlock$Data))
plot(dati_postlock$Data,dati_postlock$Valore, type='l', col="blue", lwd=2, 
     main="Post lockdown period, estimated mean values against real values",
     xlab="Data", ylab="PM10 daily concentration")
lines(dati_postlock$Data,pre, col="red", lwd=2)
leg.txt<-c("Real Values","Estimated Mean")
legend("topleft",leg.txt,bty="n",lwd=2,col=c("blue","red"))

mean(dati_postlock$Valore)
mean(pre)
boxplot(dati_postlock$Valore,pre, col=c("blue","red"),
        main="Post lockdown period, estimated trend values against real values",
        xlab="Data", ylab="PM10 daily concentration")

t.test(dati_postlock$Valore, pre, "greater") # p-value = 0
# Il lockdown è stato influente ma in negativo


# Dati Destagionalizzati, concettualmente migliore ------------------------



dati_dest<-diff(dati_prelock$Valore,365)
appo<-dati_prelock$Data[0:10661]
mod_pre<-lm(dati_dest~appo)
mod_pre

dati_postlock<-dati[dati$post_lockdown==1,]
pre<-predict(mod_pre, data.frame(appo=dati_postlock$Data[0:297]))


dati_dest<-diff(dati_postlock$Valore,365)
plot(dati_dest, type="l")
tr<-lm(dati_dest~Data[0:297], data=dati_postlock)
summary(tr)
plot(dati_postlock$Data[0:297],dati_dest, type='l', col="blue", lwd=2, 
     main="Post lockdown period, estimated trend against seasonally adjusted trend and values",
     xlab="Data", ylab="PM10 daily concentration")
lines(dati_postlock$Data[0:297],pre, col="red", lwd=2)
abline(tr, col="green", lwd=2)
leg.txt<-c("Real Values","Estimated Trend","Real Trend")
legend("bottomleft",leg.txt,bty="n",lwd=2,col=c("blue","red","green"))


boxplot(dati_dest,pre, col=c("blue","red"),
        main="Post lockdown period, estimated trend values seasonally adjusted real values",
        xlab="Data", ylab="PM10 daily concentration")
leg.txt<-c("Seasonally adjusted Values","Estimated Trend")
legend("bottomright",leg.txt,bty="n",lwd=2,col=c("blue","red"))

t.test(dati_dest, pre, "greater")# cambia nulla


