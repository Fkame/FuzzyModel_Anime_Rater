package app.fuzzyModelCore.function;

import java.math.BigDecimal;

/**
 * Класс представляет собой простую линейную функцию - прямую линию.
 * <p>Уравнение прямой по двум точкам: (х - х1) / (x2 - x1) = (y - y1) / (y2 - y1);
 * <p>y(x) = [(x - x1) * (y1 - y1) / (x2 - x1)] + y1;
 * 
 * <p>Для подсчёта {@code calculateY} используется уравнение прямой по двум точкам, которое формируется из параметров, переданных в
 * конструктор.
 */
public class LinealFunction implements IFunction {
    
    private BigDecimal firstXPoint;
    private BigDecimal firstYPoint;
    private BigDecimal secondXPoint;
    private BigDecimal secondYPoint;

    /**
     * Принимает параметры для построения прямой. {@code firstXPoint} и {@code secondXPoint} должны быть границами прямой
     * по оси Х.
     * @param firstXPoint значение крайней левой точки по оси Х.
     * @param firstYPoint значение крайней левой точки по оси Y.    
     * @param secondXPoint значение крайней правой точки по оси X.
     * @param secondYPoint значение крайней правой точки по оси Y.
     */
    public LinealFunction(double firstXPoint, double firstYPoint, double secondXPoint, double secondYPoint) {
        if (firstXPoint > secondXPoint) throw new IllegalArgumentException("left limit > right limit");
        this.firstXPoint = BigDecimal.valueOf(firstXPoint);
        this.firstYPoint = BigDecimal.valueOf(firstYPoint);
        this.secondXPoint = BigDecimal.valueOf(secondXPoint);
        this.secondYPoint = BigDecimal.valueOf(secondYPoint);
    }
    
    public double calculateY (double xValue) {
        BigDecimal x = BigDecimal.valueOf(xValue);
        if (x.compareTo(secondXPoint) == 1 | x.compareTo(firstXPoint) == -1) return 0;
        
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
