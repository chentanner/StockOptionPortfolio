package model.valuation.evaluators;

import model.valuation.AbstractRecord;
import model.valuation.SaleRecord;
import model.valuation.StockOptionValuation;
import model.valuation.ValuationContext;

import java.math.BigDecimal;

public class SaleRecordEvaluator implements RecordEvaluator {

    @Override
    public StockOptionValuation evaluate(AbstractRecord record, ValuationContext context, StockOptionValuation valuation) {
        SaleRecord saleRecord = (SaleRecord) record;

        if(saleRecord.getAmountSold() == 0){
            // If nothing is sold ignore the record.
            return valuation;
        }
        if(saleRecord.getAmountSold() < 0){
            // If nothing is sold ignore the record.
            throw new RuntimeException("Can't sell negative amount of stock.");
        }

        Integer updatedStockCount = valuation.getTotalStockCount() - saleRecord.getAmountSold();
        if(updatedStockCount < 0){
            throw new RuntimeException("Attempted to over sell stock.");
        }
        //reduce portfolio stock count
        valuation.setTotalStockCount(valuation.getTotalStockCount() - saleRecord.getAmountSold());

        // Update total gained value
        BigDecimal optionPrice = saleRecord.getSalePrice().subtract(valuation.getAverageGrantPrice());
        BigDecimal saleGains = optionPrice.multiply(BigDecimal.valueOf(saleRecord.getAmountSold()));
        valuation.setTotalValueGained(valuation.getTotalValueGained().add(saleGains) );

        return valuation;
    }
}
