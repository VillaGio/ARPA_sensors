#!/bin/sh
cd `dirname $0`
ROOT_PATH=`pwd`
java -Dtalend.component.manager.m2.repository=$ROOT_PATH/../lib -Xms256M -Xmx4096M -cp classpath.jar: arpa_data.deltaingestioncurrentyears_0_1.deltaIngestionCurrentYearS --context=Default "$@" 