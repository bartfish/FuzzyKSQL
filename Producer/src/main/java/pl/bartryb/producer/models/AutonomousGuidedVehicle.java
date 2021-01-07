package pl.bartryb.producer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class AutonomousGuidedVehicle {

    @JsonProperty
    public Double traction;

    @JsonProperty
    public double longtitute;

    @JsonProperty
    public double latitude;

    @JsonProperty
    public Double batteryPercentageLeft;

    @JsonProperty
    public String machineState;

    @JsonProperty
    public Double humidity;


}
