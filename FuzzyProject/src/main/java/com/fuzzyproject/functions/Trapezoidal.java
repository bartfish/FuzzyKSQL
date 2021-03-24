package com.fuzzyproject.functions;

public class Trapezoidal {

    // "TR;LEFT;LEFT_TOP;RIGHT_TOP;RIGHT;VALUE"
    // "TR;0.5;12;4,xValue"

    // "TR_LEFT;LEFT_TOP;LEFT;VALUE"
    // "TR_LEFT;0.5;12;4"

    // "TR_RIGHT;RIGHT;RIGHT_TOP;VALUE"
    // "TR_RIGHT;0.5;3;4"

    public static double TrapezoidalFunction(Double left, Double leftTop, Double rightTop, Double right, Double xValue) {

        System.out.println("left " + left);
        System.out.println("leftTop " + leftTop);
        System.out.println("rightTop " + rightTop);
        System.out.println("right " + right);
        System.out.println("xValue " + xValue);

        if (xValue < left || xValue > right) {
            return 0;
        }

        if (xValue >= left && xValue <= leftTop) {
            double nominator = xValue - left;
            double denominator = leftTop - left;

            return nominator/denominator;
        }

        if (xValue >= leftTop && xValue <= rightTop) {
            return 1;
        }

        if (xValue >= rightTop && xValue <= right) {
            double nominator = right - xValue;
            double denominator = right - rightTop;

            return nominator/denominator;
        }

        return 0;

    }

    public static double TrapezoidalRight(Double rightTop, Double right, Double xValue) {

        if (xValue > right) {
            return 0;
        }
        if (xValue < rightTop) {
            return 1;
        }
        if (xValue >= rightTop && xValue <= right) {
            double nominator = right - xValue;
            double denominator = right - rightTop;

            return nominator/denominator;
        }

        return 0;
    }

    public static double TrapezoidalLeft(Double left, Double leftTop, Double xValue) {
        if (xValue < left) {
            return 0;
        }
        if (xValue > leftTop) {
            return 1;
        }
        if (xValue >= left && xValue <= leftTop) {
            double nominator = xValue - left;
            double denominator = leftTop - left;

            return nominator/denominator;
        }

        return 0;
    }
}