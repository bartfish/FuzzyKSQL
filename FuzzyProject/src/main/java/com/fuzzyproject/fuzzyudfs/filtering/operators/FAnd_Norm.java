package com.fuzzyproject.fuzzyudfs.filtering.operators;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

@UdfDescription(name = "FUZZY_AND", description = "check if value fits specific set of values")
public class FAnd_Norm {

    @Udf(description = "Overriden AND operator - list of at least 2 doubles as parameters")
    public static Double FAnd(@UdfParameter(value = "double elements", description = "array of elements") final Double... elements) {

        // using here the t-norm zadehowa
        // find the minimum of the given values and return it
        Double minElement = elements[0];
        for (Double element : elements) {
//            System.out.println(element);
            if (minElement > element) {
                minElement = element;
            }
        }
        return minElement;
    }

}