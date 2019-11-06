package model.csvtransform.parsers;

import model.valuation.AbstractRecord;
import model.valuation.SaleRecord;

import java.io.IOException;
import java.math.BigDecimal;

public class SaleRecordParser extends AbstractRecordParser {

    public AbstractRecord processLine (String [] splitLine) throws IOException {
        if(splitLine.length != 5){
            throw new IOException("Invalid number of sale record properties.");
        }

        SaleRecord record = new SaleRecord();
        super.parseBaseProperties(splitLine, record);

        try{
            record.setAmountSold(Integer.parseInt(splitLine[3]));
            record.setSalePrice(BigDecimal.valueOf(Double.parseDouble(splitLine[4])));
        } catch (Exception e) {
            throw new IOException("Couldn't parse sale record's, amount sold and sale price.");
        }

        return record;
    }
}
