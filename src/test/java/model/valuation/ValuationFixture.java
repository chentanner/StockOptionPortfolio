package model.valuation;

import model.csvtransform.parsers.DateParser;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class ValuationFixture {
    public static VestingValuation CreateVestingValuation(){
        return CreateVestingValuation(
                BigDecimal.valueOf(.55d),
                100);
    }

    public static VestingValuation CreateVestingValuation(
            BigDecimal grantPrice,
            Integer unitCount){
        Date date = null;
        try {
            date = DateParser.parseDate("20130101");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return CreateVestingValuation(
                "employeeId1",
                date,
                grantPrice,
                unitCount);
    }

    public static VestingValuation CreateVestingValuation(
            String employeeId,
            Date recordDate,
            BigDecimal grantPrice,
            Integer unitCount){
        VestingValuation valuation = new VestingValuation();
        valuation.setEmployeeId(employeeId);
        valuation.setRecordDate(recordDate);
        valuation.setGrantPrice(grantPrice);
        valuation.setUnitCount(unitCount);
        return valuation;
    }

    public static PerfValuation CreatePerfValuation(
            BigDecimal performanceMultiplier){
        Date date = null;
        try {
            date = DateParser.parseDate("20130101");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return CreatePerfValuation(
                "employeeId1",
                date,
                performanceMultiplier);
    }

    public static PerfValuation CreatePerfValuation(
            String employeeId,
            Date recordDate,
            BigDecimal performanceMultiplier){
        PerfValuation valuation = new PerfValuation();
        valuation.setEmployeeId(employeeId);
        valuation.setRecordDate(recordDate);
        valuation.setPerformanceMultiplier(performanceMultiplier);
        return valuation;
    }

}
