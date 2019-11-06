package model.valuation;

import java.util.*;

public class StockOptionPortfolio {
    // EmployeeId to valuations
    private Map<String, List<AbstractRecord>> employeeRecords = new HashMap<>();
    private ValuationContext valuationContext;

    public StockOptionPortfolio(){}


    public Set<String> getEmployeeIds (){
        return employeeRecords.keySet();
    }

    public List<AbstractRecord> getEmployeeRecords(String employeeId){
        return employeeRecords.get(employeeId);
    }

    public void addRecord(String employeeId, AbstractRecord record){
        if(employeeId == null || record == null) {
            return;
        }

        List<AbstractRecord> employeeRecordList = employeeRecords.get(employeeId);
        if(employeeRecordList == null){
            employeeRecordList = new ArrayList<>();
            employeeRecords.put(employeeId, employeeRecordList);
        }
        employeeRecordList.add(record);
    }

    public ValuationContext getValuationContext() {
        return valuationContext;
    }

    public void setValuationContext(ValuationContext valuationContext) {
        this.valuationContext = valuationContext;
    }
}
