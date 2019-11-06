package model.csvtransform.parsers;

import model.valuation.AbstractValuation;

import java.io.IOException;

public interface ValuationRecordParser {
    public AbstractValuation processLine(String [] splitLine) throws IOException;
}
