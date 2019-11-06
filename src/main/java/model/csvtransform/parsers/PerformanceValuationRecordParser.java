package model.csvtransform.parsers;

import model.valuation.AbstractValuation;
import model.valuation.PerfValuation;

import java.io.IOException;
import java.math.BigDecimal;

public class PerformanceValuationRecordParser extends AbstractValuationRecordParser{

    public AbstractValuation processLine (String [] splitLine) throws IOException {
        if(splitLine.length != 4){
            throw new IOException("Invalid valuation context values");
        }

        PerfValuation valuation = new PerfValuation();
        super.parseBaseProperties(splitLine, valuation);

        try{
            valuation.setPerformanceMultiplier(BigDecimal.valueOf(Double.parseDouble(splitLine[3])));
        } catch (Exception e) {
            throw new IOException("Couldn't parse valuation date and price.");
        }

        return valuation;
    }
}
