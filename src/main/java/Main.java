import graph.DrawerFunctions;
//import graph.InputWindow;
import graph.InputWindow;
import linal.*;


public class Main {

    public static void main(String... args) {
        InputWindow.init();
        Double[][] dotsRaw = new Double[][]{
                 //{-3d, -81d}, {-2d, -24d}, {-1d, -3d}, {0.1d, 0.003d}, {1.5d, 10.125d}, {4d, 192d},
                //{-1.5d, -3.25d},
               // {1.93d,6.203d}, {3.45d,9.282d}, {5d,11.248d}, {7.17d, 13.159d}, {16.5d,17.576d}, {24.8d,19.736d}, {40.5d,22.335d}
                //, {400.5d,220.335d}
        // {-1.5d, 3.25d - 10d}, {-1.1d,6.37d - 10d}, {-0.5d, 9.27 -10d}, {0d, 10d -10d}, {0.4d, 9.52d -10d}, {1.3d, 4.93d-10d}
                {0.1, 1.25}, {0.2, 2.38}, {0.3, 3.79}, {0.4, 5.44}, {0.5, 7.14}//, {0.6, 8.84}
        };
        int n = dotsRaw.length;
        Dot[] dots = new Dot[n];
//        for (int i = 0; i < n; ++i) {
//            dots[i] = new Dot(dotsRaw[i][0], dotsRaw[i][1]);
//        }
//        String ans = SuperMegaSolver.solveAllProblems(dotsRaw, 0.32);
//        System.out.println(ans);
    }
}
