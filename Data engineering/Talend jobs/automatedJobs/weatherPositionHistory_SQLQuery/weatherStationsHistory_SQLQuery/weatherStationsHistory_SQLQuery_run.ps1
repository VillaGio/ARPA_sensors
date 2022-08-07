$fileDir = Split-Path -Parent $MyInvocation.MyCommand.Path
cd $fileDir
java '-Dtalend.component.manager.m2.repository=%cd%/../lib' '-Xms256M' '-Xmx4096M' -cp '.;../lib/routines.jar;../lib/log4j-slf4j-impl-2.13.2.jar;../lib/log4j-api-2.13.2.jar;../lib/log4j-core-2.13.2.jar;../lib/log4j-1.2-api-2.13.2.jar;../lib/commons-collections-3.2.2.jar;../lib/jsr311-api-1.1.1.jar;../lib/jboss-marshalling-river-2.0.12.Final.jar;../lib/jboss-marshalling-2.0.12.Final.jar;../lib/jersey-client-1.19.jar;../lib/jersey-core-1.19.jar;../lib/advancedPersistentLookupLib-1.3.jar;../lib/accessors-smart-2.4.7.jar;../lib/dom4j-2.1.3.jar;../lib/json-smart-2.4.7.jar;../lib/slf4j-api-1.7.29.jar;../lib/trove.jar;../lib/json-path-2.1.0.jar;../lib/postgresql-42.2.14.jar;../lib/crypto-utils-0.31.12.jar;weatherstationshistory_sqlquery_0_1.jar;' arpa_data.weatherstationshistory_sqlquery_0_1.weatherStationsHistory_SQLQuery --context=Default $args
