package model.valuation.evaluators;

import enums.ValuationType;

import java.util.HashMap;

public class ValuationRecordEvaluatorFactory {

    private static ValuationRecordEvaluatorFactory instance = new ValuationRecordEvaluatorFactory();

    public static ValuationRecordEvaluatorFactory getInstance() {
        return instance;
    }

    private static HashMap<ValuationType, ValuationRecordEvaluator> evaluators = new HashMap<ValuationType, ValuationRecordEvaluator>();

    static {
        evaluators.put(ValuationType.VEST, new VestingRecordEvaluator());
        evaluators.put(ValuationType.PERF, new PerformanceRecordEvaluator());
        evaluators.put(ValuationType.SALE, new SaleRecordEvaluator());
    }

    private ValuationRecordEvaluatorFactory() {
    }


    public ValuationRecordEvaluator getEvaluator(ValuationType valuationType) {
        return evaluators.get(valuationType);
    }
}
