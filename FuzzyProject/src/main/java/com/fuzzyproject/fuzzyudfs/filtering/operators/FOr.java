package com.fuzzyproject.fuzzyudfs.filtering.operators;

import com.fuzzyproject.functions.FunctionIdentifier;
import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@UdfDescription(name = "FUZZY_OR", description = "check if value fits specific linguistic one")
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