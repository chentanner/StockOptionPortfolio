package model.csvtransform.parsers;

import model.valuation.AbstractValuation;
import model.valuation.SaleValuation;

import java.io.IOException;
import java.math.BigDecimal;

public class SaleValuationRecordParser extends AbstractValuationRecordParser{

    public AbstractValuation processLine (String [] splitLine) throws IOException {
        if(splitLine.length != 5){
            throw new IOException("Invalid valuation context values");
        }

        SaleValuation valuation = new SaleValuation();
        super.parseBaseProperties(splitLine, valuation);

        try{
            valuation.setAmountSold(Integer.parseInt(splitLine[3]));
            valuation.setSalePrice(BigDecimal.valueOf(Double.parseDouble(splitLine[4])));
        } catch (Exception e) {
            throw new IOException("Couldn't parse valuation date and price.");
        }

        return valuation;
    }
}
