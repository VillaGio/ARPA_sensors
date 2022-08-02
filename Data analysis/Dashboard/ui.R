library(shiny) # shiny features
library(shinydashboard) # shiny dashboard functions
library(DT)  # for DT tables
library(dplyr)  # for pipe operator & data manipulations
library(plotly) # for data visualization and plots using plotly 
library(ggplot2) # for data visualization & plots using ggplot2
library(ggthemes)   #for ggplot themes
library(ggtext) # beautifying text on top of ggplot
library(maps) # for USA states map - boundaries used by ggplot for mapping
library(ggcorrplot) # for correlation plot
library(shinycssloaders) # to add a loader while graph is populating
library(leaflet) # for leaflet maps
library(sf) # to handle shp files
library(RColorBrewer) # for color palettes
library(ggsci)
# https://fontawesome.com/v4/icons/

dashboardPage(
  
  ## Header
  skin = "black",
  dashboardHeader(title="Analysis on decay of pollutants", titleWidth = 650, 
                  tags$li(class="dropdown",tags$a(href="https://github.com/VillaGio/ARPA_sensors", icon("github"), "Source Code", target="_blank"))
                  ),
  
  ## Siderbar
  dashboardSidebar(
    
    sidebarMenu(id = "sidebar",
                menuItem("Dataset", tabName = "data", icon = icon("database")),
                menuItem("Visualization", tabName = "viz", icon=icon("chart-line")),
                
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
                              
                              #Markdown to display 
                              div(includeMarkdown("./data/about.md"), 
                              align="justify")), 
                     
                     tabPanel("Data", icon = icon("table"),
                              
                              #Drop-down menu with pollutants
                              selectInput("datatype", 
                                          label = "Select dataset:",
                                          choices = list("Pollutants Dataset" = "sensori", "Stations Position Dataset"= "stazioni")),
                              
                              #Dataset to display
                              div(dataTableOutput("dataStaz"), style = "font-size:90%"))
              )),
      
      ## Second Tab Item for Visualization
      tabItem(tabName = "viz", 
              tabBox(id="t2",  width=12, 
                     #tabPanel("Descriptives"),
                     tabPanel("Distribution",
                              tags$style(HTML(".js-irs-0 .irs-single, .js-irs-0 .irs-bar-edge, .js-irs-0 .irs-bar {background: #2b3e50; border-top: #2b3e50; border-bottom: #2b3e50}")),
                              
                              fluidRow(
                                box(sliderInput("annoHist", label = "Year:", min = 2001, max = 2021, sep = "", value = 2020), 
                                              selectInput("pollHist", label = "Pollutant:", choices = list("PM10", "PM2.5", "CO - Carbon Monoxide","O3 - Ozone", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide")),width = 4,  solidHeader = TRUE),
                                
                                tabBox(title = "", side = "right", width = 8, selected = "Histogram",
                                           tabPanel("Densplot", withSpinner(plotlyOutput("stacked"), type = 6, size = 0.5, color = "#2b3e50"), width = 8),
                                           tabPanel("Histogram", withSpinner(plotlyOutput("histo"), type = 6, size = 0.5, color = "#2b3e50"), width = 8)
                                )
                              )),
                     
                     tabPanel("Boxplot",
                              fluidRow(
                                column(4, box(selectInput("pollBoxplot", label = "Pollutant:", choices = list("PM10", "PM2.5", "CO - Carbon Monoxide","O3 - Ozone", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide")),
                                              width = NULL, solidHeader = TRUE)),
                                box(withSpinner(plotlyOutput("boxplot"), type = 6, size = 0.5, color = "#2b3e50"), solidHeader = TRUE, width = 8)
                              )
                     ),
                     
                     tabPanel("Correlation Matrix",
                              tags$style(HTML(".js-irs-1 .irs-single, .js-irs-1 .irs-bar-edge, .js-irs-1 .irs-bar {background: #2b3e50; border-top: #2b3e50; border-bottom: #2b3e50}")),
                              
                              fluidRow(
                                column(6, box(selectInput("yearCorr", label = "Year:", choices = list("Global", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021")), width = NULL, solidHeader = TRUE),
                                          box(plotOutput("corrPoll"), width = NULL, solidHeader = TRUE)),
                               
                                column(6, box(selectInput("pollCorr", label = "Pollutant:", choices = list("PM10", "PM2.5", "CO - Carbon Monoxide","O3 - Ozone", "NO - Nitric Oxide", "NO2 - Nitrogen Dioxide")), width = NULL, solidHeader = TRUE),
                                          box(plotOutput("corrYear"), width = NULL, solidHeader = TRUE))
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
               
                      tabPanel("Sensors Position", 
                               icon = icon("map-marker", lib = "glyphicon"),
                               #Checkbox for pollutants and stations map
                               #tags$head(tags$style(HTML(".multicol{font-size:12px;height:auto;-webkit-column-count: 2;-moz-column-count: 2;column-count: 2;}"))),
                        
                        fluidRow(
                          #column(3, "Some description"),
                          column(4, box(checkboxGroupInput("checkGroup",
                                                       label="Pollutant(s): ",
                                                       choices = list("Ammonia", "Arsenic", "Benzene", "Black Carbon", "Cadmium", "Carbon Monoxide", "Lead", "Nickel","Nitric Oxide", "Nitrogen Dioxide", "Nitrogen Monoxide", "Ozone", "PM10", "PM2.5")), width = NULL, solidHeader = TRUE)),
                          column(8, box(leafletOutput("stations"), width = NULL, solidHeader = TRUE))
                          )
                        )
               ))
                        )
              )
      )
  


