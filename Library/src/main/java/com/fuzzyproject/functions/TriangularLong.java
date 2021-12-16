package com.fuzzyproject.functions;

public class TriangularLong {

    // "TF;LEFT;RIGHT;MIDDLE,VALUE"
    // "TF:0.5;12;4,xValue"
    public static double TriangularFunction(Long left, Long middle, Long right, Long xValue) {

        if (xValue > left && xValue <= middle) {
            double nominator = xValue - left;
            double denominator = middle - left;

            return nominator/denominator;
        }

        if (xValue > middle && xValue < right) {
            double nominator = right - xValue;
            double denominator = right - middle;

            return nominator/denominator;
        }

        return 0;
    }
}