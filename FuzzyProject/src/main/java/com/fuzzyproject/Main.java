package com.fuzzyproject;

import com.fuzzyproject.functions.FunctionSwitcher;

public class Main {

    public static void main(String[] args) {

        // define strings which will be given in the ksql as arguments
        String TRIANGULAR_SIMPLE = "TRIA;0.5;12;16;12"; // "TRIA;LEFT;RIGHT;MIDDLE,VALUE;FUZZY_PARAM"
        String GAUSS_SIMPLE = "GAUS;0.5;12;4;3"; // "GAUS;MIDDLE;K_PARAM;VALUE;FUZZY_PARAM"
        String TRAPEZOIDAL_SIMPLE = "TR_F;0.5;8;10;12;9"; // "TR_F;LEFT;LEFT_TOP;RIGHT_TOP;RIGHT;VALUE;FUZZY_PARAM"
        String TRAPEZOIDAL_LEFT = "TR_L;0.5;12;29;"; // "TR_L;LEFT_TOP;LEFT;VALUE;FUZZY_PARAM"
        String TRAPEZOIDAL_RIGHT = "TR_R;0.5;3;2.9;"; // "TR_R;RIGHT;RIGHT_TOP;VALUE;FUZZY_PARAM"

//        String MULTI_AREAS_WITH_LINGUISTICS = "MIXE;{LOW;TR_F;0;10;30;40;}/{NORMAL;TR_F;45;55;60;70;}/{HIGH;TR_F;65;75;90;95};(50);";

//        System.out.println(FunctionSwitcher.FunctionSwitch(TRIANGULAR_SIMPLE)); // works
//        System.out.println(FunctionSwitcher.FunctionSwitch(GAUSS_SIMPLE)); // works
//        System.out.println(FunctionSwitcher.FunctionSwitch(TRAPEZOIDAL_SIMPLE)); // works
//        System.out.println(FunctionSwitcher.FunctionSwitch(TRAPEZOIDAL_LEFT)); // works
//        System.out.println(FunctionSwitcher.FunctionSwitch(TRAPEZOIDAL_RIGHT));  // works
//        FunctionSwitcher.FunctionSwitch(MULTI_AREAS_WITH_LINGUISTICS);



    }

}
