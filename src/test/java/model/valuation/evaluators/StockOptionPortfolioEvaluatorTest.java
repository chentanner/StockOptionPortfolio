package model.valuation.evaluators;

import model.csvtransform.parsers.DateParser;
import model.valuation.*;
import model.valuation.results.StockVestingValuationResult;
import model.valuation.results.ValuationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class StockOptionPortfolioEvaluatorTest {


    private StockOptionPortfolioEvaluator evaluator;

    @BeforeTest
    public void setUp(){
        this.evaluator = new StockOptionPortfolioEvaluator();
    }

    @Test
    public void testEvaluator() throws ParseException {
        StockOptionPortfolioValuation stockVesting = new StockOptionPortfolioValuation();

        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingValuation valuation = ValuationFixture.CreateVestingValuation(
                grantPrice,
                unitCount);

        VestingValuation valuation2 = ValuationFixture.CreateVestingValuation(
                "employee2",
                DateParser.parseDate("20140101"),
                grantPrice.add(BigDecimal.valueOf(0.1d)),
                unitCount);

        stockVesting.addValuation(valuation.getEmployeeId(),valuation);
        stockVesting.addValuation(valuation2.getEmployeeId(),valuation2);

        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockVestingValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 2);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),45d);

        result = valuationResults.get(1);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),35d);
    }

    @Test
    public void testEvaluatorNoValuationsOrContext() throws ParseException {
        StockOptionPortfolioValuation stockVesting = new StockOptionPortfolioValuation();

        StockVestingValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 0);
    }

    @Test
    public void testEvaluatorIgnoredVestingValuationsByDate() throws ParseException {
        StockOptionPortfolioValuation stockVesting = new StockOptionPortfolioValuation();

        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingValuation valuation = ValuationFixture.CreateVestingValuation(
                grantPrice,
                unitCount);

        VestingValuation valuation2 = ValuationFixture.CreateVestingValuation(
                "employee2",
                DateParser.parseDate("20200101"),
                grantPrice.add(BigDecimal.valueOf(0.1d)),
                unitCount);

        stockVesting.addValuation(valuation.getEmployeeId(),valuation);
        stockVesting.addValuation(valuation2.getEmployeeId(),valuation2);


        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockVestingValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 2);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),45d);

        result = valuationResults.get(1);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),0d);
    }

    @Test
    public void testEvaluatorIgnoredPerfValuationsByDate() throws ParseException {
        StockOptionPortfolioValuation stockVesting = new StockOptionPortfolioValuation();

        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingValuation valuation = ValuationFixture.CreateVestingValuation(
                grantPrice,
                unitCount);

        PerfValuation valuation2 = ValuationFixture.CreatePerfValuation(
                valuation.getEmployeeId(),
                DateParser.parseDate("20200101"),
                BigDecimal.valueOf(2d));

        stockVesting.addValuation(valuation.getEmployeeId(),valuation);
        stockVesting.addValuation(valuation2.getEmployeeId(),valuation2);


        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockVestingValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 1);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),45d);
    }

    @Test
    public void testEvaluatorPerfValuationsSortCollision() throws ParseException {
        // Test expected behavior when VestingValuation and PerfValuation have the same date.
        StockOptionPortfolioValuation stockVesting = new StockOptionPortfolioValuation();

        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingValuation valuation = ValuationFixture.CreateVestingValuation(
                grantPrice,
                unitCount);

        PerfValuation valuation2 = ValuationFixture.CreatePerfValuation(
                BigDecimal.valueOf(2d));

        stockVesting.addValuation(valuation.getEmployeeId(),valuation);
        stockVesting.addValuation(valuation2.getEmployeeId(),valuation2);


        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockVestingValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 1);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),90d);

        // Switch Valuation order, should have no impact on result.
        stockVesting = new StockOptionPortfolioValuation();
        stockVesting.addValuation(valuation2.getEmployeeId(),valuation2);
        stockVesting.addValuation(valuation.getEmployeeId(),valuation);
        stockVesting.setValuationContext(context);

        valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 1);

        result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),90d);
    }

    @Test
    public void testEvaluatorUnderWaterValuation() throws ParseException {
        StockOptionPortfolioValuation stockVesting = new StockOptionPortfolioValuation();

        BigDecimal grantPrice = BigDecimal.valueOf(.9d);
        Integer unitCount = 100;
        VestingValuation valuation = ValuationFixture.CreateVestingValuation(
                grantPrice,
                unitCount);

        VestingValuation valuation2 = ValuationFixture.CreateVestingValuation(
                grantPrice.add(BigDecimal.valueOf(0.6d)), // 1.5
                unitCount);

        stockVesting.addValuation(valuation.getEmployeeId(),valuation);
        stockVesting.addValuation(valuation2.getEmployeeId(),valuation2);


        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20150101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);
        stockVesting.setValuationContext(context);

        StockVestingValuationResult valuationResult = evaluator.evaluateStockVestingValuation(stockVesting);
        List<ValuationResult> valuationResults = valuationResult.getValuationResults();

        Assert.assertEquals(valuationResults.size(), 1);

        ValuationResult result = valuationResults.get(0);
        Assert.assertEquals(result.getTotalCashToGain().doubleValue(),10d);
    }

}
