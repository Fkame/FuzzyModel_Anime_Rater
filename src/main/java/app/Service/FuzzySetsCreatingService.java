package app.Service;

import java.util.ArrayList;
import java.util.List;

import app.fuzzyModelCore.FuzzySet;
import app.fuzzyModelCore.function.LinealFunction;

public class FuzzySetsCreatingService {
    
    public static List<FuzzySet> getSoundVariable() {
        List<FuzzySet> soundVariable = new ArrayList<>();
        
        FuzzySet badSound = new FuzzySet();
        badSound.setFuzzySetName("Bad");
        badSound.add(new LinealFunction(0, 1, 3, 1));
        badSound.add(new LinealFunction(3, 1, 7, 0));

        FuzzySet goodSound = new FuzzySet();
        goodSound.setFuzzySetName("Good");
        goodSound.add(new LinealFunction(3, 0, 7, 1));
        goodSound.add(new LinealFunction(7, 1, 10, 1));

        soundVariable = new ArrayList<FuzzySet>();
        soundVariable.add(badSound);
        soundVariable.add(goodSound);

        return soundVariable;
    }

    public static List<FuzzySet> getAnimationVariable() {
        return null;
    }

    public static List<FuzzySet> getStoryVariable() {
        return null;
    }

    public static List<FuzzySet> getCharactersVariable() {
        return null;
    }

    public static List<FuzzySet> getRatingVariable() {      
        FuzzySet veryBad = new FuzzySet();
        FuzzySet bad = new FuzzySet();
        FuzzySet normal = new FuzzySet();
        FuzzySet good = new FuzzySet();
        FuzzySet veryGood = new FuzzySet();

        veryBad.setFuzzySetName("Very bad");
        veryBad.add(new LinealFunction(0, 1, 1, 1));
        veryBad.add(new LinealFunction(1, 1, 3, 0));

        bad.setFuzzySetName("Bad");
        bad.add(new LinealFunction(1, 0, 3, 1));
        bad.add(new LinealFunction(3, 1, 5, 0));

        bad.add(new LinealFunction(1, 0, 3, 1));
        bad.add(new LinealFunction(3, 1, 5, 0));

        normal.setFuzzySetName("Normal");
        normal.add(new LinealFunction(3, 0, 5, 1));
        normal.add(new LinealFunction(5, 1, 7, 0));

        good.setFuzzySetName("Good");
        good.add(new LinealFunction(5, 0, 7, 1));
        good.add(new LinealFunction(7, 1, 9, 0));

        veryGood.setFuzzySetName("Very good");
        veryGood.add(new LinealFunction(7, 0, 9, 1));
        veryGood.add(new LinealFunction(9, 1, 10, 1));

        List<FuzzySet> ratingVariable = new ArrayList<>();
        ratingVariable.add(veryBad);
        ratingVariable.add(bad);
        ratingVariable.add(normal);
        ratingVariable.add(good);
        ratingVariable.add(veryGood);

        return ratingVariable;
    }
}
