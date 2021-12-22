package app.fuzzyModelCore.function;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Функция гаусса.
 * Классическая формула:
 * y = exp(-x^2 / 2).
 * 
 * <p>Применяемая формула:
 * y = exp(-(x - a)^2 / (2*b^2))
 * <p>a - точка по оси Х, в которой функция достигает максимального значения, также это точка перелома - 
 * после этой точки функция начинает нисходить.
 * <p>b - коэффициент, специальное значение. Чем выше значение b - тем уже колокол, чем ниже b - тем шире колокол.
 * <p>Имеет следующее отражение в функции: значение 2*b - ширина колокола (т.е. расстояние по оси Х между 2-мя точками) при 
 * значении функции примерно равном 2/3.
 * <p>То есть, если сделать срез функции на уровне y = (примерно) 2/3, то расстояние между попавшими в срез точками будет 2*b. 
 * 
 * <p>Если использовать классическую формулу, то b = 1, срезанные точки находятся по Х на -1 и 1.
 */
public class GaussianFunction implements IFunction {

    private BigDecimal leftXLimit;
    private BigDecimal rightXLimit;
    private BigDecimal bellTopPoint;
    private BigDecimal bellWidth;
    public int scaleInCalucations = 3;

    public GaussianFunction (double leftXLimit, double rightXLimit, double bellTopPoint, double bellWidth) {
        if (leftXLimit > rightXLimit) throw new IllegalArgumentException("left limit > right limit");
        if (bellTopPoint > rightXLimit | bellTopPoint < leftXLimit) 
            throw new IllegalArgumentException("middlePoint is not in limits interval");
        
        this.leftXLimit = BigDecimal.valueOf(leftXLimit);
        this.rightXLimit = BigDecimal.valueOf(rightXLimit);
        this.bellTopPoint = BigDecimal.valueOf(bellTopPoint);
        this.bellWidth = BigDecimal.valueOf(bellWidth);
    }

    public double calculateY (double xValue) {
        BigDecimal x = BigDecimal.valueOf(xValue);
        if (x.compareTo(rightXLimit) == 1 | x.compareTo(leftXLimit) == -1) return 0;

        BigDecimal numerator = x.subtract(bellTopPoint).pow(2);
        BigDecimal denominator = bellWidth.pow(2).multiply(BigDecimal.valueOf(2));
        BigDecimal degree = numerator.divide(denominator, scaleInCalucations, RoundingMode.HALF_EVEN).negate();

        return Math.exp(degree.doubleValue());
    }

    public double getLeftXLimit() {
        return this.leftXLimit.doubleValue();
    }

    public double getRightXLimit() {
        return this.rightXLimit.doubleValue();
    }

    
}
