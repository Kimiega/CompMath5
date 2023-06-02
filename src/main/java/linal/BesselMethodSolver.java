package linal;

import java.util.function.Function;

public class BesselMethodSolver extends InterpolationSolver {
    @Override
    public String nameSolver() {
        return "Bessel Method";
    }

    @Override
    public Equation calcInterpolation(Dot[] dots) {
        if (dots.length % 2 != 0)
            return null;
        double h = Math.abs(dots[0].x() - dots[1].x());
        for (int i = 0; i < dots.length-1; ++i) {
            if (Math.abs(Math.abs(dots[i].x() - dots[i+1].x()) - h) > LinalUtils.MAGIC_LOW_VALUE)
                return null;
        }
        return new Equation() {
            @Override
            public Function<Double, Double> equation() {
                return calcPolynomial(dots);
            }

            @Override
            public String equationToString() {
                return "Bessel Polynomial";
            }
        };
    }

    @Override
    protected Function<Double, Double> calcPolynomial(Dot[] dots) {
        double[][] finiteDifferences = LinalUtils.finiteDifferences(dots);
        double h = Math.abs(dots[0].x() - dots[1].x());
        return (Double x) -> {
            int lT = 0;
            int rT = 0;
            int iterFD = dots.length/2 - 1;
            double t = (x - dots[dots.length/2 - 1].x())/h;
            double sum = (finiteDifferences[iterFD][0] + finiteDifferences[iterFD + 1][0])/2 + (t - 0.5) * finiteDifferences[iterFD][1];

            for (int i = 2; i < dots.length; ++i) {
                double multiply = 1;
                if (i % 2 == 0) {
                    iterFD--;
                    lT--;
                    for (int j = lT; j <=rT; ++j) {
                        multiply *= (t + j);
                    }
                    multiply *= (finiteDifferences[iterFD][i] + finiteDifferences[iterFD + 1][i])/2;
                    multiply /= LinalUtils.getFactorial(i).doubleValue();
                }
                else {
                    for (int j = lT; j <=rT; ++j) {
                        multiply *= (t + j);
                    }
                    multiply *= (t-0.5);
                    multiply *= finiteDifferences[iterFD][i];
                    multiply /= LinalUtils.getFactorial(i).doubleValue();
                    rT++;
                }

                sum += multiply;
            }
            return sum;
        };
    }
}
