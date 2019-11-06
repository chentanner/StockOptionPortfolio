package model.valuation.evaluators;

import model.valuation.AbstractRecord;
import model.valuation.StockOptionValuation;
import model.valuation.ValuationContext;
import model.valuation.VestingRecord;

public class VestingRecordEvaluator implements RecordEvaluator {

    @Override
    public StockOptionValuation evaluate(AbstractRecord record, ValuationContext context, StockOptionValuation valuation) {
        VestingRecord vestingRecord = (VestingRecord)record;

        if(context.getMarketPrice().subtract(vestingRecord.getGrantPrice()).doubleValue() < 0){
            // Vesting is under water.
            return valuation;
        }

        if(vestingRecord.getUnitCount() == 0) {
            return valuation;
        }

        valuation.addWeightedGrantPrice(vestingRecord.getGrantPrice(), vestingRecord.getUnitCount());

        return valuation;
    }
}
