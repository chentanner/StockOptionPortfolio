package model.csvstransform.readers;

import model.csvtransform.parsers.DateParser;
import model.csvtransform.parsers.VestingValuationRecordParser;
import model.valuation.AbstractValuation;
import model.valuation.ValuationContext;
import model.valuation.ValuationFixture;
import model.valuation.VestingValuation;
import model.valuation.evaluators.VestingRecordEvaluator;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class VestingValuationRecordParserTest {

    private VestingValuationRecordParser parser;

    @BeforeTest
    public void setUp(){
        this.parser = new VestingValuationRecordParser();
    }

    @Test
    public void testVestingParser() throws ParseException, IOException {
        String type = "VEST";
        String employeeId = "emp1";
        String date = "20130101";
        Integer unitCount = 1000;
        Double grantPrice = 0.45d;
        String[] inputArray = {type, employeeId, date, unitCount.toString(), grantPrice.toString()};

        AbstractValuation valuation = this.parser.processLine(inputArray);
        VestingValuation vestingValuation = (VestingValuation)valuation;
        Assert.assertEquals(valuation.getType().toString(), type);
        Assert.assertEquals(valuation.getEmployeeId(), employeeId);
        Assert.assertEquals(valuation.getRecordDate(), DateParser.parseDate(date));
        Assert.assertEquals(vestingValuation.getUnitCount(), unitCount);
        Assert.assertEquals(vestingValuation.getGrantPrice().doubleValue(), grantPrice);
    }

    @Test
    public void testVestingParserIncorrectArrayLength() {
        String type = "VEST";
        String employeeId = "emp1";
        String date = "20130101";
        Integer unitCount = 1000;
        Double grantPrice = 0.45d;
        String[] inputArray = {type, employeeId, date, unitCount.toString(), grantPrice.toString(), "EXTRA_ITEM"};

        try{
            AbstractValuation valuation = this.parser.processLine(inputArray);
            Assert.fail("Expected exception thrown.");
        }catch (IOException ioe){
            // Expected IOExceptions... Pass
        }
    }
}
