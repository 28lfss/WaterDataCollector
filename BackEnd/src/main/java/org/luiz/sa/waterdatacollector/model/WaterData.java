package org.luiz.sa.waterdatacollector.model;

import jakarta.persistence.*;

@Entity
@Table(name = "water_data")
public class WaterData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float ph;
    private int tds;
    private Float temperature;
    private String deviceId;
    private long timestamp;

    public WaterData () {}

    public Long getId() {
        return id;
    }

    public Float getPh() {
        return ph;
    }

    public void setPh(Float ph) {
        this.ph = ph;
    }

    public int getTds() {
        return tds;
    }

    public void setTds(int tds) {
        this.tds = tds;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        //this.timestamp = Instant.now().toEpochMilli();
    }

/*    public Map<String, Object> getReadings() {
        Map<String, Object> allData = new HashMap<>();
        allData.put("ID", getId());
        allData.put("Device ID", getDeviceId());
        allData.put("Timestamp", getTimestamp());
        allData.put("Temperature", getTemperature());
        allData.put("pH", getPh());
        allData.put("TDS", getTds());

        return allData;
    }
 */
}
