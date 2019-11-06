package model.csvtransform.parsers;

import model.valuation.AbstractValuation;
import model.valuation.VestingValuation;

import java.io.IOException;
import java.math.BigDecimal;

public class VestingValuationRecordParser extends AbstractValuationRecordParser{

    public AbstractValuation processLine (String [] splitLine) throws IOException {
        if(splitLine.length != 5){
            throw new IOException("Invalid valuation context values");
        }

        VestingValuation valuation = new VestingValuation();
        super.parseBaseProperties(splitLine, valuation);

        try{
            valuation.setUnitCount(Integer.parseInt(splitLine[3]));
            valuation.setGrantPrice(BigDecimal.valueOf(Double.parseDouble(splitLine[4])));
        } catch (Exception e) {
            throw new IOException("Couldn't parse valuation date and price.");
        }

        return valuation;
    }
}
