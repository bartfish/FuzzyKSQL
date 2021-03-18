package com.fuzzyproject.functions;

public class FunctionSwitcher {

    // BASED_PAYLOADS:

    // "TRIA;LEFT;RIGHT;MIDDLE,VALUE;FUZZY_PARAM"
    // "TRIA:0.5;12;4,xValue"

    // "GAUS;MIDDLE;K_PARAM;VALUE;FUZZY_PARAM"
    // "GAUS;0.5;12;4,xValue"

    // "TR_F;LEFT;LEFT_TOP;RIGHT_TOP;RIGHT;VALUE;FUZZY_PARAM"
    // "TR_F;0.5;12;4,xValue"

    // "TR_L;LEFT_TOP;LEFT;VALUE;FUZZY_PARAM"
    // "TR_l;0.5;12;4"

    // "TR_R;RIGHT;RIGHT_TOP;VALUE;FUZZY_PARAM"
    // "TR_R;0.5;3;4"

    // AT THE END ADDED THE FUZZY_PARAMETER - VALUE FROM 0 TO 1


    // CREATING MULTIPLE AREAS (LINGUISTIC ONES)
    // "{LOW;T;0;10;30;40;}/{NORMAL;T;45;55;60;70;}/{HIGH;T;65;75;90;95};(50);



    public double FunctionSwitch(String definedPayload) {

        // SPLIT THE WHOLE PAYLOAD INTO THE ARRAY BY ;
        // CONVERT ALL ARGUMENTS (EXCEPT THE FIRST ONE) TO DOUBLES

        // TAKE THE FIRST ELEMENT OF THE ARRAY
            // COMPARE AND DEFINE THE FUNCTION
            // RUN THE SPECIFIED FUNCTION

        return 0;
    }
}
