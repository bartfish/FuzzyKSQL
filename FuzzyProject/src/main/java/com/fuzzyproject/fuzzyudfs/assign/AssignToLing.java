package com.fuzzyproject.fuzzyudfs.assign;

import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

import java.util.Map;

@UdfDescription(name = "ASSIGN_TO_LING", description = "assigns value to linguistic value or returns none if nothing fits")
public class AssignToLing {

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public static String AssignToLing(
            @UdfParameter(value = "expression", description = "exemplary expression: TRIA;LEFT;MIDDLE;RIGHT") final String expression,
            @UdfParameter(value = "seachedValue", description = "searchedValue") final Double searchedValue,
            @UdfParameter(value = "verificationCoefficient", description = "verificationCoefficient 0-1") final Double verificationCoefficient
    ) {
        Map.Entry<String, Double> returnedEntry = LinguisticDef.defineLinguisticRanges(expression, searchedValue);
        if (returnedEntry.getValue() < verificationCoefficient) {
            return "none";
        }

        return returnedEntry.getKey();

    }

}