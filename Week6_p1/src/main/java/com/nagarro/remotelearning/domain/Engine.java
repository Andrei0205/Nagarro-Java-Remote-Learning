package com.nagarro.remotelearning.domain;

public class Engine {
    
    private final EngineArchitecture engineArchitecture;
    private final double displacement;
    private final int horsePower;

    public Engine(EngineArchitecture engineArchitecture, double displacement, int horsePower) {
        this.engineArchitecture = engineArchitecture;
        this.displacement = displacement;
        this.horsePower = horsePower;
    }

    public EngineArchitecture getEngineArchitecture() {
        return engineArchitecture;
    }

    public double getDisplacement() {
        return displacement;
    }

    public int getHorsePower() {
        return horsePower;
    }
}
