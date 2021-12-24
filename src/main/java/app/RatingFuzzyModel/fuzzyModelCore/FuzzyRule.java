package app.RatingFuzzyModel.fuzzyModelCore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Агрегация правил по И производится с помощью PROD (умножение).
 */
public class FuzzyRule {
    
    private List<FuzzySet> conditionTerms;
    private FuzzySet conclusionTerm;
    public int scaleValue = 3;

    public FuzzyRule (List<FuzzySet> conditionTerms, FuzzySet conclusionTerm) { 
        this.conditionTerms = conditionTerms;
        this.conclusionTerm = conclusionTerm;
    }

    public double getRuleActionValue(List<Double> inputValues) {
        if (inputValues.size() != conditionTerms.size()) 
            throw new IllegalArgumentException("conditionTerms.size() != inputValues.size()");

        BigDecimal activationValue = BigDecimal.ONE;
        for (int i = 0; i < conditionTerms.size(); i++) {
            FuzzySet conditionTerm = conditionTerms.get(i);
            double inputForTerm = inputValues.get(i);
            BigDecimal tempValue = BigDecimal.valueOf(conditionTerm.calculateY(inputForTerm));
            activationValue.multiply(tempValue).setScale(scaleValue, RoundingMode.HALF_EVEN);
        }
        return activationValue.doubleValue();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IF");
        int counter = 1;
        for (FuzzySet term : conditionTerms) {
            builder.append(" (x" + counter + "=").append(term.getFuzzySetName()).append(") &");
            counter += 1;
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("THEN Y = ").append(conclusionTerm.getFuzzySetName());
        
        return builder.toString();
    }

    public FuzzySet getConclusionTerm() {
        return this.conclusionTerm;
    }

    public List<FuzzySet> getConditionTerms() {
        return this.conditionTerms;
    }

    
}
