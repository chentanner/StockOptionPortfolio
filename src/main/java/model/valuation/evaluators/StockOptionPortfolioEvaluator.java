package model.valuation.evaluators;

import model.valuation.AbstractValuation;
import model.valuation.StockOptionPortfolioValuation;
import model.valuation.ValuationContext;
import model.valuation.results.StockVestingValuationResult;
import model.valuation.results.ValuationResult;

import java.util.ArrayList;
import java.util.List;

public class StockOptionPortfolioEvaluator {

    public StockVestingValuationResult evaluateStockVestingValuation(StockOptionPortfolioValuation stockVesting) {
        StockVestingValuationResult stockVestingValuationResult = new StockVestingValuationResult();
        ValuationContext context = stockVesting.getValuationContext();

        for (String employeeId : stockVesting.getEmployeeIds()) {
            List<AbstractValuation> sortedValuations = new ArrayList<>(stockVesting.getEmployeeValuations(employeeId));
            // Sort valuations based on record date, if there is a collision, make sure PERF types are last.
            sortedValuations.sort((valuation1, valuation2) ->{
                int result = valuation1.getRecordDate().compareTo(valuation2.getRecordDate());
                if(result == 0){
                    return valuation1.getType().compareTo(valuation2.getType());
                }
                return result;
            });

            StockOptionPortfolio portfolio = calculateTotalCashGain(sortedValuations, context);

            ValuationResult valuationResult = new ValuationResult(
                    employeeId,
                    portfolio.getTotalValueToGain(context.getMarketPrice()),
                    portfolio.getTotalValueGained());
            stockVestingValuationResult.addValuationResult(valuationResult);
        }

        return stockVestingValuationResult;
    }

    private StockOptionPortfolio calculateTotalCashGain (List<AbstractValuation> sortedValuations, ValuationContext context){
        StockOptionPortfolio portfolio = new StockOptionPortfolio();
        for (AbstractValuation abstractValuation : sortedValuations) {
            if (abstractValuation.getRecordDate().compareTo(context.getValuationDate()) >= 0) {
                // Since valuations are sorted, we can stop processing them
                // when we hit our first item with a date beyond the valuation date.
                break;
            }

            ValuationRecordEvaluator evaluator = ValuationRecordEvaluatorFactory.getInstance().getEvaluator(abstractValuation.getType());
            portfolio = evaluator.evaluate(abstractValuation, context, portfolio);
        }

//        if (totalCashGain.doubleValue() < 0) {
//            // Normalizing for "under water" options
//            totalCashGain = BigDecimal.ZERO;
//        }

        return portfolio;
    }
}
