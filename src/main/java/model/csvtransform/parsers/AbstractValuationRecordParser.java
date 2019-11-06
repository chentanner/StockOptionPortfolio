package model.csvtransform.parsers;

import model.valuation.AbstractValuation;

import java.io.IOException;

public abstract class AbstractValuationRecordParser implements ValuationRecordParser {

    protected void parseBaseProperties(String [] splitLine, AbstractValuation valuation) throws IOException {
        try{
            valuation.setEmployeeId(splitLine[1]);
            valuation.setRecordDate(DateParser.parseDate(splitLine[2]));
        } catch (Exception e) {
            throw new IOException("Couldn't parse records, employee ID or record date.");
        }
    }
}
