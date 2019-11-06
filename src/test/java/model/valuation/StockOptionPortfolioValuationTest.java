package model.valuation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class StockOptionPortfolioValuationTest {

    @Test
    public void testAddingValuation() {
        StockOptionPortfolioValuation stockOptionPortfolioValuation = new StockOptionPortfolioValuation();
        VestingValuation vestingValuation = ValuationFixture.CreateVestingValuation();

        stockOptionPortfolioValuation.addValuation(vestingValuation.getEmployeeId(), vestingValuation);

        List<AbstractValuation> valuations = stockOptionPortfolioValuation.getEmployeeValuations(vestingValuation.getEmployeeId());
        Assert.assertEquals(valuations.size(),1);
        AbstractValuation valuation = valuations.get(0);
        Assert.assertEquals(vestingValuation,valuation);
    }

    @Test
    public void testAddingNullValuation() {
        String employeeId = "employee1";
        StockOptionPortfolioValuation stockOptionPortfolioValuation = new StockOptionPortfolioValuation();

        stockOptionPortfolioValuation.addValuation(employeeId, null);

        List<AbstractValuation> valuations = stockOptionPortfolioValuation.getEmployeeValuations(employeeId);
        Assert.assertNull(valuations);
    }

    @Test
    public void testAddingNullEmplyeeId() {
        String employeeId = null;
        StockOptionPortfolioValuation stockOptionPortfolioValuation = new StockOptionPortfolioValuation();

        stockOptionPortfolioValuation.addValuation(employeeId, new VestingValuation());

        List<AbstractValuation> valuations = stockOptionPortfolioValuation.getEmployeeValuations(employeeId);
        Assert.assertNull(valuations);
    }

}