import controller.StockOptionPortfolioController;
import dependencyInjectSimulator.DependencyInjectionSimulator;

import java.io.IOException;

public class Main {
    public static void main (String [] args) throws IOException {

        StockOptionPortfolioController controller = DependencyInjectionSimulator.buildStockOptionPortfolioController();

        controller.evaluateStockVestings(System.in, System.out);
    }
}
