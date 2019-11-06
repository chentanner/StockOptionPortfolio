package model.csvtransform.parsers;

import model.valuation.AbstractRecord;
import model.valuation.VestingRecord;

import java.io.IOException;
import java.math.BigDecimal;

public class VestingRecordParser extends AbstractRecordParser {

    public AbstractRecord processLine (String [] splitLine) throws IOException {
        if(splitLine.length != 5){
            throw new IOException("Invalid number of vesting record properties");
        }

        VestingRecord vestingRecord = new VestingRecord();
        super.parseBaseProperties(splitLine, vestingRecord);

        try{
            vestingRecord.setUnitCount(Integer.parseInt(splitLine[3]));
            vestingRecord.setGrantPrice(BigDecimal.valueOf(Double.parseDouble(splitLine[4])));
        } catch (Exception e) {
            throw new IOException("Couldn't parse vesting record's unit count and grant price.");
        }

        return vestingRecord;
    }
}
