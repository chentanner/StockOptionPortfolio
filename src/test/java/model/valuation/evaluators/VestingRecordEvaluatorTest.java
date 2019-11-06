package model.valuation.evaluators;

import model.csvtransform.parsers.DateParser;
import model.valuation.StockOptionValuation;
import model.valuation.ValuationContext;
import model.valuation.RecordFixture;
import model.valuation.VestingRecord;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

public class VestingRecordEvaluatorTest {

    private VestingRecordEvaluator evaluator;

    @BeforeTest
    public void setUp(){
        this.evaluator = new VestingRecordEvaluator();
    }

    @Test
    public void testEvaluator() {
        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingRecord record = RecordFixture.CreateVestingRecord(
                grantPrice,
                unitCount);


        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionValuation portfolio = new StockOptionValuation();

        StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueToGain(marketPrice).doubleValue(), 45d);
    }

    @Test
    public void testEvaluatorNegativeGains() {
        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingRecord record = RecordFixture.CreateVestingRecord(
                grantPrice,
                unitCount);


        BigDecimal marketPrice = BigDecimal.valueOf(.3d);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionValuation portfolio = new StockOptionValuation();

        StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueToGain(marketPrice), BigDecimal.ZERO);
    }

    @Test
    public void testEvaluatorEqualMarketAndGrantPrice() {
        BigDecimal grantPrice = BigDecimal.valueOf(1d);
        Integer unitCount = 100;
        VestingRecord record = RecordFixture.CreateVestingRecord(
                grantPrice,
                unitCount);


        BigDecimal marketPrice = BigDecimal.valueOf(1.0d);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionValuation portfolio = new StockOptionValuation();

        StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueToGain(marketPrice).doubleValue(), BigDecimal.ZERO.doubleValue());
    }

    @Test
    public void testEvaluatorZeroUnitCount() {
        BigDecimal grantPrice = BigDecimal.valueOf(.45d);
        Integer unitCount = 0;
        VestingRecord record = RecordFixture.CreateVestingRecord(
                grantPrice,
                unitCount);

        BigDecimal marketPrice = BigDecimal.valueOf(1d);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionValuation portfolio = new StockOptionValuation();

        StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueToGain(marketPrice), BigDecimal.ZERO);
    }
}
