package model.csvtransform.parsers;

import model.valuation.ValuationContext;

import java.io.IOException;

public interface ValuationContextParser {

    public ValuationContext processLine(String[] splitLine) throws IOException;
}
