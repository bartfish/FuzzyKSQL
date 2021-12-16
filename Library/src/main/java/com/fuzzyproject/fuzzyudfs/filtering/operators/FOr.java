package com.fuzzyproject.fuzzyudfs.filtering.operators;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

@UdfDescription(name = "EXTENDED_OR", description = "check if value fits specific linguistic one")
public class FOr {

    @Udf(description = "Overriden OR operator - list of at least 2 booleans as parameters")
    public static Boolean FOr(@UdfParameter(value = "boolean elements", description = "array of elements") final Boolean... elements) {
        Boolean areAnyTrueOnes = false;
        for (Boolean element : elements) {
            if (element) {
                areAnyTrueOnes = true;
                break;
            }
        }
        return areAnyTrueOnes;
    }

}