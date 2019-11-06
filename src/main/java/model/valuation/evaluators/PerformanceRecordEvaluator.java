package model.valuation.evaluators;

import model.valuation.AbstractRecord;
import model.valuation.PerformanceRecord;
import model.valuation.StockOptionValuation;
import model.valuation.ValuationContext;

import java.math.BigDecimal;

public class PerformanceRecordEvaluator implements RecordEvaluator {

    @Override
    public StockOptionValuation evaluate(AbstractRecord record, ValuationContext context, StockOptionValuation valuation) {
        PerformanceRecord performanceRecord = (PerformanceRecord) record;

        BigDecimal updatedStockCount = BigDecimal.valueOf(valuation.getTotalStockCount()).multiply(performanceRecord.getPerformanceMultiplier());
        valuation.setTotalStockCount(updatedStockCount.intValue());

        return valuation;
    }
}
