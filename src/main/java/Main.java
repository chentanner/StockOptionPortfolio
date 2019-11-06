import controller.StockOptionPortfolioCliController;
import model.csvtransform.writers.StockVestingValuationResultCsvWriter;
import service.StockVestingValuationService;
import service.StockVestingService;
import model.csvtransform.parsers.StockVestingCsvParser;
import model.csvtransform.parsers.StockVestingParser;
import model.csvtransform.parsers.ValuationContextParser;
import model.csvtransform.parsers.ValuationContextSimpleParser;

import java.io.IOException;

public class Main {
    public static void main (String [] args) throws IOException {

        // Dependency injection... Ideally this would be handled by a framework such as Spring.
        ValuationContextParser valuationContextParser = new ValuationContextSimpleParser();
        StockVestingParser stockVestingParser = new StockVestingCsvParser(valuationContextParser);
        StockVestingValuationResultCsvWriter stockVestingWriter = new StockVestingValuationResultCsvWriter();

        StockVestingValuationService stockVestingService = new StockVestingService();

        StockOptionPortfolioCliController controller = new StockOptionPortfolioCliController(
                stockVestingService,
                stockVestingParser,
                stockVestingWriter);

        controller.evaluateStockVestings();
    }
}
