package model.valuation.evaluators;

import model.valuation.AbstractRecord;
import model.valuation.StockOptionValuation;
import model.valuation.ValuationContext;

public interface RecordEvaluator {
    public StockOptionValuation evaluate(AbstractRecord record, ValuationContext context, StockOptionValuation valuation);
}
