package model.valuation;

import enums.ValuationType;

import java.math.BigDecimal;

public class SaleValuation extends AbstractValuation{

    private Integer amountSold;
    private BigDecimal salePrice;

    public SaleValuation(){
        super();
    };

    @Override
    public ValuationType getType() {
        return ValuationType.SALE;
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
