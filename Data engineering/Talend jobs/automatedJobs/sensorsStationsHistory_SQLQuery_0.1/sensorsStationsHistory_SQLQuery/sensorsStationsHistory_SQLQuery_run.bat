%~d0
cd %~dp0
java -Dtalend.component.manager.m2.repository="%cd%/../lib" -Xms256M -Xmx4096M -cp classpath.jar; arpa_data.sensorsstationshistory_sqlquery_0_1.sensorsStationsHistory_SQLQuery --context=Default %* 