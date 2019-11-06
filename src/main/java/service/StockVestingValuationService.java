package service;

import model.valuation.StockOptionPortfolioValuation;
import model.valuation.results.StockVestingValuationResult;

public interface StockVestingValuationService {
    public StockVestingValuationResult calculateValuation(StockOptionPortfolioValuation stockVesting);
}
