package model.valuation.evaluators;

import model.valuation.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SaleRecordEvaluatorTest {

    private SaleRecordEvaluator evaluator;

    @BeforeTest
    public void setUp(){
        this.evaluator = new SaleRecordEvaluator();
    }

    @Test
    public void testEvaluator() {
        BigDecimal salePrice = BigDecimal.valueOf(.55d);
        Integer amountSold = 100;
        SaleRecord record = RecordFixture.CreateSaleRecord(
                salePrice,
                amountSold);

        ValuationContext context = ValuationContextFixture.CreateValuationContext();

        // Initialize portfollio so the sale will be valid.
        StockOptionValuation portfolio = new StockOptionValuation();
        portfolio.addWeightedGrantPrice(BigDecimal.valueOf(0.2d), 500);

        StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueGained().doubleValue(), 35d);
    }

    @Test
    public void testEvaluatorSoldNegativeGains() {
        BigDecimal salePrice = BigDecimal.valueOf(.55d);
        Integer amountSold = 100;
        SaleRecord record = RecordFixture.CreateSaleRecord(
                salePrice,
                amountSold);

        ValuationContext context = ValuationContextFixture.CreateValuationContext();

        // Initialize portfollio so the sale will be valid.
        StockOptionValuation portfolio = new StockOptionValuation();
        portfolio.addWeightedGrantPrice(BigDecimal.valueOf(1d), 500);

        StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueGained().doubleValue(), -45d);
    }

    @Test
    public void testEvaluatorEqualMarketAndGrantPrice() {
        ValuationContext context = ValuationContextFixture.CreateValuationContext();

        BigDecimal salePrice = context.getMarketPrice();
        Integer amountSold = 100;
        SaleRecord record = RecordFixture.CreateSaleRecord(
                salePrice,
                amountSold);


        StockOptionValuation portfolio = new StockOptionValuation();
        portfolio.addWeightedGrantPrice(salePrice, 500);

        StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);

        BigDecimal formattedZero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        Assert.assertEquals(resultPortfolio.getTotalValueGained().doubleValue(), BigDecimal.ZERO.doubleValue());
    }

    @Test
    public void testEvaluatorSoldZeroUnits() {
        BigDecimal salePrice = BigDecimal.valueOf(.45d);
        Integer amountSold = 0;
        SaleRecord record = RecordFixture.CreateSaleRecord(
                salePrice,
                amountSold);

        ValuationContext context = ValuationContextFixture.CreateValuationContext();

        StockOptionValuation portfolio = new StockOptionValuation();
        portfolio.addWeightedGrantPrice(BigDecimal.ONE, 100);

        StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueGained().doubleValue(), BigDecimal.ZERO.doubleValue());
    }

    @Test
    public void testEvaluatorSoldNegativeUnits() {
        BigDecimal salePrice = BigDecimal.valueOf(.45d);
        Integer amountSold = -4;
        SaleRecord record = RecordFixture.CreateSaleRecord(
                salePrice,
                amountSold);

        ValuationContext context = ValuationContextFixture.CreateValuationContext();

        StockOptionValuation portfolio = new StockOptionValuation();

        try{
            StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);
            Assert.fail("Should of thrown error for over selling.");
        }catch (RuntimeException re){
            // Test passed
        }
    }

    @Test
    public void testEvaluatorSoldZeroUnitsWithNoVestings() {
        BigDecimal salePrice = BigDecimal.valueOf(.45d);
        Integer amountSold = 0;
        SaleRecord record = RecordFixture.CreateSaleRecord(
                salePrice,
                amountSold);

        ValuationContext context = ValuationContextFixture.CreateValuationContext();

        StockOptionValuation portfolio = new StockOptionValuation();

        StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);

        Assert.assertEquals(resultPortfolio.getTotalValueGained(), BigDecimal.ZERO);
    }

    @Test
    public void testEvaluatorOverSoldUnits() {
        BigDecimal salePrice = BigDecimal.valueOf(.45d);
        Integer amountSold = 200;
        SaleRecord record = RecordFixture.CreateSaleRecord(
                salePrice,
                amountSold);

        ValuationContext context = ValuationContextFixture.CreateValuationContext();

        StockOptionValuation portfolio = new StockOptionValuation();
        portfolio.addWeightedGrantPrice(BigDecimal.valueOf(1d),100);

        try{
            StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);
            Assert.fail("Should of thrown error for over selling.");
        }catch (RuntimeException re){
            // Test passed
        }
    }

    @Test
    public void testEvaluatorNoUnitsToSell() {
        BigDecimal salePrice = BigDecimal.valueOf(.45d);
        Integer amountSold = 100;
        SaleRecord record = RecordFixture.CreateSaleRecord(
                salePrice,
                amountSold);

        ValuationContext context = ValuationContextFixture.CreateValuationContext();

        StockOptionValuation portfolio = new StockOptionValuation();

        try{
            StockOptionValuation resultPortfolio = evaluator.evaluate(record, context, portfolio);
            Assert.fail("Should of thrown error for over selling.");
        }catch (RuntimeException re){
            // Test passed
        }
    }
}
