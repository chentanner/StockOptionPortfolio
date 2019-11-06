package model.valuation.evaluators;

import model.valuation.AbstractRecord;
import model.valuation.StockOptionPortfolio;
import model.valuation.StockOptionValuation;
import model.valuation.ValuationContext;
import model.valuation.results.StockOptionPortfolioValuationResult;
import model.valuation.results.ValuationResult;

import java.util.ArrayList;
import java.util.List;

public class StockOptionPortfolioEvaluator {

    public StockOptionPortfolioValuationResult evaluateStockVestingValuation(StockOptionPortfolio stockVesting) {
        StockOptionPortfolioValuationResult stockOptionPortfolioValuationResult = new StockOptionPortfolioValuationResult();
        ValuationContext context = stockVesting.getValuationContext();

        for (String employeeId : stockVesting.getEmployeeIds()) {
            List<AbstractRecord> sortedRecords = new ArrayList<>(stockVesting.getEmployeeRecords(employeeId));
            // Sort valuations based on record date, if there is a collision, make sure PERF types are last.
            sortedRecords.sort((record1, record2) ->{
                int result = record1.getRecordDate().compareTo(record2.getRecordDate());
                if(result == 0){
                    return record1.getType().compareTo(record2.getType());
                }
                return result;
            });

            StockOptionValuation valuation = calculateTotalCashGain(sortedRecords, context);

            ValuationResult valuationResult = new ValuationResult(
                    employeeId,
                    valuation.getTotalValueToGain(context.getMarketPrice()),
                    valuation.getTotalValueGained());
            stockOptionPortfolioValuationResult.addValuationResult(valuationResult);
        }

        return stockOptionPortfolioValuationResult;
    }

    private StockOptionValuation calculateTotalCashGain (List<AbstractRecord> sortedRecords, ValuationContext context){
        StockOptionValuation valuation = new StockOptionValuation();
        for (AbstractRecord abstractRecord : sortedRecords) {
            if (abstractRecord.getRecordDate().compareTo(context.getValuationDate()) >= 0) {
                // Since valuations are sorted, we can stop processing them
                // when we hit our first item with a date beyond the valuation date.
                break;
            }

            RecordEvaluator evaluator = RecordEvaluatorFactory.getInstance().getEvaluator(abstractRecord.getType());
            valuation = evaluator.evaluate(abstractRecord, context, valuation);
        }

        return valuation;
    }
}
