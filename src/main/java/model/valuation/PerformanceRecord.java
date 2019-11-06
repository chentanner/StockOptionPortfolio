package model.valuation;

import enums.RecordType;

import java.math.BigDecimal;

public class PerformanceRecord extends AbstractRecord {

    private BigDecimal performanceMultiplier = BigDecimal.valueOf(1);

    public PerformanceRecord(){
        super();
    };

    @Override
    public RecordType getType() {
        return RecordType.PERF;
    }

    public BigDecimal getPerformanceMultiplier() {
        return performanceMultiplier;
    }

    public void setPerformanceMultiplier(BigDecimal performanceMultiplier) {
        this.performanceMultiplier = performanceMultiplier;
    }
}
