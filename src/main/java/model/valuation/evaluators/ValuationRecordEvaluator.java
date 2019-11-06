package model.valuation.evaluators;

import model.valuation.AbstractValuation;
import model.valuation.ValuationContext;

public interface ValuationRecordEvaluator {
    public StockOptionPortfolio evaluate(AbstractValuation valuation, ValuationContext context, StockOptionPortfolio portfolio);
}
