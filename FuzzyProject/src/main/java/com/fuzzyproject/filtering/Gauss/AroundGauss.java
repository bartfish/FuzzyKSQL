package com.fuzzyproject.filtering.Gauss;

import com.fuzzyproject.functions.Gauss;
import com.fuzzyproject.functions.Triangular;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

@UdfDescription(name = "AROUND_GAUSS", description = "checks with configured gauss function")
public class AroundGauss {

    @Udf(description = "Check whether value fits gauss function defined in arguments")
    public Double AroundGauss(
            @UdfParameter(value = "FUNCTION_DEFINED", description = "type CONST (const for taking configured triangular) OR LET (with let pass 3 additional parameters)") final String functionDefined,
            @UdfParameter(value = "VALUE", description = "the second value") final Double searchedValue,
            @UdfParameter(value = "MIDDLE", description = "Middle point value") final Double middle,
            @UdfParameter(value = "K_PARAMETER", description = "kparameter value") final Double kParameter) {

        if (functionDefined == "LET") {
            return Gauss.GaussianFunction(middle, kParameter, searchedValue);
        }
        return Gauss.GaussianFunction(middle, kParameter, searchedValue);// DEFINED CONFIGURED GAUSS LATER

    }

}