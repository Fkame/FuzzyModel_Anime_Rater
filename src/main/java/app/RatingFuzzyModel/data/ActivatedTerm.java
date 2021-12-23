package app.RatingFuzzyModel.data;

import java.math.BigDecimal;
import java.util.List;

import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;

public class ActivatedTerm implements Comparable<ActivatedTerm> {
    public FuzzySet originalTerm;
    public List<Double> pointsOnX;
    public List<Double> newPointsOnY;

    @Override
    public int compareTo(ActivatedTerm toCompare) {
        double max1 = newPointsOnY.stream().max(Double::compareTo).get();
        double max2 = toCompare.newPointsOnY.stream().max(Double::compareTo).get();
        BigDecimal max1Value = BigDecimal.valueOf(max1);
        BigDecimal max2Value = BigDecimal.valueOf(max2);
        if (max1Value.compareTo(max2Value) == -1) return -1;
        if (max1Value.compareTo(max2Value) == 0) return 0;
        return -1;
    }

    public String getTermName() {
        return originalTerm.getFuzzySetName();
    }
}
