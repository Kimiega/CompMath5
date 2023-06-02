package linal;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class LinalUtils {
    public final static double MAGIC_LOW_VALUE = 0.0000000000001;
    public static double[][] finiteDifferences(Dot[] dots) {
        double[][] matrix = new double[dots.length][dots.length];
        for (int i = 0; i < dots.length; ++i) {
            matrix[i][0] = dots[i].y();
        }
        for (int i = 1; i < dots.length; ++i) {
            for (int j = 0; j < dots.length - i; ++j) {
                matrix[j][i] = matrix[j+1][i-1] - matrix[j][i-1];
            }
        }

        return matrix;
    }
    public static BigInteger getFactorial(int f) {
        if (f < 2) {
            return BigInteger.valueOf(1);
        }
        else {
            return IntStream.rangeClosed(2, f).mapToObj(BigInteger::valueOf).reduce(BigInteger::multiply).get();
        }
    }
}
