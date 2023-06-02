package linal;

import graph.DrawerFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuperMegaSolver {
    public static Thread thread = null;
    public static Equation fromWindowEquation = null;
    public static String solveAllProblems(Double[][] dotsRaw, double target) {
        int n = dotsRaw.length;
        var iter = Arrays.stream(dotsRaw).sorted((Double[] x, Double[] y) -> x[0] < y[0] ? -1 : 1).iterator();
        Dot[] dots = new Dot[n];
        int i = 0;
        while (iter.hasNext()) {
            Double[] dot = iter.next();
            dots[i] = new Dot(dot[0], dot[1]);
            i++;
        }
        return solveAllProblems(dots, target);
    }

    public static String solveAllProblems(Dot[] dots, double target) {
        StringBuilder sb = new StringBuilder();
        int n = dots.length;
        double xmin = target;
        double xmax = target;
        double ymin = dots[0].y();
        double ymax = dots[0].y();
        for (var dot : dots) {
            if (xmin > dot.x())
                xmin = dot.x();
            if (xmax < dot.x())
                xmax = dot.x();
            if (ymin > dot.y())
                ymin = dot.y();
            if (ymax < dot.y())
                ymax = dot.y();
        }
        if (xmin == xmax) {
            xmin-=5;
            xmax+=5;
        }
        if (ymin == ymax) {
            ymin -=5;
            ymax +=5;
        }
        double[][] matrix = LinalUtils.finiteDifferences(dots);
        sb.append(String.format("%7s %7s %7s %7s ","i", "xi", "yi", "d1yi"));
        for (int i = 2; i <n; ++i)
            sb.append(String.format("%7s ", String.format("d%dyi",i)));
        sb.append("\n");
        for (int i = 0; i < n; ++i) {
            sb.append(String.format("%7d %7.4f ", i, dots[i].x()));
            for (int j = 0; j < n - i; ++j) {
                sb.append(String.format("%7.4f ", matrix[i][j]));
            }
           sb.append("\n");
        }
        sb.append("\n");
        InterpolationSolver[] solvers = {
                new LagrangeMethodSolver(), new GaussLeftMethodSolver(), new GaussRightMethodSolver(),
                new StirlingMethodSolver(), new BesselMethodSolver()
        };
        List<Equation> equations = new ArrayList<>();
        List<Dot> targetDots = new ArrayList<>();
        for (var solver : solvers) {
           Equation equation = solver.calcInterpolation(dots);
           if (equation == null) {
               sb.append(solver.nameSolver()).append(" is unavailable\n\n");
               continue;
           }
           equations.add(equation);
           Dot dot = new Dot(target, equation.equation().apply(target));
            sb.append(solver.nameSolver()).append(String.format(": P(%f) = %f\n\n", dot.x(), dot.y()));
           targetDots.add(dot);
           ymin = Math.min(ymin, dot.y());
           ymax = Math.max(ymax, dot.y());
        }
        double finalXmin = xmin;
        double finalXmax = xmax;
        double finalYmin = ymin;
        double finalYmax = ymax;
        thread = new Thread(() -> {
            DrawerFunctions drawer = new DrawerFunctions(finalXmin, finalXmax, finalYmin, finalYmax);
            drawer.printDots(dots);
            drawer.printTargetDots(targetDots);
            for (var equation1 : equations)
                drawer.drawFunction(equation1);
            if (fromWindowEquation != null) {
                drawer.drawFunction(fromWindowEquation);
            }
            fromWindowEquation = null;
        });
        thread.start();
        return sb.toString();
    }
}
