package com.fuzzyproject.functions.mixed;

import com.fuzzyproject.functions.FunctionSwitcher;

import java.util.*;

public class LinguisticDef {


    public static String defineLinguisticRanges(String payload, Double searchedValue) {

        // "lowTR_F;20;30;40;50/normal;TR_F;40;50;60;70;high;TR_F;50;80;90;100"

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
            System.out.println("key: " + i + " value: " + matchedLinguistic.get(i));
            results.put(i, FunctionSwitcher.FunctionSwitchV(matchedLinguistic.get(i), searchedValue));
        }

        System.out.println(results);

        // get the linguistic one which has the biggest value which is bigger than 0
        System.out.println(Collections.max(results.values()));

        // return the hashmap
        Map.Entry<String, Double> maxEntry = null;

        for (Map.Entry<String, Double> entry : results.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        System.out.println(maxEntry);

        // check the agreement coefficient for each function and verify which value is the closest

        // return the linguistic value of the given parameter

        return "normal";
    }
}
