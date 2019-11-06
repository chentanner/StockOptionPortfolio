package service;

import model.valuation.StockOptionPortfolio;
import model.valuation.results.StockOptionPortfolioValuationResult;

public interface StockVestingValuationService {
    public StockOptionPortfolioValuationResult calculateValuation(StockOptionPortfolio stockVesting);
}
