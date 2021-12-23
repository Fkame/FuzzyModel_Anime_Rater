package app.RatingFuzzyModel.data;

import java.util.ArrayList;
import java.util.List;

public class FuzzyConclusionFigure {
    public List<Double> pointsX;
    public List<Double> pointsY;

    public FuzzyConclusionFigure() {
        pointsX = new ArrayList<>();
        pointsY = new ArrayList<>();
    }

    public void addX(double xValue) { this.pointsX.add(xValue); }
    public void addY(double yValue) { this.pointsY.add(yValue); }

    public double findXwhereMaxY() {
        if (pointsX == null | pointsY == null) throw new IllegalArgumentException("No points - no max");
        if (pointsX.size() == 0 | pointsY.size() == 0) throw new IllegalArgumentException("No points - no max");
        if (pointsX.size() != pointsY.size()) throw new IllegalArgumentException("pointsX.size() != pointsY.size()");

        double maxY = pointsY.get(0);
        double xValueOfY = pointsX.get(0);
        for (int i = 1; i < pointsX.size(); i++) {
            if (pointsY.get(i) > maxY) {
                maxY = pointsY.get(i);
                xValueOfY = pointsX.get(i);
            }
        }
        return xValueOfY;
    }
}
