package model.csvstransform.readers;

import model.csvtransform.parsers.DateParser;
import model.csvtransform.parsers.StockVestingCsvParser;
import model.csvtransform.parsers.ValuationContextParser;
import model.csvtransform.parsers.ValuationContextSimpleParser;
import model.valuation.StockOptionPortfolioValuation;
import model.valuation.ValuationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Set;

public class StockVestingCsvParserTest {

    private StockVestingCsvParser stockVestingCsvParse;

    @BeforeTest
    public void setUp(){

        ValuationContextParser valuationContextParser = new ValuationContextSimpleParser();
        this.stockVestingCsvParse = new StockVestingCsvParser(valuationContextParser);
    }

    @Test
    public void testStockVestingCsvParse() throws IOException, ParseException {
        String string = "5\nVEST,001B,20120101,1000,0.45\nVEST,002B,20120101,1500,0.45\nVEST,002B,20130101,1000,0.50\nVEST,001B,20130101,1500,0.50\nVEST,003B,20130101,1000,0.50\n20140101,1.00";
        InputStream stringStream = new java.io.ByteArrayInputStream(string.getBytes());
        StockOptionPortfolioValuation stockVesting = this.stockVestingCsvParse.parseStream(stringStream);
        Assert.assertNotNull(stockVesting);

        Set<String> employeeIds = stockVesting.getEmployeeIds();
        for(String employeeId : employeeIds){
            Assert.assertNotNull(stockVesting.getEmployeeValuations(employeeId));
        }

        assertContext(
                stockVesting,
                "20140101",
                1d );
    }

    private void assertContext(StockOptionPortfolioValuation stockVesting, String expectedDate, Double expectedMarketPrice) throws ParseException {
        ValuationContext context = stockVesting.getValuationContext();
        Assert.assertEquals(context.getValuationDate(), DateParser.parseDate(expectedDate));
        Assert.assertEquals(context.getMarketPrice().doubleValue(),expectedMarketPrice);
    }
}
