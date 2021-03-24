package com.fuzzyproject.filtering;

import com.fuzzyproject.functions.FunctionSwitcher;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

@UdfDescription(name = "AROUND", description = "checks with respect to the assigned function")
public class Around {

    @Udf(description = "Check whether value fits triangular function defined in arguments")
    public Double AroundTriangular(
            @UdfParameter(value = "expression", description = "exemplary expression: TRIA;0.5;12;16;12") final String expression
    ) {
        return FunctionSwitcher.FunctionSwitch(expression);
    }

}