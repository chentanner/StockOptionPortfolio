package model.valuation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class StockOptionPortfolioTest {

    @Test
    public void testAddingValuation() {
        StockOptionPortfolio stockOptionPortfolio = new StockOptionPortfolio();
        VestingRecord vestingRecord = RecordFixture.CreateVestingRecord();

        stockOptionPortfolio.addRecord(vestingRecord.getEmployeeId(), vestingRecord);

        List<AbstractRecord> records = stockOptionPortfolio.getEmployeeRecords(vestingRecord.getEmployeeId());
        Assert.assertEquals(records.size(),1);
        AbstractRecord record = records.get(0);
        Assert.assertEquals(vestingRecord,record);
    }

    @Test
    public void testAddingNullValuation() {
        String employeeId = "employee1";
        StockOptionPortfolio stockOptionPortfolio = new StockOptionPortfolio();

        stockOptionPortfolio.addRecord(employeeId, null);

        List<AbstractRecord> records = stockOptionPortfolio.getEmployeeRecords(employeeId);
        Assert.assertNull(records);
    }

    @Test
    public void testAddingNullEmplyeeId() {
        String employeeId = null;
        StockOptionPortfolio stockOptionPortfolio = new StockOptionPortfolio();

        stockOptionPortfolio.addRecord(employeeId, new VestingRecord());

        List<AbstractRecord> records = stockOptionPortfolio.getEmployeeRecords(employeeId);
        Assert.assertNull(records);
    }

}