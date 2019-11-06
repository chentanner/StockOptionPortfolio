package model.valuation.evaluators;

import model.csvtransform.parsers.DateParser;
import model.valuation.PerformanceRecord;
import model.valuation.StockOptionValuation;
import model.valuation.ValuationContext;
import model.valuation.RecordFixture;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

public class PerformanceRecordEvaluatorTest {

    private PerformanceRecordEvaluator evaluator;

    @BeforeTest
    public void setUp(){
        this.evaluator = new PerformanceRecordEvaluator();
    }

    @Test
    public void testEvaluator() {
        BigDecimal perfMultiplier = BigDecimal.valueOf(1.5d);
        PerformanceRecord valuation = RecordFixture.CreatePerfRecord(perfMultiplier);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionValuation portfolio = new StockOptionValuation();
        portfolio.setTotalStockCount(1000);

        StockOptionValuation resultPortfolio = evaluator.evaluate(valuation,context,portfolio);

        Assert.assertEquals(resultPortfolio.getTotalStockCount().intValue(), 1500);
    }


    @Test
    public void testEvaluatorCurrentValueOfZero() {
        BigDecimal perfMultiplier = BigDecimal.valueOf(1.5d);
        PerformanceRecord valuation = RecordFixture.CreatePerfRecord(perfMultiplier);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionValuation portfolio = new StockOptionValuation();
        portfolio.setTotalStockCount(0);

        StockOptionValuation resultPortfolio = evaluator.evaluate(valuation,context,portfolio);

        Assert.assertEquals(resultPortfolio.getTotalStockCount().intValue(), 0);
    }

    @Test
    public void testEvaluatorZeroMulitplier() {
        BigDecimal perfMultiplier = BigDecimal.ZERO;
        PerformanceRecord valuation = RecordFixture.CreatePerfRecord(perfMultiplier);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionValuation portfolio = new StockOptionValuation();
        portfolio.setTotalStockCount(100);

        StockOptionValuation resultPortfolio = evaluator.evaluate(valuation,context,portfolio);

        Assert.assertEquals(resultPortfolio.getTotalStockCount().intValue(), 0);
    }
}
