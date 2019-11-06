package model.csvstransform.readers;

import model.csvtransform.parsers.DateParser;
import model.csvtransform.parsers.PerformanceValuationRecordParser;
import model.valuation.AbstractValuation;
import model.valuation.PerfValuation;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class PerformanceValuationRecordParserTest {

    private PerformanceValuationRecordParser parser;

    @BeforeTest
    public void setUp(){
        this.parser = new PerformanceValuationRecordParser();
    }

    @Test
    public void testPerformanceParser() throws ParseException, IOException {
        String type = "PERF";
        String employeeId = "emp1";
        String date = "20130101";
        Double performanceMultiplier = 1.5d;
        String[] inputArray = {type, employeeId, date, performanceMultiplier.toString()};

        AbstractValuation valuation = this.parser.processLine(inputArray);
        PerfValuation perfValuation = (PerfValuation)valuation;
        Assert.assertEquals(valuation.getType().toString(), type);
        Assert.assertEquals(valuation.getEmployeeId(), employeeId);
        Assert.assertEquals(valuation.getRecordDate(), DateParser.parseDate(date));
        Assert.assertEquals(perfValuation.getPerformanceMultiplier().doubleValue(), performanceMultiplier);
    }

    @Test
    public void testPerformanceParserIncorrectArrayLength() {
        String type = "PERF";
        String employeeId = "emp1";
        String date = "20130101";
        Double performanceMultiplier = 1.5d;
        String[] inputArray = {type, employeeId, date, performanceMultiplier.toString(), "EXTRA_ITEM"};

        try{
            AbstractValuation valuation = this.parser.processLine(inputArray);
            Assert.fail("Expected exception thrown.");
        }catch (IOException ioe){
            // Expected IOExceptions... Pass
        }
    }
}
