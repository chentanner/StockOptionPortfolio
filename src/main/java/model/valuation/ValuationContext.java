package model.valuation;

import java.math.BigDecimal;
import java.util.Date;

public class ValuationContext {
    private BigDecimal marketPrice;
    private Date valuationDate;

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Date getValuationDate() {
        return valuationDate;
    }

    public void setValuationDate(Date valuationDate) {
        this.valuationDate = valuationDate;
    }
}
