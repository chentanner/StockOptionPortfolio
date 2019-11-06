package model.valuation.evaluators;

import model.csvtransform.parsers.DateParser;
import model.valuation.*;
import model.valuation.results.StockOptionPortfolioValuationResult;
import model.valuation.results.ValuationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class StockOptionPortfolioEvaluatorTest {

    private StockOptionPortfolioEvaluator evaluator;

    @BeforeTest
    public void setUp(){
        RecordEvaluatorFactoryInterface recordEvaluatorFactory = RecordEvaluatorFactory.getInstance();

        this.evaluator = new StockOptionPortfolioEvaluator(recordEvaluatorFactory);
    }

    @Test
    public void testEvaluator() {
        StockOptionPortfolio stockVesting = new StockOptionPortfolio();

        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingRecord record = RecordFixture.CreateVestingRecord(
                grantPrice,
                unitCount);

        VestingRecord record2 = RecordFixture.CreateVestingRecord(
                "employee2",
                DateParser.parseDate("20140101"),
                grantPrice.add(BigDecimal.valueOf(0.1d)),
                unitCount);

        stockVesting.addRecord(record.getEmployeeId(),record);
        stockVesting.addRecord(record2.getEmployeeId(),record2);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockOptionPortfolioValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 2);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),45d);

        result = valuationResults.get(1);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),35d);
    }

    @Test
    public void testEvaluatorNoValuationsOrContext() {
        StockOptionPortfolio stockVesting = new StockOptionPortfolio();

        StockOptionPortfolioValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 0);
    }

    @Test
    public void testEvaluatorIgnoredVestingValuationsByDate() {
        StockOptionPortfolio stockVesting = new StockOptionPortfolio();

        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingRecord record = RecordFixture.CreateVestingRecord(
                grantPrice,
                unitCount);

        VestingRecord record2 = RecordFixture.CreateVestingRecord(
                "employee2",
                DateParser.parseDate("20200101"),
                grantPrice.add(BigDecimal.valueOf(0.1d)),
                unitCount);

        stockVesting.addRecord(record.getEmployeeId(),record);
        stockVesting.addRecord(record2.getEmployeeId(),record2);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockOptionPortfolioValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 2);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),45d);

        result = valuationResults.get(1);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),0d);
    }

    @Test
    public void testEvaluatorIgnoredPerfValuationsByDate() {
        StockOptionPortfolio stockVesting = new StockOptionPortfolio();

        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingRecord record = RecordFixture.CreateVestingRecord(
                grantPrice,
                unitCount);

        PerformanceRecord record2 = RecordFixture.CreatePerfRecord(
                record.getEmployeeId(),
                DateParser.parseDate("20200101"),
                BigDecimal.valueOf(2d));

        stockVesting.addRecord(record.getEmployeeId(),record);
        stockVesting.addRecord(record2.getEmployeeId(),record2);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockOptionPortfolioValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 1);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),45d);
    }

    @Test
    public void testEvaluatorPerfValuationsSortCollision() {
        // Test expected behavior when VestingRecord and PerformanceRecord have the same date.
        StockOptionPortfolio stockVesting = new StockOptionPortfolio();

        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingRecord record = RecordFixture.CreateVestingRecord(
                grantPrice,
                unitCount);

        PerformanceRecord record2 = RecordFixture.CreatePerfRecord(
                BigDecimal.valueOf(2d));

        stockVesting.addRecord(record.getEmployeeId(),record);
        stockVesting.addRecord(record2.getEmployeeId(),record2);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockOptionPortfolioValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 1);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),90d);

        // Switch Valuation order, should have no impact on result.
        stockVesting = new StockOptionPortfolio();
        stockVesting.addRecord(record2.getEmployeeId(),record2);
        stockVesting.addRecord(record.getEmployeeId(),record);
        stockVesting.setValuationContext(context);

        valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 1);

        result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),90d);
    }

    @Test
    public void testEvaluatorUnderWaterValuation() {
        StockOptionPortfolio stockVesting = new StockOptionPortfolio();

        BigDecimal grantPrice = BigDecimal.valueOf(.9d);
        Integer unitCount = 100;
        VestingRecord record = RecordFixture.CreateVestingRecord(
                grantPrice,
                unitCount);

        VestingRecord record2 = RecordFixture.CreateVestingRecord(
                grantPrice.add(BigDecimal.valueOf(0.6d)), // 1.5
                unitCount);

        stockVesting.addRecord(record.getEmployeeId(),record);
        stockVesting.addRecord(record2.getEmployeeId(),record2);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockOptionPortfolioValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 1);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),10d);
    }
}
