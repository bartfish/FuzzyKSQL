package com.fuzzyproject.functions;

public class Gauss {

    // "GA;MIDDLE;K_PARAM;VALUE"
    // "GA;0.5;12;4,xValue"
    public Double GaussianFunction(Double middle, Double kParameter, Double xValue) {
        Double calculatedPower = -1 * (Math.pow((xValue - middle), 2))/(2 * Math.pow(kParameter, 2));
        return Math.exp(calculatedPower);
    }
}