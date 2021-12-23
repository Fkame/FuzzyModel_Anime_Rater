package app.RatingFuzzyModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import app.RatingFuzzyModel.data.ActivatedTerm;
import app.RatingFuzzyModel.data.FuzzyConclusionFigure;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzyRule;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;

public class RatingFuzzyModel {

    private List<FuzzySet> outputVariable;
    private List<FuzzyRule> rules;

    public int scaleValue = 3;
    public BigDecimal minimumLimitForRuleActivation = BigDecimal.valueOf(0.01);

    /**
     * Шаг по оси X, который будет использоваться для акумуляции модифицированных заключений в некую итоговую фигуру для 
     * последующей дефаззификации по ней.
     */
    public BigDecimal stepOnXAxis = BigDecimal.valueOf(0.01);

    
    public RatingFuzzyModel (List<FuzzyRule> rules, List<FuzzySet> outputVariable) {
        this.outputVariable = outputVariable;
        this.rules = rules;
    }

    /**
     * Оценка аниме по значениям входных критериев.
     * @param inputScores значения входных критериев. Должны быть в том же порядке, в каком подавались лингвистические переменные, 
     * то есть по порядку элементы {@code inputScores} должны соответствовать {@code inputVariables}, которые передавались
     * в конструктор.
     * @return оценка аниме по входным критериям.
     */
    public ModelResult evaluteAnime(List<Double> inputScores) {
        // Шаг 1 - Степень выполнения правил
        List<Double> rulesActivation = calculateRulesActivationValues(inputScores);

        // Шаг 2 - Активированность термов выходной лингвистической переменной
        // В этом месте на выходные термы ставится ограничение в виде среза от правила.
        List<ActivatedTerm> activatedTerms = activateOutputTermsByRules(rules, rulesActivation);
        
        // Шаг 3 - Объединение модифицированных термов в 1 график для последующей дефаззификации.
        FuzzyConclusionFigure resultFigure = 
            this.getFinalFuzzySet(activatedTerms, this.getOutputVariableMin().doubleValue(), this.getOutputVariableMax().doubleValue());

        // Шаг 4 - Дефаззификация
        double exactValue = defuzzificate(resultFigure);

        // Шаг 5 - Формирование результатов
        ModelResult modelRez = new ModelResult();
        modelRez.inputs = List.copyOf(inputScores);
        modelRez.fuzzyScore = this.findOutputTermByXValue(resultFigure.findXwhereMaxY());
        modelRez.outputScore = exactValue;
        return modelRez; 
    }

    public String findOutputTermByXValue(double x) {
        double maxY = outputVariable.get(0).calculateY(x);
        String nameOfTerm = outputVariable.get(0).getFuzzySetName();
        for (int i = 1; i < outputVariable.size(); i++) {
            double localY = outputVariable.get(i).calculateY(x);
            if (localY > maxY) {
                maxY = localY;
                nameOfTerm = outputVariable.get(i).getFuzzySetName();
            }
        }
        return nameOfTerm;
    }

    public List<Double> calculateRulesActivationValues(List<Double> inputScores) {
        List<Double> degreesOfRulesActivation = new ArrayList<>();
        for (FuzzyRule rule : rules) {
            degreesOfRulesActivation.add(rule.getRuleActionValue(inputScores));
        }
        return degreesOfRulesActivation;
    }

    /**
     * Модифицирует исходные термы выходного множества, добавляя в них ограничение в виде срезов, представленных в виде значений
     * активизации активных правил.
     * @param rules список правил.
     * @param rulesActivation степени активизации правил. Последовательность должна совпадать с {@code rules}.
     * @return список из модифицированных каждым правилом термов выходных множеств. 
     * <p>То есть получается так, что каждое активное правило (степень активации которого должна быть выше 
     * {@code RatingFuzzyModel.minimumLimitForRuleActivation}) модифицирует какой-либо выходной терм, создавая 
     * горизонтальный срез по оси Y. Таким образом, количество элементов в выходном списке будет равняться количеству 
     * активизированных правил.
     */
    public List<ActivatedTerm> activateOutputTermsByRules(List<FuzzyRule> rules, List<Double>rulesActivation) {
        if (rules.size() != rulesActivation.size()) throw new IllegalArgumentException("rules.size() != rulesActivation.size()");

        List<ActivatedTerm> activatedTerms = new ArrayList<>();
        for (int i = 0; i < rules.size(); i++) {
            double activation = rulesActivation.get(i);
            BigDecimal correctActivation = BigDecimal.valueOf(activation).setScale(this.scaleValue, RoundingMode.HALF_EVEN);
            if (correctActivation.compareTo(minimumLimitForRuleActivation) == -1) continue;

            FuzzySet activatingTerm = rules.get(i).getConclusionTerm();
            ActivatedTerm activatedTerm = this.activateOutputTerm(activatingTerm, activation);
            activatedTerms.add(activatedTerm);
        }
        return activatedTerms;
    }

    public ActivatedTerm activateOutputTerm(FuzzySet term, double activationValue) {
        ActivatedTerm activatedTerm = new ActivatedTerm();
        activatedTerm.originalTerm = term;
        activatedTerm.functionSlice = activationValue;
        return activatedTerm;
    }

    public BigDecimal getOutputVariableMin() {
        double min = outputVariable.get(0).getMinX();
        for (int i = 1; i < outputVariable.size(); i++) {
            double localMin = outputVariable.get(i).getMinX();
            min = Math.min(min, localMin);
        }
        return BigDecimal.valueOf(min);
    }

    public BigDecimal getOutputVariableMax() {
        double max = outputVariable.get(0).getMaxX();
        for (int i = 1; i < outputVariable.size(); i++) {
            double localMax = outputVariable.get(i).getMaxX();
            max = Math.max(max, localMax);
        }
        return BigDecimal.valueOf(max);
    }

    public FuzzyConclusionFigure getFinalFuzzySet(List<ActivatedTerm> activatedTerms, double startX, double endX) {
        FuzzyConclusionFigure figure = new FuzzyConclusionFigure();
        BigDecimal x = BigDecimal.valueOf(startX);
        BigDecimal limit = BigDecimal.valueOf(endX);

        for (; x.compareTo(limit) == -1; x.add(stepOnXAxis)) {
            double maxY = this.findMaxYInActivatedTerms(activatedTerms, x.doubleValue());
            figure.addX(x.doubleValue());
            figure.addY(maxY);
        }

        double maxY = this.findMaxYInActivatedTerms(activatedTerms, limit.doubleValue());
        figure.addX(x.doubleValue());
        figure.addY(maxY);
        
        return figure;
    }

    public double findMaxYInActivatedTerms(List<ActivatedTerm> activatedTerms, double currentX) {
        double maxY = activatedTerms.get(0).calculateDegree(currentX);
        for (ActivatedTerm term : activatedTerms) {
            double yValue = term.calculateDegree(currentX);
            maxY = Math.max(maxY, yValue);
        }
        return maxY;
    }

    /**
     * Дефаззификация по дискретному методу центроида.
     * @param figure акумуляция модифицированных нечётких термов выходной лингвистической переменной.
     * @return чёткое число - ответ.
     */
    public double defuzzificate(FuzzyConclusionFigure figure) {
        BigDecimal numerator = BigDecimal.ZERO;
        for (int i = 0; i < figure.pointsX.size(); i++) {
            BigDecimal xValue = BigDecimal.valueOf(figure.pointsX.get(i));
            BigDecimal yValue = BigDecimal.valueOf(figure.pointsY.get(i));
            numerator.add(xValue.multiply(yValue));
        }
        
        BigDecimal denuminator = BigDecimal.ZERO;
        for (int i = 0; i < figure.pointsX.size(); i++) {
            BigDecimal yValue = BigDecimal.valueOf(figure.pointsY.get(i));
            denuminator.add(yValue);
        }

        BigDecimal crispNumber = numerator.divide(denuminator, scaleValue, RoundingMode.HALF_EVEN);
        return crispNumber.doubleValue();
    }

}
