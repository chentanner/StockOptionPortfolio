package model.csvtransform.parsers;

import enums.RecordType;

import java.util.HashMap;

public class RecordParserFactory {

    private static RecordParserFactory instance = new RecordParserFactory();

    public static RecordParserFactory getInstance() {
        return instance;
    }

    private static HashMap<RecordType, RecordParser> csvParsers = new HashMap<RecordType, RecordParser>();

    static {
        csvParsers.put(RecordType.VEST, new VestingRecordParser());
        csvParsers.put(RecordType.PERF, new PerformanceRecordParser());
        csvParsers.put(RecordType.SALE, new SaleRecordParser());
    }

    private RecordParserFactory() {
    }


    public RecordParser getCsvLineParser(RecordType recordType) {
        return csvParsers.get(recordType);
    }
}
