package org.luiz.sa.waterdatacollector.model;

public class WaterDataSimplified {
    private long timestamp;
    private float ph;
    private float temperature;
    private int tds;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getTds() {
        return tds;
    }

    public void setTds(int tds) {
        this.tds = tds;
    }
}
