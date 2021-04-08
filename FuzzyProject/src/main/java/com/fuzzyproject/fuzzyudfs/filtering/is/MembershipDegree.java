package com.fuzzyproject.fuzzyudfs.filtering.is;

import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

import java.util.Map;

@UdfDescription(name = "GET_MEMBERSHIP_DEGREE", description = "check if value fits specific linguistic one")
public class MembershipDegree {

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public static Double MembershipDegreeGetter(
            @UdfParameter(value = "expression", description = "exemplary expression: TRIA;LEFT;MIDDLE;RIGHT") final String expression,
            @UdfParameter(value = "seachedValue", description = "searchedValue") final Double searchedValue
    ) {
        Map.Entry<String, Double> returnedEntry = LinguisticDef.defineLinguisticRanges(expression, searchedValue);

        if (returnedEntry == null) {
            return 0.0;
        }
        return returnedEntry.getValue();
    }

}