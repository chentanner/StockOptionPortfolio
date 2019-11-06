package model.valuation;

import enums.RecordType;

import java.math.BigDecimal;

public class SaleRecord extends AbstractRecord {

    private Integer amountSold;
    private BigDecimal salePrice;

    public SaleRecord(){
        super();
    };

    @Override
    public RecordType getType() {
        return RecordType.SALE;
    }

    public Integer getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(Integer amountSold) {
        this.amountSold = amountSold;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
}
