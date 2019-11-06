package model.service;

import model.valuation.StockOptionPortfolio;
import model.valuation.evaluators.PortfolioEvaluator;
import model.valuation.results.StockOptionPortfolioValuationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.StockVestingService;

public class StockVestingServiceTest {

    private StockVestingService service;

    @BeforeTest
    public void setUp(){
        PortfolioEvaluator evaluatorMock = stockVesting -> new StockOptionPortfolioValuationResult();
        this.service = new StockVestingService(evaluatorMock);
    }

    @Test
    public void testCalculateValuation()  {
        // The service is more of a wrapper for StockOptionPortfolioEvaluator, so we just need a smoke test here.
        StockOptionPortfolio stockVesting = new StockOptionPortfolio();
        StockOptionPortfolioValuationResult result = service.calculateValuation(stockVesting);
        Assert.assertNotNull(result);
    }
}
