package com.api.simapi.MandelbrotJava;

/**
 * 
 * @author Catree
 *
 */
public class MandelbrotJava {
	 // return number of iterations to check if c = a + ib is in MandelbrotJava set
	/**
	 * 
	 * @see http://introcs.cs.princeton.edu/java/32class/MandelbrotJava.java.html
	 */
    public static int mandelbrot(Complex z0, int max) {
        Complex z = z0;
        for (int t = 0; t < max; t++) {
            if (z.abs() > 2.0) return t;
            z = z.times(z).plus(z0);
        }
        return max;
    }
}