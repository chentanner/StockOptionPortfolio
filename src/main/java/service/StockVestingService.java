package service;

import model.valuation.StockOptionPortfolio;
import model.valuation.evaluators.StockOptionPortfolioEvaluator;
import model.valuation.results.StockOptionPortfolioValuationResult;

public class StockVestingService implements StockVestingValuationService {

    @Override
    public StockOptionPortfolioValuationResult calculateValuation(StockOptionPortfolio stockVesting) {
        StockOptionPortfolioEvaluator stockVestingEvaluator = new StockOptionPortfolioEvaluator();
        return stockVestingEvaluator.evaluateStockVestingValuation(stockVesting);
    }
}
