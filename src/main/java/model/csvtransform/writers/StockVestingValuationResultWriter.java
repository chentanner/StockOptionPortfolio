package model.csvtransform.writers;

import model.valuation.results.StockOptionPortfolioValuationResult;

import java.io.IOException;
import java.io.OutputStream;

public interface StockVestingValuationResultWriter {
    public void writeToStream(StockOptionPortfolioValuationResult vestingValuationResult, OutputStream outStream) throws IOException;
}
