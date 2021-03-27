//package com.fuzzyproject.fuzzyudfs.filtering.operators;
//
//import com.fuzzyproject.functions.FunctionIdentifier;
//import com.fuzzyproject.functions.mixed.LinguisticDef;
//import io.confluent.ksql.function.udf.Udf;
//import io.confluent.ksql.function.udf.UdfParameter;
//import io.confluent.ksql.function.udf.UdfDescription;
//import org.jetbrains.annotations.Nullable;
//
//@UdfDescription(name = "FAnd", description = "check if value fits specific linguistic one")
//public class FAnd {
//
//    @Udf(description = "Overriden AND operator, max of ANDs is 6 and at least 2 arguments are required")
//    public static Boolean FAnd(
//            @UdfParameter(value = "condition1", description = "Condition1") final String condition1,
//            @UdfParameter(value = "condition2", description = "Condition2") final String condition2
//    ) {
//
//        System.out.println(FunctionIdentifier.IdentifyFunction(condition1));
//        System.out.println(FunctionIdentifier.IdentifyFunction(condition2));
//
////        String value = LinguisticDef.defineLinguisticRanges(expression, searchedValue);
////        return value == linguisticValue;
//        return true;
//    }
//
//}