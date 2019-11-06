import controller.StockOptionPortfolioCliController;
import model.csvtransform.writers.StockVestingValuationResultCsvWriter;
import service.StockVestingValuationService;
import service.StockVestingService;
import model.csvtransform.parsers.StockOptionPortfolioCsvParser;
import model.csvtransform.parsers.StockOptionPortfolioParser;
import model.csvtransform.parsers.ValuationContextCsvParser;
import model.csvtransform.parsers.ValuationContextParser;

import java.io.IOException;

public class Main {
    public static void main (String [] args) throws IOException {

        // Dependency injection... Ideally this would be handled by a framework such as Spring.
        ValuationContextCsvParser valuationContextCsvParser = new ValuationContextParser();
        StockOptionPortfolioParser stockOptionPortfolioParser = new StockOptionPortfolioCsvParser(valuationContextCsvParser);
        StockVestingValuationResultCsvWriter stockVestingWriter = new StockVestingValuationResultCsvWriter();

        StockVestingValuationService stockVestingService = new StockVestingService();

        StockOptionPortfolioCliController controller = new StockOptionPortfolioCliController(
                stockVestingService,
                stockOptionPortfolioParser,
                stockVestingWriter);

        controller.evaluateStockVestings(System.in, System.out);
    }
}
