package model.valuation.evaluators;

import model.valuation.AbstractValuation;
import model.valuation.SaleValuation;
import model.valuation.ValuationContext;

import java.math.BigDecimal;

public class SaleRecordEvaluator implements ValuationRecordEvaluator {

    @Override
    public StockOptionPortfolio evaluate(AbstractValuation valuation, ValuationContext context, StockOptionPortfolio portfolio) {
        SaleValuation saleValuation = (SaleValuation)valuation;

        //reduce portfolio stock count
        portfolio.setTotalStockCount(portfolio.getTotalStockCount() - saleValuation.getAmountSold());

        // Update total gained value
        BigDecimal optionPrice = saleValuation.getSalePrice().subtract(portfolio.getAverageGrantPrice());
        BigDecimal saleGains = optionPrice.multiply(BigDecimal.valueOf(saleValuation.getAmountSold()));
        portfolio.setTotalValueGained(portfolio.getTotalValueGained().add(saleGains) );

        return portfolio;
    }
}
