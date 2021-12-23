package app.RatingFuzzyModel.data;

import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;

public class ActivatedTerm {
    public FuzzySet originalTerm;
    public Double functionSlice;

    /**
     * Подсчитывает значение оси ОУ (функции принадлежности) с учётом среза от правила. 
     * <p>Работает по правилу MIN: degree = min {{@code functionSlice}, origin_degree}. 
     * origin_degree - значение функции принадлежности в точке X.
     * @param xValue 
     * @return значение функции, ограниченной срезом правила
     */
    public double calculateDegree(double xValue) {
        double yOrigin = originalTerm.calculateY(xValue);
        return Math.min(yOrigin, functionSlice);
    }

    public String getTermName() {
        return originalTerm.getFuzzySetName();
    }
}
