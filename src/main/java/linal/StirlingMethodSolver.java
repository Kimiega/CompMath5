package linal;

import java.util.function.Function;

public class StirlingMethodSolver extends InterpolationSolver {
    GaussLeftMethodSolver gaussLeftMethodSolver = new GaussLeftMethodSolver();
    GaussRightMethodSolver gaussRightMethodSolver = new GaussRightMethodSolver();
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
                return "Stirling Polynomial";
            }
        };
    }

    @Override
    protected Function<Double, Double> calcPolynomial(Dot[] dots) {
        return (Double x) -> {
            var leftGauss = gaussLeftMethodSolver.calcPolynomial(dots);
            var rightGauss = gaussRightMethodSolver.calcPolynomial(dots);
            return (leftGauss.apply(x) + rightGauss.apply(x))/2;
        };
    }

    @Override
    public String nameSolver() {
        return "Stirling Method";
    }
}
