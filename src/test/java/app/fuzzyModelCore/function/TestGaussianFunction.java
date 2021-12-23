package app.fuzzyModelCore.function;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import app.RatingFuzzyModel.fuzzyModelCore.function.GaussianFunction;

public class TestGaussianFunction {
    
    @Test
    public void testCalculateY1() {
        double[] xValues =      {0,     -1,     1};
        double[] expectedY =    {1,     0.6,    0.6};

        double leftXLimit = -2;
        double rightXLimit = 2;
        double bellWidth = 1;
        double bellTopPoint = 0;
        GaussianFunction f = new GaussianFunction(leftXLimit, rightXLimit, bellTopPoint, bellWidth);
        for (int i = 0; i < xValues.length; i++) {
            double y = f.calculateY(xValues[i]);
            System.out.println("x=" + xValues[i] + ", expectedY=" + expectedY[i] + ", currentY=" + y);
            assertEquals(expectedY[i], y, 0.01);
        }
    }
}
