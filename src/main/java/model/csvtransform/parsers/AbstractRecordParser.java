package model.csvtransform.parsers;

import model.valuation.AbstractRecord;

import java.io.IOException;

public abstract class AbstractRecordParser implements RecordParser {

    protected void parseBaseProperties(String [] splitLine, AbstractRecord record) throws IOException {
        try{
            record.setEmployeeId(splitLine[1]);
            record.setRecordDate(DateParser.parseDate(splitLine[2]));
        } catch (Exception e) {
            throw new IOException("Couldn't parse record's, employee ID or record date.");
        }
    }
}
