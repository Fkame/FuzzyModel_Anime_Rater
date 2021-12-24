package app.RatingFuzzyModel.fuzzyModelCore.function;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestQudraticFunction {

    @Test
    public void testCalculateY1() {
        double[] xValues =      {2,     3,      4};
        double[] expectedY =    {0,     0.5,    1};

        double leftXLimit = 2;
        double rightXLimit = 4;
        boolean isIncreasing = true;
        QudraticFunction f = new QudraticFunction(leftXLimit, rightXLimit, isIncreasing);
        for (int i = 0; i < xValues.length; i++) {
            double y = f.calculateY(xValues[i]);
            System.out.println("x=" + xValues[i] + ", expectedY=" + expectedY[i] + ", currentY=" + y);
            assertEquals(expectedY[i], y, 0.01);
        }
    }

    @Test
    public void testCalculateY2() {
        double[] xValues =      {2,     3,      4};
        double[] expectedY =    {1,     0.5,    0};

        double leftXLimit = 2;
        double rightXLimit = 4;
        boolean isIncreasing = false;
        QudraticFunction f = new QudraticFunction(leftXLimit, rightXLimit, isIncreasing);
        for (int i = 0; i < xValues.length; i++) {
            double y = f.calculateY(xValues[i]);
            System.out.println("x=" + xValues[i] + ", expectedY=" + expectedY[i] + ", currentY=" + y);
            assertEquals(expectedY[i], y, 0.01);
        }
    }

}
