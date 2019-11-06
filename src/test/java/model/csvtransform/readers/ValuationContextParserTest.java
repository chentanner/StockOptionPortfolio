package model.csvtransform.readers;

import model.csvtransform.parsers.DateParser;
import model.csvtransform.parsers.ValuationContextParser;
import model.valuation.ValuationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class ValuationContextParserTest {

    private ValuationContextParser parser;

    @BeforeTest
    public void setUp(){
        this.parser = new ValuationContextParser();
    }

    @Test
    public void testValuationContextParser() throws IOException {
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
