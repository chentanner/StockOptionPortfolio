package model.valuation;

import java.util.*;

public class StockOptionPortfolioValuation {
    // EmployeeId to valuations
    private Map<String, List<AbstractValuation>> employeeValuations = new HashMap<>();
    private ValuationContext valuationContext;

    public StockOptionPortfolioValuation(){}


    public Set<String> getEmployeeIds (){
        return employeeValuations.keySet();
    }

    public List<AbstractValuation> getEmployeeValuations (String employeeId){
        return employeeValuations.get(employeeId);
    }

    public void addValuation(String employeeId, AbstractValuation valuation){
        if(employeeId == null || valuation == null) {
            return;
        }

        List<AbstractValuation> employeeValuationList = employeeValuations.get(employeeId);
        if(employeeValuationList == null){
            employeeValuationList = new ArrayList<>();
            employeeValuations.put(employeeId, employeeValuationList);
        }
        employeeValuationList.add(valuation);
    }

    public ValuationContext getValuationContext() {
        return valuationContext;
    }

    public void setValuationContext(ValuationContext valuationContext) {
        this.valuationContext = valuationContext;
    }
}
