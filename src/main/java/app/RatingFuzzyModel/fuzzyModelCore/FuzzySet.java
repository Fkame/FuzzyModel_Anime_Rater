package app.RatingFuzzyModel.fuzzyModelCore;

import java.util.ArrayList;
import java.util.List;

import app.RatingFuzzyModel.fuzzyModelCore.function.IFunction;

public class FuzzySet {
    private List<IFunction> functions;

    private double minX;
    private double maxX;

    private String fuzzySetName;

    public void setFuzzySetName(String name) { this.fuzzySetName = name; }
    public String getFuzzySetName() { return this.fuzzySetName; }

    public FuzzySet() { 
        functions = new ArrayList<IFunction>();
    }

    public FuzzySet(String name) { 
        functions = new ArrayList<IFunction>();
        this.fuzzySetName = name;
    }

    public void add(IFunction func) {
        if (functions.size() == 0) {
            this.minX = func.getLeftXLimit();
            this.maxX = func.getRightXLimit();
        }
        if (func.getLeftXLimit() < this.minX) this.minX = func.getLeftXLimit();
        if (func.getRightXLimit() > this.maxX) this.maxX = func.getRightXLimit();
        this.functions.add(func);
    }

    public void clear() {
        this.functions.clear();
    }

    public double calculateY(double xValue) {
        if (xValue > this.maxX | xValue < this.minX) return 0;
        for (int i = 0; i < functions.size(); i++) {
            IFunction f = functions.get(i);
            if (xValue > f.getRightXLimit() | xValue < f.getLeftXLimit()) continue;
            return f.calculateY(xValue);
        }
        return 0;
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMaxX() {
        return this.maxX;
    }

}
