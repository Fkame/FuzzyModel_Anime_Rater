package app.fuzzyModelCore.function;

public interface IFunction {
    
    double calculateY(double xValue);

    double getLeftXLimit();
    double getRightXLimit();
}
