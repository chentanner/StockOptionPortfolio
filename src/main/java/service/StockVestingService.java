package service;

import model.valuation.StockOptionPortfolio;
import model.valuation.evaluators.PortfolioEvaluator;
import model.valuation.results.StockOptionPortfolioValuationResult;

public class StockVestingService implements StockVestingValuationService {

    PortfolioEvaluator evaluator;

    public StockVestingService(PortfolioEvaluator evaluator){
        this.evaluator = evaluator;
    }

    @Override
    public StockOptionPortfolioValuationResult calculateValuation(StockOptionPortfolio stockVesting) {
        return this.evaluator.evaluateStockVestingValuation(stockVesting);
    }
}
