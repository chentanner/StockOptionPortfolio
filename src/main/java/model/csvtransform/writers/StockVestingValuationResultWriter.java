package model.csvtransform.writers;

import model.valuation.results.StockVestingValuationResult;

import java.io.IOException;
import java.io.OutputStream;

public interface StockVestingValuationResultWriter {
    public void writeToStream(StockVestingValuationResult vestingValuationResult, OutputStream outStream) throws IOException;
}
