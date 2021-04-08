package com.fuzzyproject;

import com.fuzzyproject.functions.MathFunctionSwitcher;
import com.fuzzyproject.functions.mixed.LinguisticDef;
import com.fuzzyproject.fuzzyudfs.assign.AssignToLing;
import com.fuzzyproject.fuzzyudfs.filtering.is.IsLing;
import com.fuzzyproject.fuzzyudfs.filtering.operators.FAnd;

public class Main {

    public static void main(String[] args) {

        // define strings which will be given in the ksql as arguments
        String TRIANGULAR_SIMPLE = "TRIA;50;80;28;0.336899056516208"; // "TRIA;LEFT;RIGHT;MIDDLE,VALUE;FUZZY_PARAM"
        String GAUSS_SIMPLE = "GAUS;0.5;12;4;3"; // "GAUS;MIDDLE;K_PARAM;VALUE;FUZZY_PARAM"
        String TRAPEZOIDAL_SIMPLE = "TR_F;0.5;8;10;12;9"; // "TR_F;LEFT;LEFT_TOP;RIGHT_TOP;RIGHT;VALUE;FUZZY_PARAM"
        String TRAPEZOIDAL_LEFT = "TR_L;0.5;12;29;"; // "TR_L;LEFT_TOP;LEFT;VALUE;FUZZY_PARAM"
        String TRAPEZOIDAL_RIGHT = "TR_R;0.5;3;2.9;"; // "TR_R;RIGHT;RIGHT_TOP;VALUE;FUZZY_PARAM"

//        String MULTI_AREAS_WITH_LINGUISTICS = "MIXE;{LOW;TR_F;0;10;30;40;}/{NORMAL;TR_F;45;55;60;70;}/{HIGH;TR_F;65;75;90;95};(50);";

        // System.out.println(MathFunctionSwitcher.FunctionSwitch(TRIANGULAR_SIMPLE)); // works
//        System.out.println(MathFunctionSwitcher.FunctionSwitch(GAUSS_SIMPLE)); // works
//        System.out.println(MathFunctionSwitcher.FunctionSwitch(TRAPEZOIDAL_SIMPLE)); // works
//        System.out.println(FunctionSwitcher.FunctionSwitch(TRAPEZOIDAL_LEFT)); // works
//        System.out.println(FunctionSwitcher.FunctionSwitch(TRAPEZOIDAL_RIGHT));  // works
//        FunctionSwitcher.FunctionSwitch(MULTI_AREAS_WITH_LINGUISTICS);

//        System.out.println(FunctionSwitcher.FunctionSwitchV("TRIA;50;80;150", 79.5));
        // System.out.println(LinguisticDef.defineLinguisticRanges("low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100", 99.0, 0.6));
//        System.out.println(IsLing.Is("low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100", 90.0, "high", 0.7));
//        System.out.println(AssignToLing.AssignToLing("low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100", 60.0, 0.7));
        System.out.println(FAnd.FAnd(IsLing.VerifyIs("low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100", 90.0, "high", 0.7),
                IsLing.VerifyIs("low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100", 90.0, "low", 0.7)));


    }

}
