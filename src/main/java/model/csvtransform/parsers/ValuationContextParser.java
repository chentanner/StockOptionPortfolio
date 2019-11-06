package model.csvtransform.parsers;

import model.valuation.ValuationContext;

import java.io.IOException;
import java.math.BigDecimal;

public class ValuationContextParser implements ValuationContextCsvParser {

    @Override
    public ValuationContext processLine(String[] splitLine) throws IOException {
        ValuationContext context = new ValuationContext();
        if(splitLine.length != 2){
            throw new IOException("Invalid valuation context values");
        }

        try{
            context.setValuationDate(DateParser.parseDate(splitLine[0]));
            context.setMarketPrice(BigDecimal.valueOf(Double.parseDouble(splitLine[1])));
        } catch (Exception e) {
            throw new IOException("Couldn't parse valuation date and price.");
        }

        return context;
    }
}
