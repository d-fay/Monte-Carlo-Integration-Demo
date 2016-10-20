package com.alg;

public class TrapIntegrate {

    final public static double A = -2;
    final public static double B = 2;
    final public static int N = 100000;

    /**********************************************************************
     * Standard normal distribution density function.
     * Replace with any sufficiently smooth function.
     **********************************************************************/
    static double f(double x) {
        return Math.exp(- x * x / 2) / Math.sqrt(2 * Math.PI);
    }


    /**********************************************************************
     * Integrate f from a to b using the trapezoidal rule.
     * Increase N for more precision.
     **********************************************************************/
    static double trapIntegrate(double a, double b, int n) {
        double h = (b - a) / n;              // step size
        double sum = 0.5 * (f(a) + f(b));    // area
        for (int i = 1; i < n; i++) {
            double x = a + h * i;
            sum = sum + f(x);
        }
        return sum * h;
    }

    /**********************************************************************
     * MEAN OF VALUES AT RANDOM LOCATIONS:
     * Perform numerical integration using the Monte Carlo
     * method. The endpoints and the number of random points
     * to sample are read in.
     **********************************************************************/
    // computing the mean value of the function at random locations
    public static double monteCarloIntegrate(double a, double b, int n) {
        double x;
        double sum = 0;

        // compute mean value of function at random location within the interval
        for (int i = 0; i < n; i++)
        {
            x = Math.random() * (b - a) + a;
            sum += f(x);
        }
        double approx = (b - a) * sum/n;    // multiply mean value of function by interval's width
        return approx;
    }

    public static double monteCarloDartIntegration( double a, double b, int n) {

        // the min and max values are provided parameters (interval)
        double xMin = a;
        double xMax = b;

        // default values for proceeding min-max calculation
        double yMin = f(xMin);
        double yMax = yMin;
        double x;
        double y;

        // find the min and max for y values
        // n is number of steps
        for (int i = 0; i < n; i++) {
            x = xMin + (xMax - xMin) * (float)i/n;
            y = f(x);

            if ( y < yMin) { yMin = y; }
            if ( y > yMax) { yMax = y; }

        }

        // monte carlo
        double rectArea = (xMax - xMin) * (yMax - yMin);

        int count = 0;  // count darts that land under curve
        //int numDarts = 100000000;
        // n is number of darts
        for (int j = 0; j < n; j++) {
            x = xMin + (xMax - xMin) * Math.random();
            y = yMin + (yMax - yMin) * Math.random();

            if (f(x) > 0 && y > 0 && y <= f(x)) { count++; }    // count if random dart is within area of curve
            if (f(x) < 0 && y < 0 && y >= f(x)) { count++; }    // y values could be negative and within function
        }

        //double percentArea = (float)count/numDarts;            // percentage of darts within the 'area under the curve'
        double functionArea = rectArea * (double)count/n;   // area under the curve

        return functionArea;

    }



    public static void main(String[] args) {
        System.out.println("Trapezoidal Integration Method with: " + N + " subdivisions: " + trapIntegrate(A, B, N));
        System.out.println("Monte Carlo Integration Method using mean values: " + N + " samples: " + monteCarloIntegrate(A, B, N));
        System.out.println("Monte Carlo Integration Method using random darts: " + N + " darts: " + monteCarloDartIntegration(A, B, N));

    }

}