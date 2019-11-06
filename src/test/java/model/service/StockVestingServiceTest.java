package model.service;

import model.valuation.StockOptionPortfolio;
import model.valuation.results.StockOptionPortfolioValuationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.StockVestingService;

public class StockVestingServiceTest {

    private StockVestingService service;

    @BeforeTest
    public void setUp(){
        this.service = new StockVestingService();
    }

    @Test
    public void testCalculateValuation()  {
        // The service is more of a wrapper for StockOptionPortfolioEvaluator, so we just need a smoke test here.
        StockOptionPortfolio stockVesting = new StockOptionPortfolio();
        StockOptionPortfolioValuationResult result = service.calculateValuation(stockVesting);
        Assert.assertNotNull(result);
    }
}
