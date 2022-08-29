%~d0
cd %~dp0
java -Dtalend.component.manager.m2.repository="%cd%/../lib" -Xms1024M -Xmx8129M -cp classpath.jar; arpa_data.deltaingestioncurrentyears_0_1.deltaIngestionCurrentYearS --context=Default %* 
