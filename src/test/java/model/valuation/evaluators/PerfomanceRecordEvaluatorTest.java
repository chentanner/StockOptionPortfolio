package model.valuation.evaluators;

import model.csvtransform.parsers.DateParser;
import model.valuation.PerfValuation;
import model.valuation.ValuationContext;
import model.valuation.ValuationFixture;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class PerfomanceRecordEvaluatorTest {

    private PerformanceRecordEvaluator evaluator;

    @BeforeTest
    public void setUp(){
        this.evaluator = new PerformanceRecordEvaluator();
    }

    @Test
    public void testEvaluator() throws ParseException {
        BigDecimal perfMultiplier = BigDecimal.valueOf(1.5d);
        PerfValuation valuation = ValuationFixture.CreatePerfValuation(perfMultiplier);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionPortfolio portfolio = new StockOptionPortfolio();
        portfolio.setTotalStockCount(1000);

        StockOptionPortfolio resultPortfolio = evaluator.evaluate(valuation,context,portfolio);

        Assert.assertEquals(resultPortfolio.getTotalStockCount().intValue(), 1500);
    }


    @Test
    public void testEvaluatorCurrentValueOfZero() throws ParseException {
        BigDecimal perfMultiplier = BigDecimal.valueOf(1.5d);
        PerfValuation valuation = ValuationFixture.CreatePerfValuation(perfMultiplier);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionPortfolio portfolio = new StockOptionPortfolio();
        portfolio.setTotalStockCount(0);

        StockOptionPortfolio resultPortfolio = evaluator.evaluate(valuation,context,portfolio);

        Assert.assertEquals(resultPortfolio.getTotalStockCount().intValue(), 0);
    }

    @Test
    public void testEvaluatorZeroMulitplier() throws ParseException {
        BigDecimal perfMultiplier = BigDecimal.ZERO;
        PerfValuation valuation = ValuationFixture.CreatePerfValuation(perfMultiplier);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionPortfolio portfolio = new StockOptionPortfolio();
        portfolio.setTotalStockCount(100);

        StockOptionPortfolio resultPortfolio = evaluator.evaluate(valuation,context,portfolio);

        Assert.assertEquals(resultPortfolio.getTotalStockCount().intValue(), 0);
    }
}
