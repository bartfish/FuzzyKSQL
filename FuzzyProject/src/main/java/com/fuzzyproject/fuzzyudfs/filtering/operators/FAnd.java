package com.fuzzyproject.fuzzyudfs.filtering.operators;

import com.fuzzyproject.functions.FunctionIdentifier;
import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@UdfDescription(name = "EXTENDED_AND", description = "check if value fits specific linguistic one")
public class FAnd {

    @Udf(description = "Overriden AND operator - list of at least 2 booleans as parameters")
    public static Boolean FAnd(@UdfParameter(value = "boolean elements", description = "array of elements") final Boolean... elements) {

        for (Boolean element : elements) {
            if (!element) {
                return false;
            }
        }

        return true;
    }

}