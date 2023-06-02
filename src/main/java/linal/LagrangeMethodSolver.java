package linal;

import java.util.function.Function;

public class LagrangeMethodSolver extends InterpolationSolver {

    @Override
    public Equation calcInterpolation(Dot[] dots) {
        return new Equation() {
            @Override
            public Function<Double, Double> equation() {
                return calcPolynomial(dots);
            }

            @Override
            public String equationToString() {
                return "Lagrange Polynomial";
            }
        };
    }


    @Override
    public String nameSolver() {
        return "Lagrange Method";
    }

    @Override
    protected Function<Double, Double> calcPolynomial(Dot[] dots) {
        return (Double x) -> {
            double sum = 0;
            for (int i = 0; i < dots.length; ++i) {
                double multiply = 1;
                for (int j = 0; j < dots.length; ++j) {
                    if (i == j)
                        continue;
                    multiply *= (x - dots[j].x())/(dots[i].x()-dots[j].x());
                }
                multiply *= dots[i].y();
                sum += multiply;
            }
            return sum;
        };

    }
}
