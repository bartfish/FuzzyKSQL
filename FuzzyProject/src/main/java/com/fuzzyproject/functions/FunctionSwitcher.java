package com.fuzzyproject.functions;

import java.util.ArrayList;
import java.util.List;

public class FunctionSwitcher {

    // BASED_PAYLOADS:

    // "TRIA;LEFT;RIGHT;MIDDLE,VALUE;FUZZY_PARAM"
    // "TRIA:0.5;12;4,xValue"

    // "GAUS;MIDDLE;K_PARAM;VALUE;FUZZY_PARAM"
    // "GAUS;0.5;12;4,xValue"

    // "TR_F;LEFT;LEFT_TOP;RIGHT_TOP;RIGHT;VALUE;FUZZY_PARAM"
    // "TR_F;0.5;12;4,xValue"

    // "TR_L;LEFT_TOP;LEFT;VALUE;FUZZY_PARAM"
    // "TR_L;0.5;12;4"

    // "TR_R;RIGHT;RIGHT_TOP;VALUE;FUZZY_PARAM"
    // "TR_R;0.5;3;4"

    // AT THE END ADDED THE FUZZY_PARAMETER - VALUE FROM 0 TO 1


    // CREATING MULTIPLE AREAS (LINGUISTIC ONES)
    // "{LOW;T;0;10;30;40;}/{NORMAL;T;45;55;60;70;}/{HIGH;T;65;75;90;95};(50);



    public static double FunctionSwitch(String definedPayload) {

        // SPLIT THE WHOLE PAYLOAD INTO THE ARRAY BY ;
        String data = definedPayload;
        String[] elements = data.split((";"));

        System.out.println(elements.length);
        
        List<Double> arrOfVAlues = new ArrayList<>();

        if (elements[0] != "MIXE") {
            // all elements except the first one should be doubles
            for (int i=1; i<elements.length; i++) {
                arrOfVAlues.add(Double.parseDouble(elements[i]));
            }
        }

        switch(elements[0]) {
            case "TRIA":
            {
                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2) + " " + arrOfVAlues.get(3));
                return Triangular.TriangularFunction(arrOfVAlues.get(0), arrOfVAlues.get(1), arrOfVAlues.get(2), arrOfVAlues.get(3));
            }

            case "GAUS":
            {
                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2));
                return Gauss.GaussianFunction(arrOfVAlues.get(0), arrOfVAlues.get(1), arrOfVAlues.get(2));
            }

            case "TR_F":
            {
                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2) + " " + arrOfVAlues.get(3) + " " + arrOfVAlues.get(4));
                return Trapezoidal.TrapezoidalFunction(arrOfVAlues.get(0), arrOfVAlues.get(1), arrOfVAlues.get(2), arrOfVAlues.get(3), arrOfVAlues.get(4));
            }

            case "TR_L":
            {
                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2));
                return Trapezoidal.TrapezoidalLeft(arrOfVAlues.get(0),arrOfVAlues.get(1),arrOfVAlues.get(2));
            }

            case "TR_R":
            {
                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2));
                return Trapezoidal.TrapezoidalRight(arrOfVAlues.get(0),arrOfVAlues.get(1),arrOfVAlues.get(2));
            }

            case "MIXE":
            {
                return 0;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + elements[0]);
        }

        // CONVERT ALL ARGUMENTS (EXCEPT THE FIRST ONE) TO DOUBLES

        // TAKE THE FIRST ELEMENT OF THE ARRAY
            // COMPARE AND DEFINE THE FUNCTION
            // RUN THE SPECIFIED FUNCTION
    }


    public static double FunctionSwitchV(String definedPayload, Double searchedValue) {

        // SPLIT THE WHOLE PAYLOAD INTO THE ARRAY BY ;
        String data = definedPayload;
        String[] elements = data.split((";"));

        System.out.println(elements.length);

        List<Double> arrOfVAlues = new ArrayList<>();

        if (elements[0] != "MIXE") {
            // all elements except the first one should be doubles
            for (int i=1; i<elements.length; i++) {
                arrOfVAlues.add(Double.parseDouble(elements[i]));
            }
        }

        switch(elements[0]) {
            case "TRIA":
            {
//                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2) + " " + arrOfVAlues.get(3));
                return Triangular.TriangularFunction(arrOfVAlues.get(0), arrOfVAlues.get(1), arrOfVAlues.get(2), searchedValue);
            }

            case "GAUS":
            {
//                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2));
                return Gauss.GaussianFunction(arrOfVAlues.get(0), arrOfVAlues.get(1), searchedValue);
            }

            case "TR_F":
            {
//                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2) + " " + arrOfVAlues.get(3) + " " + arrOfVAlues.get(4));
                return Trapezoidal.TrapezoidalFunction(arrOfVAlues.get(0), arrOfVAlues.get(1), arrOfVAlues.get(2), arrOfVAlues.get(3), searchedValue);
            }

            case "TR_L":
            {
//                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2));
                return Trapezoidal.TrapezoidalLeft(arrOfVAlues.get(0),arrOfVAlues.get(1), searchedValue);
            }

            case "TR_R":
            {
//                System.out.println(arrOfVAlues.get(0) + " " + arrOfVAlues.get(1) + " " + arrOfVAlues.get(2));
                return Trapezoidal.TrapezoidalRight(arrOfVAlues.get(0),arrOfVAlues.get(1), searchedValue);
            }

            case "MIXE":
            {
                return 0;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + elements[0]);
        }

        // CONVERT ALL ARGUMENTS (EXCEPT THE FIRST ONE) TO DOUBLES

        // TAKE THE FIRST ELEMENT OF THE ARRAY
        // COMPARE AND DEFINE THE FUNCTION
        // RUN THE SPECIFIED FUNCTION
    }
}
