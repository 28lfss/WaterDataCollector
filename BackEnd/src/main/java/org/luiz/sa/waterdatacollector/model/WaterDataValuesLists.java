package org.luiz.sa.waterdatacollector.model;

import java.util.List;

public class WaterDataValuesLists {
    private List<Long> timestamp;
    private List<Float> ph;
    private List<Float> temperature;
    private List<Integer> tds;

    public List<Long> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<Long> timestamp) {
        this.timestamp = timestamp;
    }

    public List<Float> getPh() {
        return ph;
    }

    public void setPh(List<Float> ph) {
        this.ph = ph;
    }

    public List<Float> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Float> temperature) {
        this.temperature = temperature;
    }

    public List<Integer> getTds() {
        return tds;
    }

    public void setTds(List<Integer> tds) {
        this.tds = tds;
    }
}
