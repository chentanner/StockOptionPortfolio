package model.valuation;

import enums.ValuationType;

import java.math.BigDecimal;

public class PerfValuation extends AbstractValuation{

    private BigDecimal performanceMultiplier = BigDecimal.valueOf(1);

    public PerfValuation(){
        super();
    };

    @Override
    public ValuationType getType() {
        return ValuationType.PERF;
    }

    public BigDecimal getPerformanceMultiplier() {
        return performanceMultiplier;
    }

    public void setPerformanceMultiplier(BigDecimal performanceMultiplier) {
        this.performanceMultiplier = performanceMultiplier;
    }
}
