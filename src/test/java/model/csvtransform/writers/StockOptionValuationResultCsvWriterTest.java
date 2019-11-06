package model.csvtransform.writers;

import model.valuation.results.StockOptionPortfolioValuationResult;
import model.valuation.results.ValuationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class StockOptionValuationResultCsvWriterTest {

    private StockVestingValuationResultCsvWriter writer;

    @BeforeTest
    public void setUp(){
        this.writer = new StockVestingValuationResultCsvWriter();
    }

    @Test
    public void testSerializeValuationResult() throws IOException {
        String employeeId = "emp1";
        BigDecimal totalCashToGain = BigDecimal.valueOf(1200);
        BigDecimal totalCashGained = BigDecimal.valueOf(325);
        String formattedTotalCashToGain = totalCashToGain.setScale(2, RoundingMode.HALF_UP).toPlainString();
        String formattedTotalCashGained = totalCashGained.setScale(2, RoundingMode.HALF_UP).toPlainString();
        ValuationResult valuationResult = new ValuationResult(employeeId, totalCashToGain, totalCashGained);

        String employeeId2 = "emp2";
        BigDecimal totalCashToGain2 = BigDecimal.valueOf(650);
        BigDecimal totalCashGained2 = BigDecimal.valueOf(0);
        String formattedTotalCashToGain2 = totalCashToGain2.setScale(2, RoundingMode.HALF_UP).toPlainString();
        String formattedTotalCashGained2 = totalCashGained2.setScale(2, RoundingMode.HALF_UP).toPlainString();
        ValuationResult valuationResult2 = new ValuationResult(employeeId2, totalCashToGain2, totalCashGained2);

        StockOptionPortfolioValuationResult vestingValuationResult = new StockOptionPortfolioValuationResult();
        vestingValuationResult.addValuationResult(valuationResult);
        vestingValuationResult.addValuationResult(valuationResult2);

        String outputString;
        try(ByteArrayOutputStream stream = new ByteArrayOutputStream()){
            writer.writeToStream(vestingValuationResult,stream);
            outputString = new String(stream.toByteArray());
        }

        // "emp1,1200.00,325.00\nemp2,650.00,0.00"
        String expectedString = employeeId+","+formattedTotalCashToGain+","+formattedTotalCashGained+System.lineSeparator()+employeeId2+","+formattedTotalCashToGain2+","+formattedTotalCashGained2;
        Assert.assertEquals(outputString, expectedString);
    }
}
