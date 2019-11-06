package model.valuation.results;

import java.math.BigDecimal;

public class ValuationResult {
    private String employeeId;
    private BigDecimal totalCashToGain = BigDecimal.ZERO;
    private BigDecimal totalCashGained = BigDecimal.ZERO;

    public ValuationResult(String employeeId, BigDecimal totalCashToGain, BigDecimal totalCashGained){
        this.employeeId = employeeId;
        this.totalCashToGain = totalCashToGain;
        this.totalCashGained = totalCashGained;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public BigDecimal getTotalCashToGain() {
        return totalCashToGain;
    }
    public BigDecimal getTotalCashGained() {
        return totalCashGained;
    }
}
