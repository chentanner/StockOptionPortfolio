package model.service;

import model.valuation.StockOptionPortfolioValuation;
import model.valuation.results.StockVestingValuationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.StockVestingService;

import java.text.ParseException;

public class StockVestingServiceTest {

    private StockVestingService service;

    @BeforeTest
    public void setUp(){
        this.service = new StockVestingService();
    }

    @Test
    public void testCalculateValuation() throws ParseException {
        // The service is more of a wrapper for StockOptionPortfolioEvaluator, so we just need a smoke test here.
        StockOptionPortfolioValuation stockVesting = new StockOptionPortfolioValuation();
        StockVestingValuationResult result = service.calculateValuation(stockVesting);
        Assert.assertNotNull(result);
    }
}
