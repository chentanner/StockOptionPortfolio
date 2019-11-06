package model.csvtransform.readers;

import model.csvtransform.parsers.DateParser;
import model.csvtransform.parsers.VestingRecordParser;
import model.valuation.AbstractRecord;
import model.valuation.VestingRecord;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class VestingRecordParserTest {

    private VestingRecordParser parser;

    @BeforeTest
    public void setUp(){
        this.parser = new VestingRecordParser();
    }

    @Test
    public void testVestingParser() throws IOException {
        String type = "VEST";
        String employeeId = "emp1";
        String date = "20130101";
        Integer unitCount = 1000;
        Double grantPrice = 0.45d;
        String[] inputArray = {type, employeeId, date, unitCount.toString(), grantPrice.toString()};

        AbstractRecord record = this.parser.processLine(inputArray);
        VestingRecord vestingValuation = (VestingRecord)record;
        Assert.assertEquals(record.getType().toString(), type);
        Assert.assertEquals(record.getEmployeeId(), employeeId);
        Assert.assertEquals(record.getRecordDate(), DateParser.parseDate(date));
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
            this.parser.processLine(inputArray);
            Assert.fail("Expected exception thrown.");
        }catch (IOException ioe){
            // Expected IOExceptions... Pass
        }
    }
}
