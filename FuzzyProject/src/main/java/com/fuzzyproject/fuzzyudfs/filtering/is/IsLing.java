package com.fuzzyproject.fuzzyudfs.filtering.is;

import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

import java.util.Map;

@UdfDescription(name = "VERIFY_IS", description = "check if value fits specific linguistic one")
public class IsLing {

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public static Boolean VerifyIs(
            @UdfParameter(value = "expression", description = "exemplary expression: TRIA;LEFT;MIDDLE;RIGHT") final String expression,
            @UdfParameter(value = "seachedValue", description = "searchedValue") final Double searchedValue,
            @UdfParameter(value = "linguisticValue", description = "linguisticValue") final String linguisticValue,
            @UdfParameter(value = "verificationCoefficient", description = "verificationCoefficient 0-1") final Double verificationCoefficient
    ) {
        Double returnedMembershipDegree = LinguisticDef.getLinguisticWithValue(expression, searchedValue, linguisticValue);
        if (returnedMembershipDegree < verificationCoefficient) { // if the membership degree is too small then there is no point in verifying if the linguistic condition fits
            return false;
        }
//        return returnedEntry.getKey().equals(linguisticValue);
        return true;
    }


}