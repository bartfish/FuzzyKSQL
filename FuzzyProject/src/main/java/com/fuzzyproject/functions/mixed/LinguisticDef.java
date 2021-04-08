package com.fuzzyproject.functions.mixed;

import com.fuzzyproject.functions.MathFunctionSwitcher;

import java.util.*;

public class LinguisticDef {

    // TODO: consider he linguistic value given
    public static Double getLinguisticWithValue(String payload, Double searchedValue, String linguisticValue) {

        // "low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100"
        // split by / and assign linguistic values to functions
            // key=linguistic_value, value=function_with_range
        String[] linguisticElements = payload.split(("/"));

        HashMap<String, String> matchedLinguistic = new HashMap<>();

        for (int i=0; i<linguisticElements.length; i++) {
//            System.out.println(linguisticElements[i]);
            String[] matched = linguisticElements[i].split(":");
            matchedLinguistic.put(matched[0], matched[1]); // key: low, value: "TRIA;10;20;30
        }

        HashMap<String, Double> results = new HashMap<>();
        for (String i : matchedLinguistic.keySet()) {
            if (i.equals(linguisticValue)) {
                System.out.println("key: " + i + " value: " + matchedLinguistic.get(i));
                results.put(i, MathFunctionSwitcher.FunctionSwitchV(matchedLinguistic.get(i), searchedValue));
                break;
            }
        }
        if (results.size() > 0) {
           return results.get(linguisticValue);
        }
        System.out.println();

        return 0.0;
    }
}
