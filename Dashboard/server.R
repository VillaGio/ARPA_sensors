function(input, output, session){
  
  # Data table Output in Datasets -> Data
  output$dataStaz <- renderDataTable({
    datatable(get(input$datatype),
              options = list(autoWidth = FALSE, 
                             scrollX = TRUE,
                             lengthMenu = list(c(5, 15, 20), c('5', '15', '20') ),
                             bFilter=0))
    })
  
  
  #Histogram in Visualization --> Distribution
  output$histo <-renderPlotly({
    
    # REACTIVE function to get user input for year and pollutant in Distributions dropdown menu
    react_histogram <- reactive({
      return( dati_small %>% filter(anno == input$annoHist, NomeTipoSensoreENG == input$pollHist ))
    })
    
    validate(need(react_histogram()$NomeTipoSensoreENG[1], message = paste0(input$pollHist, " data not provided for year ",input$annoHist, "." )))
    
    
    # UNIT OF MEASURE
    pol <- gsub(" .*$", "", react_histogram()$NomeTipoSensoreENG[1])
    u<- ifelse(pol=="CO", paste0("mg/m", "\U00B3"), paste0("\U03bc", "g/m", "\U00B3"))
    
    
    # PALETTE
    p <- df_max_small[2, react_histogram()$NomeTipoSensoreENG[1]]
    m <- ifelse(p == "OrRd",6, ifelse(p == "YlOrBr", 5, 3) )
    p <- brewer.pal(6, p)[m]
    #p <- "#2b3e50" #if we want same color for all
    
    
    # HISTOGRAM
    gg <- ggplot(react_histogram(),aes(x = react_histogram()$valore, color = 'density')) +  
      geom_histogram(aes(y = ..density..), fill = "white", alpha = 0.2, bins =30) + 
      scale_x_continuous(limits = c(0,as.double(df_max_small[1, react_histogram()$NomeTipoSensoreENG[1]]))) +
      geom_density(color = p, 
                   fill = p, alpha = 0.30) + 
      ggtitle(paste0("Histogram of average values for ", pol, " in year ", react_histogram()$anno[1]))+
      ylab("") + 
      xlab("")  + 
      theme(plot.title = element_text(size = 9, hjust = 0.5),
            axis.text=element_text(size=6),
            axis.title=element_text(size=6),
            legend.position="none",
            panel.grid.major = element_blank(), 
            panel.grid.minor = element_blank(),
            panel.background = element_blank()) +
      scale_color_manual(values = c('density' ="#2b3e50"))
    
    
    ggplotly(gg)%>% 
      style(hoverinfo="none")%>%
      config(displaylogo = FALSE, modeBarButtonsToRemove = c("zoomIn2d", "zoomOut2d", "toImage", "zoom2d", "autoScale2d", "lasso2d", "select2d" ))%>%
      layout(xaxis = list(   
        title=paste0(pol, " - ", u),
        zerolinecolor = '#ffff',   
        zerolinewidth = 2,
        font= list(size = 8)
        ),
        yaxis = list(   
          title='Density', 
          zerolinecolor = '#ffff',   
          zerolinewidth = 2),
        font= list(size = 8)
        )
    
  })
  
  
  
  # Stacked density plot in Visualization --> Boxplot
  output$stacked <- renderPlotly({
    
    # REACTIVE function to get the user input for pollutant
    react_stacked <- reactive({
      return(
        dati_small %>%
          select(anno, valore, NomeTipoSensoreENG) %>%
          filter(NomeTipoSensoreENG == input$pollHist) %>%
          filter(anno == "2021" | anno == "2019" | anno == "2015" | anno == "2011" | anno == "2007" | anno == "2003" | anno == "2001") %>%
          mutate(anno = as.factor(anno), valore = round(valore, 2))
        
        )})
    
    #LABEL
    pol <- gsub(" .*$", "", react_stacked()$NomeTipoSensoreENG[1])
    
    #UNIT OF MEASURE
    u<- ifelse(react_stacked()$NomeTipoSensoreENG[1]=="CO - Carbon Monoxide", paste0("mg/m", "\U00B3"), paste0("\U03bc", "g/m", "\U00B3"))
    
    
    # STACKED density plots
    g <- ggplot(react_stacked(), aes(x = valore)) +
      geom_density(aes(group = anno, color = anno), alpha = 0.5) +
      ggtitle(paste0("Density of ", pol, " across years" ))+
      #scale_color_uchicago()+
      scale_color_brewer(palette = df_max[2, react_stacked()$NomeTipoSensoreENG[1]], "Years",
                         guide = guide_legend(keywidth = 0.2))+
      xlab(paste0(pol, " - ", u)) +
      ylab("Density")  +
      theme(plot.title = element_text(size = 9, hjust = 0.5),
            legend.title=element_text(size=8),
            legend.text = element_text(size=6),
            axis.text=element_text(size=6),
            axis.title=element_text(size=8),
            panel.grid.major = element_blank(),
            panel.grid.minor = element_blank(),
            panel.background = element_blank())

    ggplotly(g, tooltip = "text")%>%
      config(displaylogo = FALSE, modeBarButtonsToRemove = c("zoomIn2d", "zoomOut2d", "toImage", "zoom2d", "autoScale2d","hoverCompareCartesian", "hoverClosestCartesian"))%>%
      layout(legend = list(
        orientation = "v", x=0.75, tracegroupgap=1
      )
      )
    
  })
  
  
  # Boxplot in Visualization -> Boxplot
  output$boxplot <- renderPlotly({
    
    react_boxplot <- reactive({return(
      dati_small %>%
        select(anno, valore, NomeTipoSensoreENG) %>%
        filter(NomeTipoSensoreENG == input$pollBoxplot) %>%
        mutate(valore = round(valore,2)))
    })
    
    #LABEL
    pol <- gsub(" .*$", "", react_boxplot()$NomeTipoSensoreENG[1])
    
    # UNIT OF MEASURE
    u<- ifelse(react_boxplot()$NomeTipoSensoreENG[1]=="CO - Carbon Monoxide", paste0("mg/m", "\U00B3"), paste0("\U03bc", "g/m", "\U00B3"))
    
    # PALETTE
    col <- c("#000000", "#0c1821", "#22333b", "#1b2a41", "#495867", "#2f3e46", "#354f52", "#52796f", "#84a98c","#cad2c5","#957186", "#d9b8c4", "#703d57", "#402a2c", "#533e2d", "#250902", "#38040e", "#640d14", "#800e13", "#ad2831")
    
    # BOXPLOTS
    react_boxplot()%>%
      plot_ly()%>%
      config(displaylogo = FALSE, modeBarButtonsToRemove = c("zoomIn2d", "zoomOut2d", "toImage", "pan2d", "zoom2d")) %>%
      add_boxplot(x = ~as.factor(anno), y = ~valore, color = ~as.factor(anno), colors = col)%>%
      layout(title = list(text = paste0("Boxplot of ",pol, " distribution across years"), font = list(size= 12)), 
             xaxis = list(title = list(text = "Year", font = list(size= 12)),
                          tickangle=45, tickfont = list(size = 10)), 
             yaxis = list(title = list(text = paste0(pol," ", u),font = list(size= 12)),
                          tickfont = list(size = 10)),
             legend = list(title = list(text= "<b> Years </b>"), font = list(size = 11)))
      
  })
  
  
  
  
  # Correlation matrix on years in VIsualization -> Correlation Matrix
  output$corrYear <- renderPlot({
    
    react_corr_year  <- reactive({
      return(
        dati_small %>%
          select(NomeTipoSensoreENG, valore) %>%
          filter(NomeTipoSensoreENG == input$pollCorr))
    })
    
    #LABEL
    pol <- gsub(" .*$", "", react_corr_year()$NomeTipoSensoreENG[1])
    
    
    # Create correlation matrix for years
    df_corr_year <- cor(react_corr_year(), use="complete", method = "pearson")
    ggcorrplot(df_cor_year, method = "square", type = "lower", title = paste0("Year correlation based on ", pol ), ggtheme = theme_tufte(), show.diag = F, colors = c("#154c79", "#e8dbd6", "#901736") )
  
    })
  
  
  # Correlation matrix on pollutants in VIsualization -> Correlation Matrix
  output$corrPoll <- renderPlot({
    
    react_corr_poll  <- reactive({
      return(
        dati_small %>%
          select(NomeTipoSensoreENG, valore, anno) %>%
          filter(anno == input$yearCorr))
    })

    # Create correlation matrix for years
    df_corr_poll <- cor(react_corr_poll(), use="complete", method = "pearson")
    ggcorrplot(df_cor_poll, method = "square", type = "lower", title = paste0("Pollutant correlation in year ", input$yearCorr ), ggtheme = theme_tufte(), show.diag = F, colors = c("#154c79", "#e8dbd6", "#901736") )
    
  })
  
  
  
  
  
  # Plot Output in Interactive Maps -> Pollutants Concentration
  output$concentration <- renderLeaflet({
    
    # REACTIVE function to get user input in Pollutants Concentration
    user_select <- reactive({
      w <- df_all %>% filter(anno == input$year, NomeTipoSensoreENG == input$pollutant )
      return(w)
    })
  
    validate(need(user_select()$NomeTipoSensoreENG[1], message = paste0(input$pollutant, " data not provided for year ",input$year, "." )))
    
  
      #UNIT OF MEASURE
    u<- ifelse(user_select()$NomeTipoSensoreENG[1]=="CO - Carbon Monoxide", paste0("mg/m", "\U00B3"), paste0("\U03bc", "g/m", "\U00B3"))
    
    #LABELS
    labels <- paste(
      "<strong>Province: </strong>",
      user_select()$ProvinciaLong , "</br>",
      "<strong>Pollutant: </strong>",
      user_select()$NomeTipoSensoreENG, "</br>",
      "<strong>Value: </strong>",
      round(user_select()$valore, 2)) %>%
      lapply(htmltools::HTML)
    
    #PALETTE
    m <- df_max[1, user_select()$NomeTipoSensoreENG]
    m <-m[1,1]
    p <- df_max[2, user_select()$NomeTipoSensoreENG]
    p <- p[1,1]
    pal = colorBin(palette = p, 9, domain = 0:m )
    
    
    #MAP
    user_select()%>%
      st_transform(crs = "+init=epsg:4326") %>%
      leaflet() %>%
      setView(lat = 45.4791, lng= 9.8452, zoom = 7) %>%
      addProviderTiles(providers$CartoDB.Positron) %>%
      addPolygons(label = labels, 
                  stroke = FALSE,
                  smoothFactor = 0.7,
                  opacity = 1,
                  fillOpacity = 0.4,
                  fillColor = ~pal(valore),
                  highlightOptions = highlightOptions(weight = 6,
                                                      fillOpacity = 1,
                                                      color = "black",
                                                      opacity =1,
                                                      bringToFront = TRUE))%>%
      addLegend("bottomright",
                pal = pal,
                values = ~ valore,
                title = paste0("Concentration (", u, ")"),
                opacity = 0.7)
  })
  
  
  
  # Plot Output in Interactive Maps -> Sensors Position
  output$stations <- renderLeaflet({
    
    
    #REACTIVE function returning the subset of stations to display 
    user_select_staz <- reactive(return(staz[staz$NomeTipoSensoreENG %in% input$checkGroup, ]))
    
    #LABELS
    lab = paste(
      "<strong>Province: </strong>",
      user_select_staz()$ProvinciaLong , "</br>",
      "<strong>Station Name: </strong>",
      user_select_staz()$NomeStazione, "</br>",
      "<strong>Pollutant: </strong>",
      user_select_staz()$NomeTipoSensoreENG) %>%
      lapply(htmltools::HTML)
    
    #PALETTE
    pals = c("#A6CEE3", "#1F78B4","#B2DF8A", "#33A02C", "#FB9A99", "#E31A1C", "#FDBF6F", "#FF7F00", "#CAB2D6", "#FFFF99", "#B15928", "#B2182B", "#1A9850", "#C51B7D")
    
    #MAP
    staz%>%
      leaflet() %>%
      setView(lat = 45.4791, lng= 9.8452, zoom = 7) %>%
      addProviderTiles(providers$CartoDB.Positron) %>%
      #addCircleMarkers(data = staz[staz$NomeTipoSensoreENG=="PM10", ], lat= ~lat, lng = ~lng, radius = 2.5, fill=T, opacity = 0.8, weight=2, color = pals(staz$NomeTipoSensoreENG[staz$NomeTipoSensoreENG=="PM10"]),label= lab, group = "PM10" ) %>%
      
      addLegend("bottomright",
                colors = pals,
                labels = sort(unique(staz$NomeTipoSensoreENG)),
                title = "Pollutants",
                opacity = 0.7)
  })
  
  
  
  observeEvent(input$checkGroup, {
    
    #REACTIVE function returning the subset of stations to display 
    user_select_staz <- reactive(return(staz[staz$NomeTipoSensoreENG %in% input$checkGroup, ]))
    
    #LABELS 
    popups <- paste(
      "<strong>Province: </strong>",
      user_select_staz()$ProvinciaLong , "</br>",
      "<strong>Station Name: </strong>",
      user_select_staz()$NomeStazione, "</br>",
      "<strong>Pollutant: </strong>",
      user_select_staz()$NomeTipoSensoreENG) %>%
      lapply(htmltools::HTML)
    
    #MAP
    leafletProxy("stations", data =staz) %>%
      clearMarkers()%>%
      addCircleMarkers(lng =  ~user_select_staz()$lng,
                       lat = ~user_select_staz()$lat,
                       group = "Stations",
                       label = popups,
                       radius = 2.5, fill=T, opacity = 0.8, weight=2,
                       color = user_select_staz()$color) %>%
      
      addLayersControl(
        overlayGroups = c("Stations"),
        options = layersControlOptions(collapsed = TRUE))
  })

  
}