@echo off
chcp 65001 > nul
java -Dfile.encoding=utf-8 -jar -Xms512m -Xmx4096m -XX:ParallelGCThreads=4 -XX:+UseParallelGC volunteer-filling.jar
