package com.fuzzyproject.fuzzyudfs.filtering.Triangular;

import com.fuzzyproject.functions.Triangular;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

@UdfDescription(name = "AROUND_TRIANGULAR", description = "checks with configured triangular function")
public class AroundTriangular {

   @Udf(description = "Check whether value fits triangular function defined in arguments")
    public Double AroundTriangular(
           @UdfParameter(value = "FUNCTION_DEFINED", description = "type CONST (const for taking configured triangular) OR LET (with let pass 3 additional parameters)") final String functionDefined,
           @UdfParameter(value = "VALUE", description = "the second value") final Double searchedValue,
           @UdfParameter(value = "LEFT", description = "Left value") final Double left,
           @UdfParameter(value = "RIGHT", description = "Right value") final Double right,
           @UdfParameter(value = "MIDDLE", description = "Middle point value") final Double middle
           ) {

        if (functionDefined == "LET") {
            return Triangular.TriangularFunction(left, middle, right, searchedValue);
        }
       return Triangular.TriangularFunction(left, middle, right, searchedValue); // DEFINED CONFIGURED TRIANGULAR LATER

    }

}