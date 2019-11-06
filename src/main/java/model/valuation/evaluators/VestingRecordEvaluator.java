package model.valuation.evaluators;

import model.valuation.AbstractValuation;
import model.valuation.ValuationContext;
import model.valuation.VestingValuation;

public class VestingRecordEvaluator implements ValuationRecordEvaluator {

    @Override
    public StockOptionPortfolio evaluate(AbstractValuation valuation, ValuationContext context, StockOptionPortfolio portfolio) {
        VestingValuation vestingValuation = (VestingValuation)valuation;

        if(context.getMarketPrice().subtract(vestingValuation.getGrantPrice()).doubleValue() < 0){
            // Vesting is under water.
            return portfolio;
        }

        if(vestingValuation.getUnitCount() == 0) {
            return portfolio;
        }

        portfolio.addWeightedGrantPrice(vestingValuation.getGrantPrice(), vestingValuation.getUnitCount());

        return portfolio;
    }
}
