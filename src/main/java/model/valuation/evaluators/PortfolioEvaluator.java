package model.valuation.evaluators;

import model.valuation.StockOptionPortfolio;
import model.valuation.results.StockOptionPortfolioValuationResult;

public interface PortfolioEvaluator {
    StockOptionPortfolioValuationResult evaluateStockVestingValuation(StockOptionPortfolio stockVesting);
}
