dati = dati_start<-read.csv("datimodelli.csv")
dati<-dati[,2:11]
str(dati)
dati_start$pm10_daybefore<-c(dati_start$pm10[1],dati_start$pm10[1:length(dati_start$pm10)-1])
samp <- sample(nrow(dati_start),3874*0.7)
dati<-dati_start[samp,]
test<-dati_start[-samp,]
# Modello di partenza -----------------------------------------------------


mod1<-lm(pm10 ~ pioggia + temperatura + vento + NO2 + NO + CO + O3 + pm2p5 + pm10_daybefore, data=dati)
summary(mod1) # brutto ma si sapeva

par(mfrow=c(2,2))
plot(mod1) # faccina che ha paura
par(mfrow=c(1,1))


# Akaike model selection --------------------------------------------------

library(MASS)
modaik<-stepAIC(mod1, direction = "both") # elimino NO e CO

modaic<-lm(pm10 ~ pioggia + temperatura + vento + NO2 + O3 + pm2p5 + pm10_daybefore, data=dati)
summary(modaic) # le variabili sono tutte significative stavolta

par(mfrow=c(2,2))
plot(modaic) # faccina che ha ancora paura
par(mfrow=c(1,1))


# Collinearità ------------------------------------------------------------

library(mctest)
imcdiag(modaic) # via NO2 
mod2<-lm(pm10 ~ pioggia + temperatura + vento + O3 + pm2p5+ pm10_daybefore, data=dati)

imcdiag(mod2) # apposto

summary(mod2)

par(mfrow=c(2,2))
plot(mod2) # faccina la cui paura cresce esponenzialmente
par(mfrow=c(1,1))


# Trasformazione variabile target -----------------------------------------

modbc<-boxcox(mod2)
title("Lambda")

lambda<-modbc$x[which.max(modbc$y)]; lambda # 0.46 non transformiamo



# Outliers ----------------------------------------------------------------

library(car)
influencePlot(modlog, main="Influence plot") # diverse osservazioni influenti

cooksd<-cooks.distance(mod2)
cd<-data.frame(cooksd)

cutoff <- 4/(length(mod2$residuals)-length(mod2$coefficients)-2)

influ <- data.frame(dati[cooksd > cutoff,])
influ

# rimozione outliers

noout<-dati[!(dati$Data %in% influ$Data),]

modnoout <- lm(pm10 ~ pioggia + temperatura + vento + O3 + pm2p5 + pm10_daybefore, data=noout)
summary(modnoout) # r quadro aumenta molto

par(mfrow=c(2,2))
plot(modnoout) 
# rimane eteroschedaticità e il cu-cu non è bellissimo, però molto più robusto rispetto all'inizio
par(mfrow=c(1,1))


# Eteroschedasticità ------------------------------------------------------

ncvTest(modnoout) # Breusch-Pagan test conferma eteroschedasticità
library(lmtest)
bptest(modnoout) # un'altra funzione lo ri-conferma eteroschedasticità

# non si risolve ma si gestisce con un modello gls https://en.wikipedia.org/wiki/Generalized_least_squares
# https://rpubs.com/cyobero/187387

noout$resi <- modnoout$residuals
varfunc.ols <- lm(log(resi^2) ~ pm10, data = noout)
noout$varfunc <- exp(varfunc.ols$fitted.values)
noout.gls <- lm(pm10 ~ pioggia + temperatura + vento + O3 + pm2p5 + pm10_daybefore,
                weights = 1/sqrt(varfunc), data=noout)
summary(noout.gls) 

par(mfrow=c(2,2))
plot(noout.gls) 

par(mfrow=c(1,1))
# non è eccezionale, si può provare eliminando le osservazioni con residuo stanrdadizzato più alto (>3)


# Ulteriore rimozione Outliers --------------------------------------------

noout$standard_res <- rstandard(noout.gls)
noout<-noout[noout$standard_res<2.3,]
# rimozione osservazioni con residuo stanrdadizzato più alto (>3)

noout.gls <- lm(pm10 ~ pioggia + temperatura + vento + O3 + pm2p5+ pm10_daybefore,
                weights = 1/sqrt(varfunc), data=noout)
summary(noout.gls) 

par(mfrow=c(2,2))
plot(noout.gls) 
par(mfrow=c(1,1))

ncvTest(noout.gls) # Breusch-Pagan test conferma omoschedasticità
# insomma questo ultimo passaggio un po' arrangiato però modello fatto top

# Previsioni ----------------------------------------------------------------

prev<-predict(noout.gls, newdata = test)
plot(test$pm10, prev, main="True values against predictions", xlab="true values in µg/m3",
     ylab="fitted values µg/m3")
abline(a=0, b=1, col="red")
leg.txt<-c("Bisector of first quadrant")
legend("topleft",leg.txt,bty="n",lwd=2,col=c("red"))
rsq <- function (x, y) cor(x, y) ^ 2
rsq(test$pm10, prev)
