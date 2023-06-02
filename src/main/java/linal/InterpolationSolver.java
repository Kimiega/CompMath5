package linal;

import java.util.function.Function;

public abstract class InterpolationSolver {

    public abstract String nameSolver();
    public abstract Equation calcInterpolation(Dot[] dots);
    protected abstract Function<Double, Double> calcPolynomial(Dot[] dots);
}
