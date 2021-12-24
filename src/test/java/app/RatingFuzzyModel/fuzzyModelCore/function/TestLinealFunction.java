package app.RatingFuzzyModel.fuzzyModelCore.function;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestLinealFunction {
    
    @Test
    public void testBorderValues() {
        double[] xValues = {1, 2, 3};
        double[] yValues = {1, 0.5, 0};

        int firstX = 1;
        int firstY = 1;
        int secondX = 3;
        int secondY = 0;
        LinealFunction f = new LinealFunction(firstX, firstY, secondX, secondY);
        for (int i = 0; i < xValues.length; i++) {
            double y = f.calculateY(xValues[i]);
            System.out.println("x=" + xValues[i] + ", expectedY=" + yValues[i] + ", currentY=" + y);
            assertEquals(yValues[i], y, 0.01);
        }
    }
    
}
