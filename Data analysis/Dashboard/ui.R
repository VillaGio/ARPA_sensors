library(shiny)            # shiny features
library(shinydashboard)   # shiny dashboard functions
library(DT)               # for DT tables
library(dplyr)            # for pipe operator & data manipulations
library(plotly)           # for data visualization and plots using plotly 
library(ggplot2)          # for data visualization & plots using ggplot2
library(ggthemes)         # for ggplot themes
library(ggtext)           # beautifying text on top of ggplot
library(maps)             # for USA states map - boundaries used by ggplot for mapping
library(ggcorrplot)       # for correlation plot
library(shinycssloaders)  # to add a loader while graph is populating
library(leaflet)          # for leaflet maps
library(sf)               # to handle shp files
library(RColorBrewer)     # for color palettes
library(shinyBS)          # for hover info text button
library(data.table)       # to display model results table 
library(tensorflow)       # to load keras model
library(keras)            # to load keras model
library(ggsci)
#install.packages("BiocManager")
#BiocManager::install("hdf5r")
#library(rhdf5)
#library(hdf5r)
# https://fontawesome.com/v4/icons/

dashboardPage(
  
  ## Header
  skin = "black",
  dashboardHeader(title="Decay of pollutants in Lombardy", titleWidth = 650, 
                  tags$li(class="dropdown",tags$a(href="https://github.com/VillaGio/ARPA_sensors", icon("github"), "Source Code", target="_blank"))
                  ),
  
  ## Sidebar
  dashboardSidebar(
    
    sidebarMenu(id = "sidebar",
                menuItem("Datasets", tabName = "data", icon = icon("database")),
                menuItem("Visualizations", tabName = "viz", icon=icon("chart-line")),
                
                # Conditional Panel for conditional widget appearance
                # Filter should appear only for the visualization menu and selected tabs within it
                #conditionalPanel("input.sidebar == 'viz' && input.t2 == 'distro'", selectInput(inputId = "var1" , label ="Select the Variable" , choices = c1)),
                #conditionalPanel("input.sidebar == 'viz' && input.t2 == 'trends' ", selectInput(inputId = "var2" , label ="Select the Arrest type" , choices = c2)),
                #conditionalPanel("input.sidebar == 'viz' && input.t2 == 'relation' ", selectInput(inputId = "var3" , label ="Select the X variable" , choices = c1, selected = "Rape")),
                #conditionalPanel("input.sidebar == 'viz' && input.t2 == 'relation' ", selectInput(inputId = "var4" , label ="Select the Y variable" , choices = c1, selected = "Assault")),
                
                menuItem("Interactive Maps", tabName = "map", icon=icon("map")),
                menuItem("Predictions", tabName = "pred", icon=icon("spinner"))
                )
    ),
  
  
  ## Body content
  dashboardBody(
    
    tabItems(
      
      ## First tab item for Dataset
      tabItem(tabName = "data", 
              tabBox(id="t1", width = 12, 
                     
                     tabPanel("About", icon=icon("address-card"),
                              fluidRow(
                      
                              #Markdown to display 
                              box(div(includeMarkdown("./data/about.md"), 
                              align="justify"), width= 8, solidHeader = TRUE),
                              
                              box(tags$img(src="arpal.jpg", width =400 , height = 150), width = 4, solidHeader = TRUE,
                                   tags$br() , 
                                   tags$a(href="https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.svilupposostenibile.regione.lombardia.it%2Fit%2Fattachments%2Ffile%2Fview%3Fhash%3Dc9cc97a1720adf94725be153f05a2c6a%26canCache%3D1&imgrefurl=https%3A%2F%2Fwww.svilupposostenibile.regione.lombardia.it%2Fit%2Fb%2F882%2Farpalombardia&tbnid=Jiou7yLW4xzIDM&vet=12ahUKEwje8vuNg7D6AhVLnf0HHWQ1BRcQMygBegUIARDAAQ..i&docid=FIXHoeeXvxuKqM&w=1000&h=326&q=arpa%20lombardia%20logo&ved=2ahUKEwje8vuNg7D6AhVLnf0HHWQ1BRcQMygBegUIARDAAQ",
                                          "Img link"), align = "right")
                              )
                              ), 
                     
                     tabPanel("Data", icon = icon("table"),
                              
                              fluidRow(
                              
                              box(
                              #Drop-down menu with pollutants
                              selectInput("datatype", 
                                          label = "Select dataset:",
                                          choices = list("Pollutants Dataset" = "sensors", 
                                                         "Weather Dataset" = "weather", 
                                                         "Sensors Stations Position Dataset"= "stations_sens",
                                                         "Weather Stations Position Dataset"= "stations_weather"
                                                         )), width = 4,  solidHeader = TRUE),
                              box(tipify(actionButton("btn3", img(src="https://www.svgrepo.com/show/146222/info.svg",height='15', width='15'), style="position: absolute; right: 5px; top: 5px; background-color:white; border-color:white;"), "Data were loaded as of 18/07/2022 from the ARPA APIs provided in About, actual data present in the API may change in the future.", 
                                         placement="bottom", trigger = "click"), width = 8,  solidHeader = TRUE),
                              
                              #Dataset to display
                              box(div(dataTableOutput("dataStaz"), style = "font-size:90%"), width = 12, solidHeader = TRUE))
              ))),
      
      ## Second Tab Item for Visualization
      tabItem(tabName = "viz", 
              tabBox(id="t2",  width=12, 
                     #tabPanel("Descriptives"),
                     tabPanel("Distribution", icon=icon("chart-bar"),
                              tags$head(tags$style(HTML(".js-irs-0 .irs-single, .js-irs-0 .irs-bar-edge, .js-irs-0 .irs-bar {background: #2b3e50; border-top: #2b3e50; border-bottom: #2b3e50
                                              .tooltip > .tooltip-inner {background-color: #EEEDE7; color: black; padding: 15px; font-size: 10px;}}"))),
                              
                              fluidRow(
                                box(tipify(actionButton("btn1", img(src="https://www.svgrepo.com/show/146222/info.svg",height='15', width='15'), style="position: absolute; right: 5px; top: 0px; background-color:white; border-color:white;"), "Plots on the right show the distribution of the daily average recorded values for a given pollutant: the higher the curve or histogram is on a certain value of the x-axis, the more records were recorded around that value. Histogram: reports the distribution of the daily average values recorded for a given pollutant in a given year. Densplot: reports the distributions of the daily average values recorded for a given pollutant over time.", 
                                           placement="bottom", trigger = "click"),
                              
                                              sliderInput("annoHist", label = "Year:", min = 2001, max = 2021, sep = "", value = 2020), 
                                              selectInput("pollHist", label = "Pollutant:", choices = list("PM10", "PM2.5", "CO - Carbon Monoxide","O3 - Ozone", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide")),width = 4,  solidHeader = TRUE),
                                
                                tabBox(title = "", side = "right", width = 8, selected = "Histogram",
                                           tabPanel("Densplot", withSpinner(plotlyOutput("stacked"), type = 6, size = 0.5, color = "#2b3e50"), width = 8),
                                           tabPanel("Histogram", withSpinner(plotlyOutput("histo"), type = 6, size = 0.5, color = "#2b3e50"), width = 8)
                                )
                              )),
                     
                     tabPanel("Boxplot",
                              title = div(img(src="https://www.svgrepo.com/show/370122/graph-boxplot.svg",height='25', width='25'), "Boxplot"),
                              fluidRow(
                          
                                column(4, box(tipify(actionButton("btn2", img(src="https://www.svgrepo.com/show/146222/info.svg",height='15', width='15'), style="position: absolute; right: 5px; top: 0px; background-color:white; border-color:white;"), "Plot on the right shows the boxplots of the distributions of a given pollutant from year 2001 to 2021. The box corresponds to 75% of the observations, i.e., 75% of the observations assumes values between the range corresponding to the vertical side of the box; the line inside the box correspond to the median value of the distribution; circles correspond to outliers, i.e., observations with extreme high (if above the box) or low (if below the box) values.", 
                                                     placement="bottom", trigger = "click"),
                                              selectInput("pollBoxplot", label = "Pollutant:", choices = list("PM10", "PM2.5", "CO - Carbon Monoxide","O3 - Ozone", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide")),
                                              width = NULL, solidHeader = TRUE)),
                                box(withSpinner(plotlyOutput("boxplot"), type = 6, size = 0.5, color = "#2b3e50"), solidHeader = TRUE, width = 8)
                              )
                     ),
                     
                     tabPanel("Correlation Matrix",icon=icon("braille"),
                              tags$style(HTML(".js-irs-1 .irs-single, .js-irs-1 .irs-bar-edge, .js-irs-1 .irs-bar {background: #2b3e50; border-top: #2b3e50; border-bottom: #2b3e50}")),
                              
                              fluidRow(
                                column(6, box(selectInput("yearCorr", label = "Year:", choices = list("Global", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021")), width = NULL, solidHeader = TRUE),
                                       box(withSpinner(plotlyOutput("corrYear"), type = 6, size = 0.5, color = "#2b3e50"), width = NULL, solidHeader = TRUE)),
                               
                                column(6, box(selectInput("pollCorr", label = "Pollutant:", choices = list("PM10", "PM2.5", "CO - Carbon Monoxide","O3 - Ozone", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide")), width = NULL, solidHeader = TRUE),
                                          box(withSpinner(plotlyOutput("corrPoll"), type = 6, size = 0.5, color = "#2b3e50"), width = NULL, solidHeader = TRUE))
                             
                              )
                     )
                              )
                     
                
              ),
              
             
      ## Third Tab Item for Interactive Maps
      tabItem(tabName = "map",
              tabBox(id="t3", width = 12, 
                     tabPanel("Pollutant Concentration", 
                              icon=icon("chart-area"),
                              tags$style(HTML(".js-irs-2 .irs-single, .js-irs-2 .irs-bar-edge, .js-irs-2 .irs-bar {background: #2b3e50; border-top: #2b3e50; border-bottom: #2b3e50}")),
                              
                              fluidRow(
                                #column(3, "Some description"),
                                column(4, box(sliderInput("year", label = "Year:", min = 2001, max = 2021, sep = "", value = 2020), 
                                              selectInput("pollutant", label = "Pollutant:", choices = list("PM10", "PM2.5", "CO - Carbon Monoxide","O3 - Ozone", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide")), width = NULL, solidHeader = TRUE)),
                                column(8, box(leafletOutput("concentration"), width = NULL, solidHeader = TRUE))
                                )
                              ),
                      
                      tabPanel(" Sensors Position", 
                               icon = icon("map-marker", lib = "glyphicon"),
                               #Checkbox for pollutants and stations map
                               #tags$head(tags$style(HTML(".multicol{font-size:12px;height:auto;-webkit-column-count: 2;-moz-column-count: 2;column-count: 2;}"))),
                                tags$head(tags$style(HTML('.box{-webkit-box-shadow: none; -moz-box-shadow: none;box-shadow: none;}'))),
                        
                        fluidRow( tags$style(".nav-tabs-custom {box-shadow: none;};"), 
                          
                          tabBox(title = "", side = "left", width = 12, selected = "Pollutants",
                                 tabPanel("Pollutants",
                                   #column(3, "Some description"),
                                   column(3, box(checkboxGroupInput("checkGroupS",
                                                                    label="Pollutant(s): ",
                                                                    choices = list("Ammonia", "Arsenic", "Benzene", "Black Carbon", "Cadmium", "Carbon Monoxide", "Lead", "Nickel","Nitric Oxide", "Nitrogen Dioxide", "Nitrogen Monoxide", "Ozone", "PM10", "PM2.5")),  width = NULL, solidHeader = TRUE)),
                                   column(1, box(actionButton(inputId="resetMapS",
                                                          label="Reset", 
                                                          style="color: #fff; background-color: #2b3e50; border-style: solid; border-color: #2b3e50; margin: 5px"), width = NULL, solidHeader = TRUE)),
                                   column(8, box(leafletOutput("stationsS"), width = NULL, solidHeader = TRUE))
                                 ),
                                 tabPanel("Weather",
                                   #column(3, "Some description"),
                                   column(3, box(checkboxGroupInput("checkGroupW",
                                                                    label="Weather condition(s): ",
                                                                    choices = list("Global Radiation", "Hydrometric Level", "Rain", "Relative Humidity","Snow Level", "Temperature", "Wind Direction", "Wind Speed")),  width = NULL, solidHeader = TRUE)),
                                   column(1, box(actionButton(inputId="resetMapW",
                                                          label="Reset", 
                                                          style="color: #fff; background-color: #2b3e50; border-style: solid; border-color: #2b3e50; margin: 5px"),  width = NULL, solidHeader = TRUE)),
                                   column(8, box(leafletOutput("stationsW"), width = NULL, solidHeader = TRUE))
                                 )
                          )
                            )
                        )
         
               )),

        ## Fourth tab item for Model predictions
        tabItem(tabName = "pred", 
                
                headerPanel(tags$label(h3('Get PM10 levels predictions'))),
                
                sidebarPanel( width = 4,
                              tags$head(
                                tags$style(
                                  HTML('label { font-size:88%;}
                                      #rain{height: 20px}
                                      #temp{height: 20px}
                                      #wind{height: 20px}
                                      #ozone{height: 20px}
                                      #pm25{height: 20px}'))),
                #HTML("<h6>Input parameters</h6>"),
                  
                  tags$label(h4('Input parameters')),
                  numericInput("rain", 
                               label = "Rain level (mm)", 
                               value = 4),
                  numericInput("temp", 
                               label = "Temperature (C)", 
                               value = 11),
                  numericInput("wind", 
                               label = "Wind (m/s)", 
                               value = 5),
                  numericInput("ozone", 
                               label = paste0("Ozone level  ", "(\U03bc", "g/m", "\U00B3)"), 
                               value = 55),
                  numericInput("pm25", 
                               label = paste0("PM2.5 level  ", "(\U03bc", "g/m", "\U00B3)"), 
                               value = 15),
                  
                  actionButton("submitbutton", "Submit", 
                               class = "btn btn-primary", style="color: #fff; background-color:#2b3e50; border-color:#2b3e50;")
                ),
                
                mainPanel(
                  #tags$label(h3('Results')), # Status/Output Text Box
                  p(style="font-size:18px; text-align: justify;", "Here you can run simulations using a pretrained linear model to predict the expected level of PM10 by providing some values for the variables specified in the left panel. These are rain level, temperature, wind speed, PM2.5 and ozone levels."),
                  p(style="font-size:18px; text-align: justify;", "Indeed, according to some preliminary analysis, the level of PM10 present in the air seems to be particularly influenced by the presence of PM2.5 and ozone. Also, lower temperatures, stronger wind and heavy rains tend to decrease the quantity of this pollutants in the environment."),
                  p(style="font-size:18px; text-align: justify;", "To help you, the default provided values represent the daily average values registred in 2021 for each variable."),
                  br(),
                  p(style="font-size:18px;",strong("Predicted PM10 value: ")),
                  div(textOutput('tabledata'), style="font-size:18px;") # Prediction results table
                
                  
                )
                ))
              )
           )