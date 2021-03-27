package com.fuzzyproject.fuzzyudfs.assign;

import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

@UdfDescription(name = "ASSIGN_TO_LING", description = "assigns value to linguistic value or returns none if nothing fits")
public class AssignToLing {

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public String AssignToLing(
            @UdfParameter(value = "expression", description = "exemplary expression: TRIA;LEFT;MIDDLE;RIGHT") final String expression,
            @UdfParameter(value = "seachedValue", description = "searchedValue") final Double searchedValue,
            @UdfParameter(value = "verificationCoefficient", description = "verificationCoefficient 0-1") final Double verificationCoefficient
    ) {
        return LinguisticDef.defineLinguisticRanges(expression, searchedValue, verificationCoefficient);
    }

}