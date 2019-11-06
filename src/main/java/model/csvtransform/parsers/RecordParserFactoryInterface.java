package model.csvtransform.parsers;

import enums.RecordType;

public interface RecordParserFactoryInterface {

    public RecordParser getCsvLineParser(RecordType recordType);
}
