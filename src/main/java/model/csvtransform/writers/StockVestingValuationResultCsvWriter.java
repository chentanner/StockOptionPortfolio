package model.csvtransform.writers;

import model.valuation.results.StockVestingValuationResult;
import model.valuation.results.ValuationResult;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class StockVestingValuationResultCsvWriter implements StockVestingValuationResultWriter{

    public StockVestingValuationResultCsvWriter(){ }

    /**
     * Writes the csv representation of the vestingValuationResult to the output stream.
     * @param vestingValuationResult
     * @param outStream
     * @throws IOException
     */
    @Override
    public void writeToStream(StockVestingValuationResult vestingValuationResult, OutputStream outStream) throws IOException {
        List<ValuationResult> valuationResults = vestingValuationResult.getValuationResults();
        // Sort results by employee id.
        Collections.sort(valuationResults, Comparator.comparing(ValuationResult::getEmployeeId));

        ValuationResultCsvSerializer resultWriter = new ValuationResultCsvSerializer();
        try(BufferedWriter writer = new BufferedWriter((new OutputStreamWriter(outStream)))){
            Iterator<ValuationResult> iterator = valuationResults.iterator();
            while (iterator.hasNext()){
                ValuationResult valuationResult = iterator.next();
                String valuationLine = resultWriter.serializeValuationResultToCsvLine(valuationResult);
                writer.write(valuationLine);
                if(iterator.hasNext()) {
                    writer.newLine();
                }
            }
        }
    }
}
