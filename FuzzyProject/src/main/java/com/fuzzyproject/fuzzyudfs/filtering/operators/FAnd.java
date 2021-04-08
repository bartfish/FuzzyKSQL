package com.fuzzyproject.fuzzyudfs.filtering.operators;

import com.fuzzyproject.functions.FunctionIdentifier;
import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@UdfDescription(name = "FUZZY_AND", description = "check if value fits specific linguistic one")
public class FAnd {

    @Udf(description = "Overriden AND operator, max of ANDs is 6 and at least 2 arguments are required")
    public static Boolean FAnd(@UdfParameter(value = "boolean elements", description = "array of elements") final Boolean... elements) {

        for (Boolean element : elements) {
            if (!element) {
                return false;
            }
        }

//        System.out.println(FunctionIdentifier.IdentifyFunction(condition1));
//        System.out.println(FunctionIdentifier.IdentifyFunction(condition2));

//        String value = LinguisticDef.defineLinguisticRanges(expression, searchedValue);
//        return value == linguisticValue;
        return true;
    }

}