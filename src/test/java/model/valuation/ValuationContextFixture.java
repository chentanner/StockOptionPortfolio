package model.valuation;

import model.csvtransform.parsers.DateParser;

import java.math.BigDecimal;
import java.util.Date;

public class ValuationContextFixture {


    public static ValuationContext CreateValuationContext(){
        return CreateValuationContext(
                DateParser.parseDate("20140101"),
                BigDecimal.valueOf(1.0d)
        );
    }

    public static ValuationContext CreateValuationContext(
            Date valuationDate,
            BigDecimal marketPrice){
        ValuationContext context = new ValuationContext();
        context.setValuationDate(valuationDate);
        context.setMarketPrice(marketPrice);
        return context;
    }
}
