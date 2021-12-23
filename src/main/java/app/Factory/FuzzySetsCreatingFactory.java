package app.Factory;

import java.util.ArrayList;
import java.util.List;

import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;
import app.RatingFuzzyModel.fuzzyModelCore.function.GaussianFunction;
import app.RatingFuzzyModel.fuzzyModelCore.function.LinealFunction;
import app.RatingFuzzyModel.fuzzyModelCore.function.QudraticFunction;
import app.RatingFuzzyModel.fuzzyModelCore.function.SigmoidFunction;

public class FuzzySetsCreatingFactory {
    
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

        soundVariable.add(badSound);
        soundVariable.add(goodSound);

        return soundVariable;
    }

    public static List<FuzzySet> getAnimationVariable() {
        List<FuzzySet> animationVariable = new ArrayList<>();

        FuzzySet badPicture = new FuzzySet("Bad");
        badPicture.add(new SigmoidFunction(0, 10, -1, 5));

        FuzzySet goodPicture = new FuzzySet("Good");
        goodPicture.add(new SigmoidFunction(0, 10, 1, 5));

        animationVariable.add(badPicture);
        animationVariable.add(goodPicture);

        return animationVariable;
    }

    public static List<FuzzySet> getStoryVariable() {
        List<FuzzySet> storyVariable = new ArrayList<>();

        FuzzySet badStory = new FuzzySet("Bad");
        badStory.add(new SigmoidFunction(0, 4, -6, 3));

        FuzzySet normalStory = new FuzzySet("Normal");
        normalStory.add(new QudraticFunction(2, 4, true));
        normalStory.add(new LinealFunction(4, 1, 6, 1));
        normalStory.add(new QudraticFunction(6, 9, false));

        FuzzySet goodStory = new FuzzySet("Good");
        goodStory.add(new SigmoidFunction(6, 10, 4, 7.5));

        storyVariable.add(badStory);
        storyVariable.add(normalStory);
        storyVariable.add(goodStory);
        
        return storyVariable;
    }

    public static List<FuzzySet> getCharactersVariable() {
        List<FuzzySet> charactersVariable = new ArrayList<>();

        FuzzySet badChars = new FuzzySet("Bad");
        badChars.add(new LinealFunction(0, 1, 2, 1));
        badChars.add(new QudraticFunction(2, 5, false));

        FuzzySet normalChars = new FuzzySet("Normal");
        normalChars.add(new GaussianFunction(2, 8, 5, 1));

        FuzzySet goodChars = new FuzzySet("Good");
        goodChars.add(new QudraticFunction(5, 8, true));
        goodChars.add(new LinealFunction(8, 1, 10, 1));

        charactersVariable.add(badChars);
        charactersVariable.add(normalChars);
        charactersVariable.add(goodChars);

        return charactersVariable;
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
