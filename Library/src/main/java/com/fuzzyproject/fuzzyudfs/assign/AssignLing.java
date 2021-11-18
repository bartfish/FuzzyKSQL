package com.fuzzyproject.fuzzyudfs.assign;

import com.fuzzyproject.functions.mixed.AssignLinguistic;
import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

import java.util.Map;

@UdfDescription(name = "ASSIGN_LING", description = "assigns value to linguistic value or returns none if nothing fits")
public class AssignLing {

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public static String AssignLing(
            @UdfParameter(value = "expression", description = "exemplary expression: TRIA;LEFT;MIDDLE;RIGHT") final String expression,
            @UdfParameter(value = "seachedValue", description = "searchedValue") final Double searchedValue
    ) {
        Map.Entry<String, Double> returnedEntry = AssignLinguistic.assignLinguisticElement(expression, searchedValue);
        return returnedEntry.getKey();

    }

}