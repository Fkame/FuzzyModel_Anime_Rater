package app.fuzzyModelCore.function;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import app.RatingFuzzyModel.fuzzyModelCore.function.SigmoidFunction;

public class TestSigmoidFunction {
    
    @Test
    public void testCalculateY() {
        double[] xValues =      { 0,    3,     6,    10};
        double[] expectedY =    { 0.05, 0.5,   0.95, 1};

        double leftXLimit = 0;
        double rightXLimit = 10;
        double bendCoef = 1;
        double shift = 3;
        SigmoidFunction f = new SigmoidFunction(leftXLimit, rightXLimit, bendCoef, shift);
        for (int i = 0; i < xValues.length; i++) {
            double y = f.calculateY(xValues[i]);
            System.out.println("x=" + xValues[i] + ", expectedY=" + expectedY[i] + ", currentY=" + y);
            assertEquals(expectedY[i], y, 0.01);
        }

    }
}
