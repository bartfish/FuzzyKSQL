package com.fuzzyproject.fuzzyudfs.filtering.operators;

import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

@UdfDescription(name = "FAnd", description = "check if value fits specific linguistic one")
public class FAnd {

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public Boolean Is(
            @UdfParameter(value = "expression", description = "exemplary expression: TRIA;LEFT;MIDDLE;RIGHT") final String expression,
            @UdfParameter(value = "seachedValue", description = "searchedValue") final Double searchedValue,
            @UdfParameter(value = "linguisticValue", description = "linguisticValue") final String linguisticValue
    ) {
        String value = LinguisticDef.defineLinguisticRanges(expression, searchedValue);
        return value == linguisticValue;
    }

}