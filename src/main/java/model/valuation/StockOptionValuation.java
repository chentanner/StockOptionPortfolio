package model.valuation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StockOptionValuation {

    private Integer totalStockCount = 0;
    private BigDecimal averageGrantPrice = null;
    private  BigDecimal totalValueToGain = BigDecimal.ZERO;
    private BigDecimal totalValueGained = BigDecimal.ZERO;

    public StockOptionValuation(){

    }

    public Integer getTotalStockCount() {
        return totalStockCount;
    }

    public void setTotalStockCount(Integer totalStockCount) {
        this.totalStockCount = totalStockCount;
    }

    public BigDecimal getAverageGrantPrice() {
        return averageGrantPrice;
    }

    public void setAverageGrantPrice(BigDecimal averageGrantPrice) {
        this.averageGrantPrice = averageGrantPrice;
    }

    public void addWeightedGrantPrice(BigDecimal grantPrice, Integer stockCount){
        if(stockCount == null || grantPrice == null)
            return;

        this.setTotalStockCount(this.getTotalStockCount() + stockCount);

        if(this.getAverageGrantPrice() == null ||
                this.getAverageGrantPrice().doubleValue() == 0d ||
                stockCount == 0){
            // Evaluating first VestingRecord
            this.setAverageGrantPrice(grantPrice);
            return;
        }
        // Calculate weight factors: (vestAmount/totalAmount) and (1 - vestAmount/totalAmount)
        BigDecimal grantPriceWeightFactor = BigDecimal.valueOf(stockCount).divide(BigDecimal.valueOf(this.getTotalStockCount()),8, RoundingMode.HALF_UP);
        BigDecimal previousGrantPriceWeightFactor = BigDecimal.ONE.subtract(grantPriceWeightFactor);

        // ((previousGrantPrice * wieghtFactor) + (grantPrice * weightFactor))
        BigDecimal previousWeightedAverageGrantPrice = this.getAverageGrantPrice().multiply(previousGrantPriceWeightFactor);
        BigDecimal weightedAverageGrantPrice = grantPrice.multiply(grantPriceWeightFactor);
        BigDecimal newWeightedGrantPrice = previousWeightedAverageGrantPrice.add(weightedAverageGrantPrice);

        this.setAverageGrantPrice(newWeightedGrantPrice);
    }

    public BigDecimal getTotalValueToGain(BigDecimal marketPrice){
        if(this.averageGrantPrice == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal optionPrice = marketPrice.subtract(this.averageGrantPrice);
        return optionPrice.multiply(BigDecimal.valueOf(this.totalStockCount));
    }

    public void setTotalValueGained(BigDecimal totalValueGained) {
        this.totalValueGained = totalValueGained;
    }

    public BigDecimal getTotalValueGained(){
        return this.totalValueGained;
    }
}
