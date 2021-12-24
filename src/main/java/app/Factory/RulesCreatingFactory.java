package app.Factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.RatingFuzzyModel.fuzzyModelCore.FuzzyRule;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;

public class RulesCreatingFactory {
    
    public static List<FuzzyRule> getRulesBase(List<FuzzySet>rating, List<FuzzySet>sound, 
                                        List<FuzzySet>animation, List<FuzzySet>story, 
                                        List<FuzzySet>chars) 
    {
        List<FuzzyRule> rules = new ArrayList<>();

        // Выделим термы в отдельные переменные для удобства.
        FuzzySet veryBad = rating.get(0);
        FuzzySet bad = rating.get(1);
        FuzzySet normal = rating.get(2);
        FuzzySet good = rating.get(3);
        FuzzySet veryGood = rating.get(4);

        // Запишем последовательность из заключений при последовательном полном переборе
        List<FuzzySet> conclTerms = Arrays.asList(
                veryBad,    veryBad,    bad,        veryBad,    veryBad,
                bad,        bad,        bad,        normal,     veryBad,
                bad,        normal,     normal,     normal,     normal,
                normal,     good,       good,       veryBad,    veryBad,
                bad,        bad,        normal,     good,       normal,
                good,       good,       normal,     normal,     normal,
                normal,     good,       veryGood,   normal,     veryGood,
                veryGood
        );
        
        // Для упрощения создания базы правил правила добавляются виде упорядоченного перебора термов. 
        // Единственное условие такого перебора - соблюсти порядок заключений для правил, для этого был создан список выше.
        int counter = 0;
        for (FuzzySet soundTerm : sound) {
            for (FuzzySet animaTerm : animation) {
                for (FuzzySet storyTerm : story) {
                    for (FuzzySet charsTerm : chars) {
                        List<FuzzySet> inputTerms = Arrays.asList(soundTerm, animaTerm, storyTerm, charsTerm);
                        rules.add(new FuzzyRule(inputTerms, conclTerms.get(counter)));
                        counter += 1;
                    }
                }
            }
        }
        return rules;
    }
}
