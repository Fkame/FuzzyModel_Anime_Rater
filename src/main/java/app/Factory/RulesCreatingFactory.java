package app.Factory;

import java.util.ArrayList;
import java.util.List;

import app.RatingFuzzyModel.fuzzyModelCore.FuzzyRule;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;

public class RulesCreatingFactory {
    
    public static List<FuzzyRule> getRulesBase(List<FuzzySet>ratingVariable, List<FuzzySet>soundVariable, 
                                        List<FuzzySet>animationVariable, List<FuzzySet>storyVariable, 
                                        List<FuzzySet>charactersVariable) 
    {
        List<FuzzyRule> rules = new ArrayList<>();
       /* List<FuzzySet> conditionsTerms = new ArrayList<>();
        FuzzyRule rule = null;

        conditionsTerms.add()
        rule = new FuzzyRule(conditionTerms, conclusionTerm)
*/
        return rules;
    }
}
