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

    /**
     * Если есть выраженный экстремум - находит его, если несколкько максимумов подряд = ровная поверхность - ищет её середину.
     * @return
     */
    public double findXwhereMaxY() {
        if (pointsX == null | pointsY == null) throw new IllegalArgumentException("No points - no max");
        if (pointsX.size() == 0 | pointsY.size() == 0) throw new IllegalArgumentException("No points - no max");
        if (pointsX.size() != pointsY.size()) throw new IllegalArgumentException("pointsX.size() != pointsY.size()");

        double maxY = pointsY.get(0);
        double xValueOfY = pointsX.get(0);
        List<Double> sameMaximusOnX = new ArrayList<>();
        for (int i = 1; i < pointsX.size(); i++) {
            if (pointsY.get(i) > maxY) {
                maxY = pointsY.get(i);
                xValueOfY = pointsX.get(i);
                sameMaximusOnX.clear();
            }
            if (pointsY.get(i) == maxY) {
                sameMaximusOnX.add(pointsX.get(i));
            }
        }
        double rezOfSame = sameMaximusOnX.size() == 0 ? 0 : sameMaximusOnX.stream().mapToDouble(Double::doubleValue).sum();
        if (rezOfSame != 0) return (xValueOfY + rezOfSame) / (sameMaximusOnX.size() + 1);
        return xValueOfY;
    }
}
