package model.valuation.evaluators;

import model.csvtransform.parsers.DateParser;
import model.valuation.ValuationContext;
import model.valuation.ValuationFixture;
import model.valuation.VestingValuation;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;

public class VestingRecordEvaluatorTest {

    private VestingRecordEvaluator evaluator;

    @BeforeTest
    public void setUp(){
        this.evaluator = new VestingRecordEvaluator();
    }

    @Test
    public void testEvaluator() throws ParseException {
        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingValuation valuation = ValuationFixture.CreateVestingValuation(
                grantPrice,
                unitCount);


        BigDecimal marketPrice = BigDecimal.valueOf(1);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionPortfolio portfolio = new StockOptionPortfolio();

        StockOptionPortfolio resultPortfolio = evaluator.evaluate(valuation, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueToGain(marketPrice).doubleValue(), 45d);
    }

    @Test
    public void testEvaluatorNegativeGains() throws ParseException {
        BigDecimal grantPrice = BigDecimal.valueOf(.55d);
        Integer unitCount = 100;
        VestingValuation valuation = ValuationFixture.CreateVestingValuation(
                grantPrice,
                unitCount);


        BigDecimal marketPrice = BigDecimal.valueOf(.3d);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionPortfolio portfolio = new StockOptionPortfolio();

        StockOptionPortfolio resultPortfolio = evaluator.evaluate(valuation, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueToGain(marketPrice), BigDecimal.ZERO);
    }

    @Test
    public void testEvaluatorEqualMarketAndGrantPrice() throws ParseException {
        BigDecimal grantPrice = BigDecimal.valueOf(1d);
        Integer unitCount = 100;
        VestingValuation valuation = ValuationFixture.CreateVestingValuation(
                grantPrice,
                unitCount);


        BigDecimal marketPrice = BigDecimal.valueOf(1.0d);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionPortfolio portfolio = new StockOptionPortfolio();

        StockOptionPortfolio resultPortfolio = evaluator.evaluate(valuation, context, portfolio);

        BigDecimal formattedZero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        Assert.assertEquals(resultPortfolio.getTotalValueToGain(marketPrice), BigDecimal.ZERO.setScale(1));
    }

    @Test
    public void testEvaluatorZeroUnitCount() throws ParseException {
        BigDecimal grantPrice = BigDecimal.valueOf(.45d);
        Integer unitCount = 0;
        VestingValuation valuation = ValuationFixture.CreateVestingValuation(
                grantPrice,
                unitCount);


        BigDecimal marketPrice = BigDecimal.valueOf(1d);
        Date evaluationDate = DateParser.parseDate("20140101");
        ValuationContext context = new ValuationContext();
        context.setMarketPrice(marketPrice);
        context.setValuationDate(evaluationDate);

        StockOptionPortfolio portfolio = new StockOptionPortfolio();

        StockOptionPortfolio resultPortfolio = evaluator.evaluate(valuation, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueToGain(marketPrice), BigDecimal.ZERO);
    }
}
