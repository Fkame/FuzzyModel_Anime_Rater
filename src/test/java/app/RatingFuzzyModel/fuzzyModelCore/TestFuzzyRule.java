package app.RatingFuzzyModel.fuzzyModelCore;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import app.Factory.FuzzySetsCreatingFactory;
import app.Factory.RulesCreatingFactory;

public class TestFuzzyRule {
    
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
    public void testGetRuleActionValue() {
        List<Double> inputValues = Arrays.asList(6.0, 7.0, 5.0, 4.0);
        double[] answers = {0, 0.007, 0.133};
        this.prepareAllInputs();
        
        FuzzyRule rule = rules.get(0);
        System.out.println(rule.getRuleActionValue(inputValues));
        assertEquals(answers[0], rule.getRuleActionValue(inputValues), 3);

        rule = rules.get(3);
        System.out.println(rule.getRuleActionValue(inputValues));
        assertEquals(answers[1], rule.getRuleActionValue(inputValues), 3);

        rule = rules.get(13);
        System.out.println(rule.getRuleActionValue(inputValues));
        assertEquals(answers[2], rule.getRuleActionValue(inputValues), 3);
    }
}
