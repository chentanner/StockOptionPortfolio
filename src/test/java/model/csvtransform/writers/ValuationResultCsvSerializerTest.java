package model.csvtransform.writers;

import model.valuation.results.ValuationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ValuationResultCsvSerializerTest {

    private ValuationResultCsvSerializer serializer;

    @BeforeTest
    public void setUp(){
        this.serializer = new ValuationResultCsvSerializer();
    }

    @Test
    public void testSerializeValuationResult() throws IOException {
        String employeeId = "emp1";
        BigDecimal totalCashToGain = BigDecimal.valueOf(1200);
        BigDecimal totalCashGained = BigDecimal.valueOf(225);

        ValuationResult valuationResult = new ValuationResult(employeeId, totalCashToGain, totalCashGained);

        String serializedString = serializer.serializeValuationResultToCsvLine(valuationResult);

        String[] splitString = serializedString.split(",");
        Assert.assertEquals(splitString.length, 3);
        Assert.assertEquals(splitString[0], employeeId);

        BigDecimal formattedTotalCashGain = totalCashToGain.setScale(2, RoundingMode.HALF_UP);
        Assert.assertEquals(splitString[1], formattedTotalCashGain.toPlainString());

        BigDecimal formattedTotalCashGained = totalCashGained.setScale(2, RoundingMode.HALF_UP);
        Assert.assertEquals(splitString[2], formattedTotalCashGained.toPlainString());
    }
}
