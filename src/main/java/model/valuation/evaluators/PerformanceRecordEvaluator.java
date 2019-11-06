package model.valuation.evaluators;

import model.valuation.AbstractValuation;
import model.valuation.PerfValuation;
import model.valuation.ValuationContext;

import java.math.BigDecimal;

public class PerformanceRecordEvaluator implements ValuationRecordEvaluator {

    @Override
    public StockOptionPortfolio evaluate(AbstractValuation valuation, ValuationContext context, StockOptionPortfolio portfolio) {
        PerfValuation perfValuation = (PerfValuation)valuation;

        BigDecimal updatedStockCount = BigDecimal.valueOf(portfolio.getTotalStockCount()).multiply(perfValuation.getPerformanceMultiplier());
        portfolio.setTotalStockCount(updatedStockCount.intValue());

        return portfolio;
    }
}
