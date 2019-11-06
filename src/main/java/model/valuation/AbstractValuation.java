package model.valuation;

import enums.ValuationType;

import java.util.Date;

public abstract class AbstractValuation {

    private String employeeId;
    private Date recordDate;

    protected AbstractValuation(){ }

    /**
     * Implemented by subclasses, returns the ValuationType.
     * @return ValuationType
     */
    public abstract ValuationType getType();

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
