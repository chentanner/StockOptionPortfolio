package model.valuation;

import model.csvtransform.parsers.DateParser;

import java.math.BigDecimal;
import java.util.Date;

public class RecordFixture {
    public static VestingRecord CreateVestingRecord(){
        return CreateVestingRecord(
                BigDecimal.valueOf(.55d),
                100);
    }

    public static VestingRecord CreateVestingRecord(
            BigDecimal grantPrice,
            Integer unitCount){
        return CreateVestingRecord(
                "employeeId1",
                DateParser.parseDate("20130101"),
                grantPrice,
                unitCount);
    }

    public static VestingRecord CreateVestingRecord(
            String employeeId,
            Date recordDate,
            BigDecimal grantPrice,
            Integer unitCount){
        VestingRecord record = new VestingRecord();
        record.setEmployeeId(employeeId);
        record.setRecordDate(recordDate);
        record.setGrantPrice(grantPrice);
        record.setUnitCount(unitCount);
        return record;
    }

    public static SaleRecord CreateSaleRecord(
            BigDecimal salePrice,
            Integer amountSold){

        return CreateSaleRecord(
                "employeeId1",
                DateParser.parseDate("20130101"),
                salePrice,
                amountSold);
    }

    public static SaleRecord CreateSaleRecord(
            String employeeId,
            Date recordDate,
            BigDecimal salePrice,
            Integer amountSold){
        SaleRecord record = new SaleRecord();
        record.setEmployeeId(employeeId);
        record.setRecordDate(recordDate);
        record.setSalePrice(salePrice);
        record.setAmountSold(amountSold);
        return record;
    }

    public static PerformanceRecord CreatePerfRecord(
            BigDecimal performanceMultiplier){
        return CreatePerfRecord(
                "employeeId1",
                DateParser.parseDate("20130101"),
                performanceMultiplier);
    }

    public static PerformanceRecord CreatePerfRecord(
            String employeeId,
            Date recordDate,
            BigDecimal performanceMultiplier){
        PerformanceRecord record = new PerformanceRecord();
        record.setEmployeeId(employeeId);
        record.setRecordDate(recordDate);
        record.setPerformanceMultiplier(performanceMultiplier);
        return record;
    }

}
