package model.csvtransform.parsers;

import model.valuation.StockOptionPortfolioValuation;

import java.io.IOException;
import java.io.InputStream;

public interface StockVestingParser {

    public StockOptionPortfolioValuation parseStream(InputStream inStream) throws IOException;
}
