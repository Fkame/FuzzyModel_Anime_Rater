package app.fuzzyModelCore.function;

import java.math.BigDecimal;

/**
 * Класс представляет собой простую линейную функцию - прямую линию.
 * Уравнение прямой по двум точкам: (х - х1) / (x2 - x1) = (y - y1) / (y2 - y1);
 * y = [(x - x1) * (y1 - y1) / (x2 - x1)] + y1;
 * 
 * Для подсчёта {@code calculateY} используется уравнение прямой по двум точкам, которое формируется из параметров, переданных в
 * конструктор.
 */
public class LinealFunction implements IFunction {
    
    private BigDecimal firstXPoint;
    private BigDecimal firstYPoint;
    private BigDecimal secondXPoint;
    private BigDecimal secondYPoint;

    public LinealFunction(double firstXPoint, double firstYPoint, double secondXPoint, double secondYPoint) {
        this.firstXPoint = BigDecimal.valueOf(firstXPoint);
        this.firstYPoint = BigDecimal.valueOf(firstYPoint);
        this.secondXPoint = BigDecimal.valueOf(secondXPoint);
        this.secondYPoint = BigDecimal.valueOf(secondYPoint);
    }
    
    public double calculateY (double xValue) {
        BigDecimal x = BigDecimal.valueOf(xValue);
        BigDecimal xMinusX1 = x.subtract(firstXPoint);
        BigDecimal y2MinusY1 = secondYPoint.subtract(firstYPoint);
        BigDecimal x2MinusX1 = secondXPoint.subtract(firstXPoint);
        BigDecimal y = xMinusX1.multiply(y2MinusY1).divide(x2MinusX1).add(firstYPoint);
        return y.doubleValue();
    }

    public double getLeftXLimit() {
        return this.firstXPoint.doubleValue();
    }

    public double getRightXLimit() {
        return this.secondXPoint.doubleValue();
    }
}
