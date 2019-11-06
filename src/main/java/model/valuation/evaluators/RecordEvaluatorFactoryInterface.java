package model.valuation.evaluators;

import enums.RecordType;

public interface RecordEvaluatorFactoryInterface {

    RecordEvaluator getEvaluator(RecordType recordType);
}
