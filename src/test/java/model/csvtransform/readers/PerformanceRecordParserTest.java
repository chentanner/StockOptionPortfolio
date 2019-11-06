package model.csvtransform.readers;

import model.csvtransform.parsers.DateParser;
import model.csvtransform.parsers.PerformanceRecordParser;
import model.valuation.AbstractRecord;
import model.valuation.PerformanceRecord;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class PerformanceRecordParserTest {

    private PerformanceRecordParser parser;

    @BeforeTest
    public void setUp(){
        this.parser = new PerformanceRecordParser();
    }

    @Test
    public void testPerformanceParser() throws IOException {
        String type = "PERF";
        String employeeId = "emp1";
        String date = "20130101";
        Double performanceMultiplier = 1.5d;
        String[] inputArray = {type, employeeId, date, performanceMultiplier.toString()};

        AbstractRecord record = this.parser.processLine(inputArray);
        PerformanceRecord performanceRecord = (PerformanceRecord)record;
        Assert.assertEquals(record.getType().toString(), type);
        Assert.assertEquals(record.getEmployeeId(), employeeId);
        Assert.assertEquals(record.getRecordDate(), DateParser.parseDate(date));
        Assert.assertEquals(performanceRecord.getPerformanceMultiplier().doubleValue(), performanceMultiplier);
    }

    @Test
    public void testPerformanceParserIncorrectArrayLength() {
        String type = "PERF";
        String employeeId = "emp1";
        String date = "20130101";
        Double performanceMultiplier = 1.5d;
        String[] inputArray = {type, employeeId, date, performanceMultiplier.toString(), "EXTRA_ITEM"};

        try{
            this.parser.processLine(inputArray);
            Assert.fail("Expected exception thrown.");
        }catch (IOException ioe){
            // Expected IOExceptions... Pass
        }
    }
}
