package model.valuation;

import enums.RecordType;

import java.math.BigDecimal;

public class VestingRecord extends AbstractRecord {

    private Integer unitCount;
    private BigDecimal  grantPrice;

    public VestingRecord(){
        super();
    };

    @Override
    public RecordType getType() {
        return RecordType.VEST;
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
