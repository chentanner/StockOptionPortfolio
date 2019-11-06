package model.valuation;

import enums.RecordType;

import java.util.Date;

public abstract class AbstractRecord {

    private String employeeId;
    private Date recordDate;

    protected AbstractRecord(){ }

    /**
     * Implemented by subclasses, returns the RecordType.
     * @return RecordType
     */
    public abstract RecordType getType();

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
