package model.csvtransform.parsers;

import model.valuation.StockOptionPortfolio;

import java.io.IOException;
import java.io.InputStream;

public interface StockOptionPortfolioParser {

    public StockOptionPortfolio parseStream(InputStream inStream) throws IOException;
}
