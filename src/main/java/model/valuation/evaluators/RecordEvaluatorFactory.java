package model.valuation.evaluators;

import enums.RecordType;

import java.util.HashMap;

public class RecordEvaluatorFactory {

    private static RecordEvaluatorFactory instance = new RecordEvaluatorFactory();

    public static RecordEvaluatorFactory getInstance() {
        return instance;
    }

    private static HashMap<RecordType, RecordEvaluator> evaluators = new HashMap<RecordType, RecordEvaluator>();

    static {
        evaluators.put(RecordType.VEST, new VestingRecordEvaluator());
        evaluators.put(RecordType.PERF, new PerformanceRecordEvaluator());
        evaluators.put(RecordType.SALE, new SaleRecordEvaluator());
    }

    private RecordEvaluatorFactory() {
    }


    public RecordEvaluator getEvaluator(RecordType recordType) {
        return evaluators.get(recordType);
    }
}
