package model.valuation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StockOptionValuationTest {

    @Test
    public void testAddWeightedGrantPrice(){
        BigDecimal grantPrice = BigDecimal.ONE;
        Integer stockCount = 1;

        StockOptionValuation stockOptionValuation = new StockOptionValuation();
        stockOptionValuation.addWeightedGrantPrice(grantPrice, stockCount);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount(), stockCount);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), grantPrice.doubleValue());

        BigDecimal grantPrice2 = BigDecimal.valueOf(2);
        Integer stockCount2 = 1;

        stockOptionValuation.addWeightedGrantPrice(grantPrice2, stockCount2);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().intValue(), 2);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), 1.5d);

        BigDecimal grantPrice3 = BigDecimal.valueOf(6);
        Integer stockCount3 = 1;

        stockOptionValuation.addWeightedGrantPrice(grantPrice3, stockCount3);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().intValue(), 3);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().setScale(2, RoundingMode.HALF_UP).doubleValue(), 3d);
    }

    @Test
    public void testAddWeightedGrantPriceNullStockCount(){
        BigDecimal grantPrice = BigDecimal.ONE;
        Integer stockCount = null;

        StockOptionValuation stockOptionValuation = new StockOptionValuation();
        stockOptionValuation.addWeightedGrantPrice(grantPrice, stockCount);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().doubleValue(), 0d);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), 0d);

        BigDecimal grantPrice2 = BigDecimal.valueOf(2);
        Integer stockCount2 = 1;

        stockOptionValuation.addWeightedGrantPrice(grantPrice2, stockCount2);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().intValue(), 1);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), 2d);
    }

    @Test
    public void testAddWeightedGrantPriceZeroStockCount(){
        BigDecimal grantPrice = BigDecimal.ONE;
        Integer stockCount = 0;

        StockOptionValuation stockOptionValuation = new StockOptionValuation();
        stockOptionValuation.addWeightedGrantPrice(grantPrice, stockCount);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().doubleValue(), 0d);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), grantPrice.doubleValue());

        BigDecimal grantPrice2 = BigDecimal.valueOf(2);
        Integer stockCount2 = 1;

        stockOptionValuation.addWeightedGrantPrice(grantPrice2, stockCount2);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().intValue(), 1);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), 2d);
    }

    @Test
    public void testAddWeightedGrantPriceNullGrantPrice(){
        BigDecimal grantPrice = null;
        Integer stockCount = 1;

        StockOptionValuation stockOptionValuation = new StockOptionValuation();
        stockOptionValuation.addWeightedGrantPrice(grantPrice, stockCount);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().intValue(), 0);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), 0d);

        BigDecimal grantPrice2 = BigDecimal.valueOf(2);
        Integer stockCount2 = 1;

        stockOptionValuation.addWeightedGrantPrice(grantPrice2, stockCount2);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().intValue(), 1);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), 2d);
    }

    @Test
    public void testAddWeightedGrantPriceZeroGrantPrice(){
        BigDecimal grantPrice = BigDecimal.ZERO;
        Integer stockCount = 1;

        StockOptionValuation stockOptionValuation = new StockOptionValuation();
        stockOptionValuation.addWeightedGrantPrice(grantPrice, stockCount);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().intValue(), 1);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), grantPrice.doubleValue());

        BigDecimal grantPrice2 = BigDecimal.valueOf(2);
        Integer stockCount2 = 1;

        stockOptionValuation.addWeightedGrantPrice(grantPrice2, stockCount2);

        Assert.assertEquals(stockOptionValuation.getTotalStockCount().intValue(), 2);
        Assert.assertEquals(stockOptionValuation.getAverageGrantPrice().doubleValue(), 2d);
    }
}
