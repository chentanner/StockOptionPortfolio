package controller;

import model.csvtransform.writers.StockVestingValuationResultWriter;
import model.valuation.results.StockVestingValuationResult;
import service.StockVestingValuationService;
import model.csvtransform.parsers.StockVestingParser;
import model.valuation.StockOptionPortfolioValuation;

import java.io.IOException;

public class StockOptionPortfolioCliController {
    private StockVestingValuationService stockVestingService;
    private StockVestingParser stockVestingParser;
    private StockVestingValuationResultWriter stockVestingWriter;

    public StockOptionPortfolioCliController(
            StockVestingValuationService stockVestingService,
            StockVestingParser stockVestingParser,
            StockVestingValuationResultWriter stockVestingWriter){
        this.stockVestingService = stockVestingService;
        this.stockVestingParser = stockVestingParser;
        this.stockVestingWriter = stockVestingWriter;
    }

    /**
     * Will read a csv file passed in System.in and will output the resulting csv on System.out.
     * @throws IOException
     */
    public void evaluateStockVestings() {
        try {
            // Parse input
            StockOptionPortfolioValuation stockVesting = this.stockVestingParser.parseStream(System.in);

            // Call service to evaluate stock options
            StockVestingValuationResult result = this.stockVestingService.calculateValuation(stockVesting);

            // output result
            stockVestingWriter.writeToStream(result, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
