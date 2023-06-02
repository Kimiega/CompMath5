package linal;

import java.util.function.Function;

public class GaussLeftMethodSolver extends InterpolationSolver {
    @Override
    public Equation calcInterpolation(Dot[] dots) {
        if (dots.length % 2 != 1)
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
                return "Gauss Polynomial Left";
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
            double t = (x - dots[dots.length/2].x())/h;
            double sum = dots[dots.length/2].y() + t * finiteDifferences[iterFD][1];
            for (int i = 2; i < dots.length; ++i) {
                double multiply = 1;
                if (i % 2 != 0) {
                    lT--;
                    iterFD--;
                }
                else rT++;
                for (int j = lT; j <=rT; ++j) {
                    multiply *= (t + j);
                }
                multiply *= finiteDifferences[iterFD][i];
                multiply /= LinalUtils.getFactorial(i).doubleValue();
                sum += multiply;
            }
            return sum;
        };
    }

    @Override
    public String nameSolver() {
        return "Gauss Left Method";
    }
}
