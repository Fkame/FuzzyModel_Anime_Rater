package app.RatingFuzzyModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.RatingFuzzyModel.data.ActivatedTerm;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzyRule;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;

public class RatingFuzzyModel {

    /**
     * FuzzySet = 1 терм (нечёткое множество) = несколько функций. 
     * <p>List<FuzzySet> = 1 лингвистическая переменная = несколько нечётких множеств.
     * <p>List<List<FuzzySet>> = список из нескольких лингвистических переменнных
     */
    private List<List<FuzzySet>> listOfFuzzyVariables;

    private List<FuzzySet> outputVariable;

    private List<FuzzyRule> rules;

    public int scaleValue = 3;
    public BigDecimal minimumLimitForRuleActivation = BigDecimal.valueOf(0.01);
    public BigDecimal stepOnXAxis = BigDecimal.valueOf(0.01);

    /**
     * 
     * @param rules список правил для нечёткого вывода.
     * @param outputVariable выходная лингвистическая переменная.
     * @param inputVariables входные лингвистические переменные. См. примечание.
     * <p>Примечание:
     * {@code FuzzySet} = 1 терм (нечёткое множество) = несколько функций.
     * {@code List<FuzzySet>} = 1 лингвистическая переменная = несколько нечётких множеств.
     * {@code List<List<FuzzySet>>} = список из нескольких лингвистических переменнных.
     */
    public RatingFuzzyModel (List<FuzzyRule> rules, List<FuzzySet> outputVariable, List<FuzzySet> ... inputVariables) 
    {
        this.listOfFuzzyVariables = Arrays.asList(inputVariables);
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
        if (inputScores.size() != listOfFuzzyVariables.size()) 
            throw new IllegalArgumentException("Кол-во входных значений != кол-ву входных переменных");

        /*
        // Шаг 1 - Фаззификация
        // Данная переменая - список степеней принадлежности входных значений всем термам соответствующей лингвистической переменной.
        List<List<Double>> listOfDegreesToTerms = new ArrayList<>();
        for (int i = 0; i < inputScores.size(); i++) {
            listOfDegreesToTerms.add(fuzzification(inputScores.get(i), listOfFuzzyVariables.get(i)));
        }
        */

        // Шаг 2 - Степень выполнения правил
        List<Double> rulesActivation = calculateRulesActivationValues(inputScores);

        // Шаг 3 - Активация термов выходной лингвистической переменной
        List<ActivatedTerm> activatedTerms = activateOutputTermsByRules(rules, rulesActivation);
        
        // Шаг 4 - Акумуляция заключений
        
        

        // Шаг 5 - Дефаззификация


        // Заполнение результата
        ModelResult result = new ModelResult();
        result.inputs = List.copyOf(inputScores);
        result.fuzzyScore = activatedTerms.stream().max(ActivatedTerm::compareTo).get().getTermName();

        return result;
    }

    /*
    public List<Double> fuzzification(double score, List<FuzzySet> terms) {
        List<Double> degrees = new ArrayList<>();
        for (FuzzySet term : terms) {
            degrees.add(term.calculateY(score));
        }
        return degrees;
    }
    */

    public List<Double> calculateRulesActivationValues(List<Double> inputScores) {
        List<Double> degreesOfRulesActivation = new ArrayList<>();
        for (FuzzyRule rule : rules) {
            degreesOfRulesActivation.add(rule.getRuleActionValue(inputScores));
        }
        return degreesOfRulesActivation;
    }

    public List<ActivatedTerm> activateOutputTermsByRules(List<FuzzyRule> rules, List<Double>rulesActivation) {
        if (rules.size() != rulesActivation.size()) throw new IllegalArgumentException("rules.size() != rulesActivation.size()");

        List<ActivatedTerm> activatedTerms = new ArrayList<>();
        for (int i = 0; i < rules.size(); i++) {
            double activation = rulesActivation.get(i);
            BigDecimal correctActivation = BigDecimal.valueOf(activation).setScale(this.scaleValue, RoundingMode.HALF_EVEN);
            if (correctActivation.compareTo(minimumLimitForRuleActivation) == -1) continue;

            FuzzySet activatingTerm = rules.get(i).getConclusionTerm();
            ActivatedTerm activatedTerm = this.activateOutputTerm(activatingTerm, activation, this.stepOnXAxis);
            activatedTerms.add(activatedTerm);
        }
        return activatedTerms;
    }

    private ActivatedTerm activateOutputTerm(FuzzySet term, double activationValue, BigDecimal step) {
        BigDecimal variableStartPoint = this.getOutputVariableMin();
        BigDecimal variableEndPoint = this.getOutputVariableMax();

        BigDecimal startPoint = BigDecimal.valueOf(term.getMinX());
        BigDecimal endPoint = BigDecimal.valueOf(term.getMaxX());

        List<Double> pointsX = new ArrayList<>();
        List<Double> pointsY = new ArrayList<>();

        BigDecimal x = variableStartPoint;
        for (; x.compareTo(startPoint) == -1; x.add(step)) {
            pointsX.add(x.doubleValue());
            pointsY.add(0.0);
        }

        for (; x.compareTo(endPoint) == -1; x.add(step)) {
            pointsX.add(x.doubleValue());
            double y = term.calculateY(x.doubleValue());
            pointsY.add(Math.min(activationValue, y));
        }
    
        for (; x.compareTo(variableEndPoint) == -1; x.add(step)) {
            pointsX.add(x.doubleValue());
            pointsY.add(0.0);
        }
        pointsX.add(variableEndPoint.doubleValue());
        pointsY.add(0.0);
        
        ActivatedTerm activatedTerm = new ActivatedTerm();
        activatedTerm.originalTerm = term;
        activatedTerm.pointsOnX = pointsX;
        activatedTerm.newPointsOnY = pointsY;
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

    public List<Double> finalFuzzySet(List<Double> xPoints, List<ActivatedTerm> terms) {
        
    }

}
