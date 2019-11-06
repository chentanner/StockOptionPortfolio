package model.csvtransform.parsers;

import model.valuation.AbstractRecord;

import java.io.IOException;

public interface RecordParser {
    public AbstractRecord processLine(String [] splitLine) throws IOException;
}
