package model.csvstransform.readers;

import model.csvtransform.parsers.DateParser;
import model.csvtransform.parsers.ValuationContextSimpleParser;
import model.csvtransform.parsers.VestingValuationRecordParser;
import model.valuation.AbstractValuation;
import model.valuation.ValuationContext;
import model.valuation.VestingValuation;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class ValuationContextSimpleParserTest {

    private ValuationContextSimpleParser parser;

    @BeforeTest
    public void setUp(){
        this.parser = new ValuationContextSimpleParser();
    }

    @Test
    public void testValuationContextParser() throws ParseException, IOException {
        String valuationDate = "20130101";
        Double marketPrice = 1d;
        String[] inputArray = {valuationDate, marketPrice.toString()};

        ValuationContext context = this.parser.processLine(inputArray);
        Assert.assertEquals(context.getMarketPrice().doubleValue(), marketPrice);
        Assert.assertEquals(context.getValuationDate(), DateParser.parseDate(valuationDate));
    }

    @Test
    public void testValuationContextParserIncorrectArrayLength() {
        String valuationDate = "20130101";
        Double marketPrice = 1d;
        String[] inputArray = {valuationDate, marketPrice.toString(), "EXTRA_ITEM"};

        try{
            this.parser.processLine(inputArray);
            Assert.fail("Expected exception thrown.");
        }catch (IOException ioe){
            // Expected IOExceptions... Pass
        }
    }

    @Test
    public void testValuationContextParserReversedParameters() {
        String valuationDate = "20130101";
        Double marketPrice = 1d;
        String[] inputArray = {marketPrice.toString(),valuationDate };

        try{
            this.parser.processLine(inputArray);
            Assert.fail("Expected exception thrown.");
        }catch (IOException ioe){
            // Expected IOExceptions... Pass
        }
    }
}
