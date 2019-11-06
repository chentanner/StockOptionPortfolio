# Stock Option Portfolio

## Overview

This command line application evaluates a series of vesting, performance, and/or sale records for a given market price and date.

## Setup
#### Requirements
This application requires Java 8+ to compile and run.

#### Build
1) Build the executable jar which can be found at ./build/libs/StockOptionVesting-1.0.jar
    - ```./gradlew jar``` (For windows ```gradlew.bat jar```) 

#### Test
1) The tests are automatically run when calling the jar task, but can also be ran simply by calling test.
    - ```./gradlew test``` (For windows ```gradlew.bat test```) 

#### Configuration
A csv file describing the series of records, market price and evaluation date needs to be passed to the executable jar. An example file:
```
3
VEST,001B,20120102,1000,0.45
SALE,001B,20120402,500,1.00
PERF,001B,20130102,1.5
20140101,1.00
```
- The first line indicates the number of follow rows which are vesting, performance, and/or sale records
- Vesting records consist of: 
    - VEST,\<employee ID\>,\< vest date\>,\<investing units\>, \<grant price\>
- Sale records consist of: 
    - SALE,\<employee ID\>,\< sale date\>,\<amount sold\>, \<sold at market price\>
- Vesting records consist of: 
    - Perf,\<employee ID\>,\< bonus date\>,\<investing units multiplier\>
- The last line specifies the as of date and the market price used for netting.
    - \<evaluation date\>,\<market price\>
#### Run
When running out of the project directory:
1) ```java -jar build/libs/StockOptionVgit remote add origin https://github.com/chentanner/StockOptionVesting.gitesting-1.0.jar < input.def```
    - Substitute input.def with a path to any configuration file.
    
 