package dependencyInjectSimulator;

import controller.StockOptionPortfolioCliController;
import controller.StockOptionPortfolioController;
import model.csvtransform.parsers.*;
import model.csvtransform.writers.StockVestingValuationResultCsvWriter;
import model.valuation.evaluators.PortfolioEvaluator;
import model.valuation.evaluators.RecordEvaluatorFactory;
import model.valuation.evaluators.RecordEvaluatorFactoryInterface;
import model.valuation.evaluators.StockOptionPortfolioEvaluator;
import service.StockVestingService;
import service.StockVestingValuationService;

public class DependencyInjectionSimulator {

    public static StockOptionPortfolioController buildStockOptionPortfolioController(){

        // Dependency injection... Ideally this would be handled by a framework such as Spring.
        RecordParserFactoryInterface recordParserFactory = RecordParserFactory.getInstance();

        ValuationContextCsvParser valuationContextCsvParser = new ValuationContextParser();
        StockOptionPortfolioParser stockOptionPortfolioParser = new StockOptionPortfolioCsvParser(valuationContextCsvParser, recordParserFactory);
        StockVestingValuationResultCsvWriter stockVestingWriter = new StockVestingValuationResultCsvWriter();

        RecordEvaluatorFactoryInterface recordEvaluatorFactory = RecordEvaluatorFactory.getInstance();
        PortfolioEvaluator portfolioEvaluator = new StockOptionPortfolioEvaluator(recordEvaluatorFactory);

        StockVestingValuationService stockVestingService = new StockVestingService(portfolioEvaluator);

        StockOptionPortfolioCliController controller = new StockOptionPortfolioCliController(
                stockVestingService,
                stockOptionPortfolioParser,
                stockVestingWriter);

        return controller;
    }
}
