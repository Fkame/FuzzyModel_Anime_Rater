package app.RatingFuzzyModel;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import app.Factory.FuzzySetsCreatingFactory;
import app.Factory.RulesCreatingFactory;
import app.RatingFuzzyModel.data.ActivatedTerm;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzyRule;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;

public class TestRatingFuzzyModel {
    
    List<FuzzyRule> rules;
    List<FuzzySet> sound;
    List<FuzzySet> animation;
    List<FuzzySet> story;
    List<FuzzySet> chars;
    List<FuzzySet> rating;

    public void prepareAllInputs() {
        this.sound = FuzzySetsCreatingFactory.getSoundVariable();
        this.animation = FuzzySetsCreatingFactory.getAnimationVariable();
        this.story = FuzzySetsCreatingFactory.getStoryVariable();
        this.chars = FuzzySetsCreatingFactory.getCharactersVariable();
        this.rating = FuzzySetsCreatingFactory.getRatingVariable();
        this.rules = RulesCreatingFactory.getRulesBase(rating, sound, animation, story, chars);
    }

    @Test
    public void testRulesActivation() {
        List<Double> inputValues = Arrays.asList(6.0, 7.0, 5.0, 4.0);
        this.prepareAllInputs();

        RatingFuzzyModel model = new RatingFuzzyModel(rules, rating);

        String methodName = "calculateRulesActivationValues";
        Method method = null;
        try {   
            method = RatingFuzzyModel.class.getDeclaredMethod(methodName, List.class);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            System.out.println("In test method [" + methodName + "] not found!");
            System.out.println(e.getMessage());
            e.printStackTrace();
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        List<Double> results = null;
        try {
            results = (List<Double>)method.invoke(model, inputValues);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } 

        System.out.println(results.toString());
        for (int i = 0; i < results.size(); i++) {
            System.out.println("[" + (i + 1) + "]=" + results.get(i));
        }
    }

    @Test
    public void testActivateOutputTermsByRules() {
        this.prepareAllInputs();

        RatingFuzzyModel model = new RatingFuzzyModel(rules, rating);

        String methodName = "activateOutputTermsByRules";
        Method method = null;
        try {   
            method = RatingFuzzyModel.class.getDeclaredMethod(methodName, List.class, List.class);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            System.out.println("In test method [" + methodName + "] not found!");
            System.out.println(e.getMessage());
            e.printStackTrace();
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            assertTrue(false);
        }

        List<Double> rulesActivation = Arrays.asList(0.0, 0.0, 0.0, 0.007, 0.018, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
                                    0.049, 0.133, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.02, 0.054, 0.0, 0.0, 0.0, 0.0, 
                                    0.0, 0.0, 0.0, 0.147, 0.401, 0.0, 0.0, 0.0, 0.0
        );
        int expectedSize = 8;
        List<ActivatedTerm> results = null;
        model.minimumLimitForRuleActivation = BigDecimal.valueOf(0.0);
        try {
            results = (List<ActivatedTerm>)method.invoke(model, rules, rulesActivation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            assertTrue(false);
        }

        System.out.println("actual size of activated terms = " + results.size() + ", expected=" + expectedSize);
        assertTrue("Checking size on current data", results.size() == expectedSize);
    }
}
