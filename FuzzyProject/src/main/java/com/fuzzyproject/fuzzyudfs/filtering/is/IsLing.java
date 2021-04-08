package com.fuzzyproject.fuzzyudfs.filtering.is;

import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

import java.util.Map;

@UdfDescription(name = "VERIFYIS", description = "check if value fits specific linguistic one")
public class IsLing {

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public static Boolean VerifyIs(
            @UdfParameter(value = "expression", description = "exemplary expression: TRIA;LEFT;MIDDLE;RIGHT") final String expression,
            @UdfParameter(value = "seachedValue", description = "searchedValue") final Double searchedValue,
            @UdfParameter(value = "linguisticValue", description = "linguisticValue") final String linguisticValue,
            @UdfParameter(value = "verificationCoefficient", description = "verificationCoefficient 0-1") final Double verificationCoefficient
    ) {
        Map.Entry<String, Double> returnedEntry = LinguisticDef.defineLinguisticRanges(expression, searchedValue);
        if (returnedEntry.getValue() < verificationCoefficient) { // if the membership degree is too small then there is no point in verifying if the linguistic condition fits
            return false;
        }
        return returnedEntry.getKey().equals(linguisticValue);
    }

}