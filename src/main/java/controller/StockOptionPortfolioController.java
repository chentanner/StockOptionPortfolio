package controller;

import java.io.InputStream;
import java.io.OutputStream;

public interface StockOptionPortfolioController {
    void evaluateStockVestings(InputStream streamIn, OutputStream streamOut);
}
