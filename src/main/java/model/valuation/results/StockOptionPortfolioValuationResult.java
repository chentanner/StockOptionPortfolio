package model.valuation.results;

import java.util.ArrayList;
import java.util.List;

public class StockOptionPortfolioValuationResult {
    private List<ValuationResult> valuationResults = new ArrayList<>();

    public void addValuationResult(ValuationResult valuationResult){
        this.valuationResults.add(valuationResult);
    }

    public List<ValuationResult> getValuationResults(){
        return valuationResults;
    }
}
