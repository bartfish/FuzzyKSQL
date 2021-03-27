package com.fuzzyproject.fuzzyudfs.assignToLing;

import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

@UdfDescription(name = "ASSIGN_TO_LING", description = "check if value fits specific linguistic one")
public class AssignToLing {

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public static String AssignToLing(
            @UdfParameter(value = "expression", description = "exemplary expression: TRIA;LEFT;MIDDLE;RIGHT") final String expression,
            @UdfParameter(value = "seachedValue", description = "searchedValue") final Double searchedValue,
            @UdfParameter(value = "verificationCoefficient", description = "verificationCoefficient 0-1") final Double verificationCoefficient
    ) {
        return LinguisticDef.defineLinguisticRanges(expression, searchedValue, verificationCoefficient);
    }

}