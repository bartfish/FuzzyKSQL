package com.fuzzyproject.functions;

public class FunctionIdentifier {

    public static String IdentifyFunction(String callContent) {

        String elements[] = callContent.split(";");
        switch (elements[0]) {
            case "AROUND":
            case "AROUND_TRAPEZ":
            case "AROUND_TRAPEZ_L":
            case "AROUND_TRAPEZ_R":
            case "AROUND_TRIANGULAR":
            case "AROUND_GAUSS":
            case "Is":
                return elements[0];

            default:
                return "NOT_FOUND";
        }



    }

}
