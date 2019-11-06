package model.csvtransform.parsers;

import model.valuation.AbstractRecord;
import model.valuation.PerformanceRecord;

import java.io.IOException;
import java.math.BigDecimal;

public class PerformanceRecordParser extends AbstractRecordParser {

    public AbstractRecord processLine (String [] splitLine) throws IOException {
        if(splitLine.length != 4){
            throw new IOException("Invalid number of performance record properties.");
        }

        PerformanceRecord record = new PerformanceRecord();
        super.parseBaseProperties(splitLine, record);

        try{
            record.setPerformanceMultiplier(BigDecimal.valueOf(Double.parseDouble(splitLine[3])));
        } catch (Exception e) {
            throw new IOException("Couldn't parse performance record's multiplier.");
        }

        return record;
    }
}
