package integration;

import controller.StockOptionPortfolioCliController;
import model.csvtransform.parsers.StockOptionPortfolioCsvParser;
import model.csvtransform.parsers.StockOptionPortfolioParser;
import model.csvtransform.parsers.ValuationContextCsvParser;
import model.csvtransform.parsers.ValuationContextParser;
import model.csvtransform.writers.StockVestingValuationResultCsvWriter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.StockVestingService;
import service.StockVestingValuationService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StockOptionPortfolioIntegrationTests {
    private StockOptionPortfolioCliController controller;

    @BeforeTest
    public void setUp(){
        ValuationContextCsvParser valuationContextCsvParser = new ValuationContextParser();
        StockOptionPortfolioParser stockOptionPortfolioParser = new StockOptionPortfolioCsvParser(valuationContextCsvParser);
        StockVestingValuationResultCsvWriter stockVestingWriter = new StockVestingValuationResultCsvWriter();

        StockVestingValuationService stockVestingService = new StockVestingService();

        this.controller = new StockOptionPortfolioCliController(
                stockVestingService,
                stockOptionPortfolioParser,
                stockVestingWriter);
    }

    public void testFirstExample(){
        String inputString = "5\nVEST,001B,20120101,1000,0.45\nVEST,002B,20120101,1500,0.45\nVEST,002B,20130101,1000,0.50\nVEST,001B,20130101,1500,0.50\nVEST,003B,20130101,1000,0.50\n20140101,1.00";
        String outputString = "001B,1300.00,0.00\n002B,1325.00,0.00\n003B,500.00,0.00".replace("\n",System.lineSeparator());

        executeEndToEndTest(inputString, outputString);
    }

    @Test
    public void testFirstExampleModifiedDate(){
        String inputString = "5\nVEST,001B,20120101,1000,0.45\nVEST,002B,20120101,1500,0.45\nVEST,002B,20130101,1000,0.50\nVEST,001B,20130101,1500,0.50\nVEST,003B,20130101,1000,0.50\n20120615,1.00";
        String outputString = "001B,550.00,0.00\n002B,825.00,0.00\n003B,0.00,0.00".replace("\n",System.lineSeparator());

        executeEndToEndTest(inputString, outputString);
    }

    @Test
    public void testSecondExample(){
        String inputString = "5\nVEST,001B,20120102,1000,0.45\nVEST,002B,20120102,1000,0.45\nVEST,003B,20120102,1000,0.45\nPERF,001B,20130102,1.5\nPERF,002B,20130102,1.5\n20140101,1.00";
        String outputString = "001B,825.00,0.00\n002B,825.00,0.00\n003B,550.00,0.00".replace("\n",System.lineSeparator());

        executeEndToEndTest(inputString, outputString);
    }

    @Test
    public void testSecondExampleModifiedDate(){
        String inputString = "5\nVEST,001B,20120102,1000,0.45\nVEST,002B,20120102,1000,0.45\nVEST,003B,20120102,1000,0.45\nPERF,001B,20130102,1.5\nPERF,002B,20130102,1.5\n20130101,1.00";
        String outputString = "001B,550.00,0.00\n002B,550.00,0.00\n003B,550.00,0.00".replace("\n",System.lineSeparator());

        executeEndToEndTest(inputString, outputString);
    }

    @Test
    public void testThirdExample(){
        String inputString = "5\nVEST,001B,20120102,1000,0.45\nSALE,001B,20120402,500,1.00\nVEST,002B,20120102,1000,0.45\nPERF,001B,20130102,1.5\nPERF,002B,20130102,1.5\n20140101,1.00";
        String outputString = "001B,412.50,275.00\n002B,825.00,0.00".replace("\n",System.lineSeparator());

        executeEndToEndTest(inputString, outputString);
    }

    @Test
    public void testThirdExampleModifiedDate(){
        String inputString = "5\nVEST,001B,20120102,1000,0.45\nSALE,001B,20120402,500,1.00\nVEST,002B,20120102,1000,0.45\nPERF,001B,20130102,1.5\nPERF,002B,20130102,1.5\n20130101,1.00";
        String outputString = "001B,275.00,275.00\n002B,550.00,0.00".replace("\n",System.lineSeparator());

        executeEndToEndTest(inputString, outputString);
    }

    private void executeEndToEndTest(
            String input,
            String expectedOutput){

        String outputString = null;
        try (InputStream inStream = new java.io.ByteArrayInputStream(input.getBytes());
             ByteArrayOutputStream outStream = new ByteArrayOutputStream()){
            this.controller.evaluateStockVestings(inStream, outStream);
            outputString = new String(outStream.toByteArray());
        } catch (IOException e) {
            Assert.fail("Wasn't expecting an exception");
        }

        Assert.assertNotNull(outputString);
        Assert.assertEquals(outputString,expectedOutput);
    }
}
