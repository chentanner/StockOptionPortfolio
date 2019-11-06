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
A csv file describing the series of records, market price and evaluationDate needs to be passed to the executable jar. An example file:
```
3
VEST,001B,20120102,1000,0.45
SALE,001B,20120402,500,1.00
PERF,001B,20130102,1.5
20140101,1.00
```
- The first line indicates the number of follow rows which are vesting, performance, and/or sale records
- Vesting records consist of: 
    - ```VEST,<employee ID>,<vestDate>,<vestingUnits>, <grantPrice>```
- Sale records consist of: 
    - ```SALE,<employee ID>,<saleDate>,<amountSold>, <marketPriceSoldAt>```
- Performance records consist of: 
    - ```PERF,<employee ID>,<bonusDate>,<bonusMultiplier>```
- The last line specifies the date of evaluation and the marketPrice used for netting.
    - ```<evaluationDate>,<marketPrice>```
#### Run
When running out of the project directory:
1) ```java -jar build/libs/StockOptionVesting-1.0.jar < input.def```
    - Substitute input.def with a path to any configuration file.
## Output
The generated output will be csv where each line is sorted by employee and each line represents an employee's potential cash to gain and realized cash gains. The line format is described as:
- ```<emplyee ID>,<Potential Cash To Gain>,<Realized Cash Gains>```

###Calculations
The calculations for "potential cash to gain" and "realized cash gains" are the results of rolling up in chronological order, all of the valid VEST, SALE and PERF records that lie before the evaluationDate.
The following sections will out line the impact of these records on the calculated fields.
#### Potential Cash To Gain
This value represents the owned stock and the potential earnings if they are all sold at the current market price. The net potential cash to gain is defined by (marketPrice - weightedAverageGrantPrice) * totalStockCount
- VEST 
    - In this scenario vesting records are assumed to be purchased at the grantPrice if their valuation at the vestDate is positive.  Otherwise, they are valued at 0 and the stock isn't purchased.
    - The vest record's vestingUnits are added to the totalStockCount.
    - The vest record's grantPrice is introduced into the weightedAverageGrantPrice based on the ratio of vestingUnits to totalStockCount.
- PERF
    - The performance records are evaluated after any other record type if they share the same date.
    - The performance record's bonusMultiplier is applied directly to the totalStockCount at that moment in time.
- SALE
    - The sale record specifies selling an amount of stock. If an employee over sells their owned stock, an exception will be thrown indicating an invalid sale.
    - The sale record will reduce the totalStockCount by amountSold.
    
#### Realized Cash Gains
This value represents the amount of cash that has been received as a result of selling stock. This value is calculated by the sum of (marketPriceSoldAt - weightedAverageGrantPrice) * amountSold
- VEST
    - Adds available stock to sell which has an impact on weightedAverageGrantPrice as described in the pervious section.
- PERF
    - Adds available stock to sell which has an impact on weightedAverageGrantPrice.
- SALE
    - The sale record must have the available stock to sell.
    - marketPriceSoldAt is used directly in the summation of Realized Cash Gains.
    - amountSold is used directly in the summation of Realized Cash Gains.