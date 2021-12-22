package app.fuzzyModelCore.function;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Сигмоидальная функция - сигмоида.
 * <p>Формула: y(x) = 1 / [1 + exp(-1 * b * (x - c))]
 * <p>b - bending coefficient (bendCoef). При b > 0 - функция возрастает, при b < 0 - убывает. 
 * В целом, чем больше число в любую из сторон, тем "круче" становится сигмоида, она начинает больше ложиться на верхние 
 * и нижние пределы.
 * <p>c - shiftOnX, в целом это примерно сдвиг сигмоиды по оси Х. Также это значение Х при котором y(x) = 0.5.
 */
public class SigmoidFunction implements IFunction {
    
    private BigDecimal bendCoef;
    private BigDecimal shiftOnX;
    private BigDecimal leftXLimit;
    private BigDecimal rightXLimit;

    public int scaleInCalucations = 3;

    public SigmoidFunction(double leftXLimit, double rightXLimit, double bendCoef, double shiftOnX) {
        if (leftXLimit > rightXLimit) throw new IllegalArgumentException("left limit > right limit");
        this.bendCoef = BigDecimal.valueOf(bendCoef);
        this.shiftOnX = BigDecimal.valueOf(shiftOnX);
        this.leftXLimit = BigDecimal.valueOf(leftXLimit);
        this.rightXLimit = BigDecimal.valueOf(rightXLimit);
    }

    public double calculateY (double xValue) {
        BigDecimal x = BigDecimal.valueOf(xValue);
        if (x.compareTo(rightXLimit) == 1 | x.compareTo(leftXLimit) == -1) return 0;

        BigDecimal xMinusShift = x.subtract(shiftOnX);
        BigDecimal degree = bendCoef.negate().multiply(xMinusShift);
        BigDecimal exponent = BigDecimal.valueOf(Math.exp(degree.doubleValue()));
        BigDecimal denominator = BigDecimal.ONE.add(exponent);
        BigDecimal y = BigDecimal.ONE.divide(denominator, scaleInCalucations, RoundingMode.HALF_EVEN);
        return y.doubleValue();
    }

    public double getLeftXLimit() {
        return this.leftXLimit.doubleValue();
    }

    public double getRightXLimit() {
        return this.rightXLimit.doubleValue();
    }

}
