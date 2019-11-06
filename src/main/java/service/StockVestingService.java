package service;

import model.valuation.StockOptionPortfolioValuation;
import model.valuation.evaluators.StockOptionPortfolioEvaluator;
import model.valuation.results.StockVestingValuationResult;

public class StockVestingService implements StockVestingValuationService {

    @Override
    public StockVestingValuationResult calculateValuation(StockOptionPortfolioValuation stockVesting) {
        StockOptionPortfolioEvaluator stockVestingEvaluator = new StockOptionPortfolioEvaluator();
        return stockVestingEvaluator.evaluateStockVestingValuation(stockVesting);
    }
}
