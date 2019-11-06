package model.valuation;

import enums.ValuationType;

import java.math.BigDecimal;

public class VestingValuation extends AbstractValuation{

    private Integer unitCount;
    private BigDecimal  grantPrice;

    public VestingValuation(){
        super();
    };

    @Override
    public ValuationType getType() {
        return ValuationType.VEST;
    }

    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }

    public BigDecimal getGrantPrice() {
        return grantPrice;
    }

    public void setGrantPrice(BigDecimal grantPrice) {
        this.grantPrice = grantPrice;
    }
}
