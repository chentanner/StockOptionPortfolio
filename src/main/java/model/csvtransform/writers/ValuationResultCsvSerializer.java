package model.csvtransform.writers;

import model.valuation.results.ValuationResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ValuationResultCsvSerializer {

    /**
     * Writes to the BufferedWriter the CSV representation of the valuation result.
     * <EmployeeId>,<FormatedTotalCashGain>
     * @param valuationResult to be written by the writter.
     * @throws IOException
     */
    public String serializeValuationResultToCsvLine(ValuationResult valuationResult) throws IOException {
        if(valuationResult == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(valuationResult.getEmployeeId());
        sb.append(",");
        sb.append(formatTotalCashGain(valuationResult.getTotalCashToGain()));
        sb.append(",");
        sb.append(formatTotalCashGain(valuationResult.getTotalCashGained()));

        return sb.toString();
    }

    /**
     * Returns the formatted value of for the total cash gain with
     * two decimal points and HALF_UP rounding.
     * @return rounded representation of total cash gain.
     */
    private String formatTotalCashGain(BigDecimal value){
        value = value.setScale(2, RoundingMode.HALF_UP);
        return value.toPlainString();
    }
}
