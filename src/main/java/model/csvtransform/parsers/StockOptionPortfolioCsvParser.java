package model.csvtransform.parsers;

import enums.RecordType;
import model.valuation.AbstractRecord;
import model.valuation.StockOptionPortfolio;
import model.valuation.ValuationContext;

import java.io.*;

public class StockOptionPortfolioCsvParser implements StockOptionPortfolioParser {

    private ValuationContextCsvParser valuationContextCsvParser;
    private RecordParserFactoryInterface recordParserFactory;

    public StockOptionPortfolioCsvParser(ValuationContextCsvParser valuationContextCsvParser, RecordParserFactoryInterface recordParserFactory){
        this.valuationContextCsvParser = valuationContextCsvParser;
        this.recordParserFactory = recordParserFactory;
    }

    /**
     * Will parse a stream containing a csv representation of a StockOptionPortfolio
     * @param inStream
     * @return StockOptionPortfolio
     * @throws IOException
     */
    public StockOptionPortfolio parseStream(InputStream inStream) throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inStream))){
            return parseInputCsv(reader);
        }
    }

    private StockOptionPortfolio parseInputCsv(BufferedReader reader) throws IOException {
        // First line contains a count of data rows to follow
        int lines = Integer.parseInt(getNextLine(reader));

        StockOptionPortfolio stockVesting = new StockOptionPortfolio();

        for (int i = 0; i < lines; i++){
            String [] splitLine = getNextLine(reader).split(",");

            RecordType lineRecordType = RecordType.valueOf(splitLine[0]);
            RecordParser recordParser = this.recordParserFactory.getCsvLineParser(lineRecordType);

            AbstractRecord record = recordParser.processLine(splitLine);
            stockVesting.addRecord(record.getEmployeeId(), record);
        }

        // The last line contains the values for the valuation context
        ValuationContext valuationContext;
        try{
            valuationContext = this.valuationContextCsvParser.processLine(getNextLine(reader).split(","));
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
