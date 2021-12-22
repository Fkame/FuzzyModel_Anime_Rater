package app.fuzzyModelCore.function;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Квадратичная функция.
 * <p>Представляет из себя 2 функции. Сначала считается по первой, при достижении середины отрезка, считается по второй.
 * <p>Общая формула:
 * <p>y = | !W - 2 * (x - a)^2 / (b - a)^ 2 |, если a <= x < (a + b) / 2;
 * <p>y = |  W - 2 * (b - x)^2 / (b - a)^ 2 |, если (a + b) / 2 <= x <= b;
 * <p>a - начало функции;
 * <p>b - конец функции;
 * <p>W - булевое значение "возрастает ли функция". Если функция возрастает - принимает значение 1, если нет - принимает значение 0. 
 * <p>Почему стоит модуль? Добавление булевой переменной для сокращения числа функци с 4-х до 2-х привело к тому, что могут
 * поменяться знаки из-за минуса после W. Чтобы убрать этот побочный эффект, уравнения были взяты в модуль. 
 * 
 * <p>В данной предметной области знак модуля в этих уравнениях ни на что не влияет, так как функция принадлежности принимает
 * значения от 0 до 1.
 * 
 */
public class QudraticFunction implements IFunction {
    
    private boolean isIncreasing;
    private BigDecimal leftXLimit;
    private BigDecimal rightXLimit;
    private BigDecimal middlePoint;

    public int scaneInCalculations = 3;
    
    public QudraticFunction (double leftXLimit, double rightXLimit, boolean isIncreasing) {
        if (leftXLimit > rightXLimit) throw new IllegalArgumentException("left limit > right limit");
        this.isIncreasing = isIncreasing;
        this.leftXLimit = BigDecimal.valueOf(leftXLimit);
        this.rightXLimit = BigDecimal.valueOf(rightXLimit);

        this.middlePoint = this.leftXLimit.add(this.rightXLimit)
                    .divide(BigDecimal.valueOf(2), scaneInCalculations, RoundingMode.HALF_EVEN);
    }

    public double calculateY(double xValue) {
        BigDecimal x = BigDecimal.valueOf(xValue);
        if (x.compareTo(rightXLimit) == 1 | x.compareTo(leftXLimit) == -1) return 0;

        if (x.compareTo(middlePoint) == -1) 
            return calculatePreMiddlePoint(x);
        else 
            return calculateAfterMiddlePoint(x);
    }

    private double calculatePreMiddlePoint(BigDecimal x) {
        BigDecimal logicValue = isIncreasing ? BigDecimal.ZERO : BigDecimal.ONE;

        BigDecimal numerator = x.subtract(leftXLimit).pow(2);
        numerator = BigDecimal.valueOf(2).multiply(numerator);
        BigDecimal denominator = rightXLimit.subtract(leftXLimit).pow(2);

        BigDecimal y = numerator.divide(denominator, scaneInCalculations, RoundingMode.HALF_EVEN);
        y = logicValue.subtract(y);
        return Math.abs(y.doubleValue());
    }

    private double calculateAfterMiddlePoint(BigDecimal x) {
        BigDecimal logicValue = isIncreasing ? BigDecimal.ONE : BigDecimal.ZERO;

        BigDecimal numerator = rightXLimit.subtract(x).pow(2);
        numerator = BigDecimal.valueOf(2).multiply(numerator);
        BigDecimal denominator = rightXLimit.subtract(leftXLimit).pow(2);
        
        BigDecimal y = numerator.divide(denominator, scaneInCalculations, RoundingMode.HALF_EVEN);
        y = logicValue.subtract(y);
        return Math.abs(y.doubleValue());
    }

    public double getLeftXLimit() {
        return this.leftXLimit.doubleValue();
    }

    public double getRightXLimit() {
        return this.rightXLimit.doubleValue();
    }
}
