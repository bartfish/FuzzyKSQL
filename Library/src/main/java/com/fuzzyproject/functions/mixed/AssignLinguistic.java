package com.fuzzyproject.functions.mixed;
import com.fuzzyproject.functions.MathFunctionSwitcher;
import java.util.*;
public class AssignLinguistic {
    public static Map.Entry<String, Double> assignLinguisticElement(String payload, Double searchedValue) {
        // "low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100"
        // split by / and assign linguistic values to functions
        String[] linguisticElements = payload.split(("/"));

        HashMap<String, String> matchedLinguistic = new HashMap<>();

        for (int i=0; i<linguisticElements.length; i++) {
            String[] matched = linguisticElements[i].split(":");
            matchedLinguistic.put(matched[0], matched[1]); // key: low, value: "TRIA;10;20;30
        }

        HashMap<String, Double> results = new HashMap<>();
        for (String i : matchedLinguistic.keySet()) {
            System.out.println("key: " + i + " value: " + matchedLinguistic.get(i));
            results.put(i, MathFunctionSwitcher.FunctionSwitchV(matchedLinguistic.get(i), searchedValue));
        }
        System.out.println(results);

        // get the linguistic one which has the biggest value which is bigger than 0
        System.out.println(Collections.max(results.values()));

        // check if all membership functions are unique
        // if they are, then return 'none'
        Set<Double> values = new HashSet<Double>(results.values());

        boolean isUnique = values.size() == 1;
        System.out.println("is unique = " + isUnique);

        if (isUnique) {
            return new AbstractMap.SimpleEntry<String, Double>("none", 0.0);
        }

        // if records are not the same then take the one with the highest value of the membership function

        // return the hashmap
        Map.Entry<String, Double> maxEntry = null;

        for (Map.Entry<String, Double> entry : results.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        // return the linguistic value of the given parameter
        return maxEntry;
    }
}
