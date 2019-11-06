package model.csvtransform.parsers;

import enums.ValuationType;

import java.util.HashMap;

public class ValuationRecordParserFactory {

    private static ValuationRecordParserFactory instance = new ValuationRecordParserFactory();

    public static ValuationRecordParserFactory getInstance() {
        return instance;
    }

    private static HashMap<ValuationType, ValuationRecordParser> csvParsers = new HashMap<ValuationType, ValuationRecordParser>();

    static {
        csvParsers.put(ValuationType.VEST, new VestingValuationRecordParser());
        csvParsers.put(ValuationType.PERF, new PerformanceValuationRecordParser());
        csvParsers.put(ValuationType.SALE, new SaleValuationRecordParser());
    }

    private ValuationRecordParserFactory() {
    }


    public ValuationRecordParser getCsvLineParser(ValuationType valuationType) {
        return csvParsers.get(valuationType);
    }
}
