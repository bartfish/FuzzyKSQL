package com.fuzzyproject.functions;

public class Gauss {

    private static final double kparam = 1;
    private static final double mid = 1;

    // "GA;MIDDLE;K_PARAM;VALUE"
    // "GA;0.5;12;4,xValue"
    public static double GaussianFunction(Double middle, Double kParameter, Double xValue) {
        Double a = 1/(kParameter * Math.sqrt(2* Math.PI));
        Double calculatedPower = -1 * (Math.pow((xValue - middle), 2))/(2 * Math.pow(kParameter, 2));
        return a*Math.exp(calculatedPower);
    }

    public static double GaussianFunctionPredefined(Double xValue) {
        double kParameter = kparam;
        double middle = mid;
        Double a = 1/(kParameter * Math.sqrt(2* Math.PI));
        Double calculatedPower = -1 * (Math.pow((xValue - middle), 2))/(2 * Math.pow(kParameter, 2));
        return a*Math.exp(calculatedPower);
    }
}