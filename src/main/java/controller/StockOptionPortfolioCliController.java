package controller;

import model.csvtransform.writers.StockVestingValuationResultWriter;
import model.valuation.results.StockOptionPortfolioValuationResult;
import service.StockVestingValuationService;
import model.csvtransform.parsers.StockOptionPortfolioParser;
import model.valuation.StockOptionPortfolio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StockOptionPortfolioCliController {
    private StockVestingValuationService stockVestingService;
    private StockOptionPortfolioParser stockOptionPortfolioParser;
    private StockVestingValuationResultWriter stockVestingWriter;

    public StockOptionPortfolioCliController(
            StockVestingValuationService stockVestingService,
            StockOptionPortfolioParser stockOptionPortfolioParser,
            StockVestingValuationResultWriter stockVestingWriter){
        this.stockVestingService = stockVestingService;
        this.stockOptionPortfolioParser = stockOptionPortfolioParser;
        this.stockVestingWriter = stockVestingWriter;
    }

    /**
     * Will read a csv file passed in System.in and will output the resulting csv on System.out.
     * @throws IOException
     */
    public void evaluateStockVestings(InputStream streamIn, OutputStream streamOut) {
        try {
            // Parse input
            StockOptionPortfolio stockOptionPortfolio = this.stockOptionPortfolioParser.parseStream(streamIn);

            // Call service to evaluate stock options
            StockOptionPortfolioValuationResult result = this.stockVestingService.calculateValuation(stockOptionPortfolio);

            // output result
            stockVestingWriter.writeToStream(result, streamOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
