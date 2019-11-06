package model.csvtransform.parsers;

import enums.ValuationType;
import model.valuation.AbstractValuation;
import model.valuation.StockOptionPortfolioValuation;
import model.valuation.ValuationContext;

import java.io.*;

public class StockVestingCsvParser implements StockVestingParser{

    private ValuationContextParser valuationContextParser;

    public StockVestingCsvParser(ValuationContextParser valuationContextParser){
        this.valuationContextParser = valuationContextParser;
    }

    /**
     * Will parse a stream containing a csv representation of a StockOptionPortfolioValuation
     * @param inStream
     * @return StockOptionPortfolioValuation
     * @throws IOException
     */
    public StockOptionPortfolioValuation parseStream(InputStream inStream) throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inStream))){
            return parseInputCsv(reader);
        }
    }

    private StockOptionPortfolioValuation parseInputCsv(BufferedReader reader) throws IOException {
        // First line contains a count of data rows to follow
        int lines = Integer.parseInt(getNextLine(reader));

        // TODO: consider making a stockVesting builder to protect object modifications after parsing csv.
        StockOptionPortfolioValuation stockVesting = new StockOptionPortfolioValuation();

        for (int i = 0; i < lines; i++){
            String [] splitLine = getNextLine(reader).split(",");

            ValuationType lineValuationType = ValuationType.valueOf(splitLine[0]);
            ValuationRecordParser recordParser = ValuationRecordParserFactory.getInstance().getCsvLineParser(lineValuationType);

            AbstractValuation valuation = recordParser.processLine(splitLine);
            stockVesting.addValuation(valuation.getEmployeeId(), valuation);
        }

        // The last line contains the values for the valuation context
        ValuationContext valuationContext;
        try{
            valuationContext = this.valuationContextParser.processLine(getNextLine(reader).split(","));
        }catch (IOException ioe){
            throw new IOException("CSV file parser failed to parse the last line.", ioe);
        }
        stockVesting.setValuationContext(valuationContext);

        return stockVesting;
    }

    private String getNextLine(BufferedReader reader) throws IOException {
        if(reader.ready()) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("End of file has been reached.");
            }
            return line;
        }
        throw new IOException("No data was passed as input.");
    }
}
