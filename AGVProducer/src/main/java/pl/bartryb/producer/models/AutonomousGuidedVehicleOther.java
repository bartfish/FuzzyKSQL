package pl.bartryb.producer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AutonomousGuidedVehicleOther {

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

    @JsonProperty
    public Double weightValue;

    @JsonProperty
    public String weightUnit;

    @JsonProperty
    public Integer machineId;

    @JsonProperty
    public Double wheelsTemperature;
    

}
