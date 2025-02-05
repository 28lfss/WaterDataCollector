package org.luiz.sa.waterdatacollector;

import org.luiz.sa.waterdatacollector.model.WaterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WaterDataCollectorRepository extends JpaRepository<WaterData, Long> {
    List<WaterData> findByDeviceId(String deviceId);

    @Query(value = "SELECT * FROM water_data WHERE device_id = :deviceId AND timestamp > :timestamp", nativeQuery = true)
    List<WaterData> findDeviceIdLastHourReads(@Param("deviceId") String deviceId, @Param("timestamp") long timestamp);

    @Query(value = "SELECT * FROM water_data WHERE device_id = :deviceId ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    WaterData findLastReadByDeviceId(@Param("deviceId") String deviceId);

    @Query(value = "SELECT timestamp FROM water_data WHERE device_id = :deviceId AND timestamp > :timestamp", nativeQuery = true)
    List<Long> getTimestampValuesByDeviceId(@Param("deviceId") String deviceId, @Param("timestamp") long timestamp);

    @Query(value = "SELECT ph FROM water_data WHERE device_id = :deviceId AND timestamp > :timestamp", nativeQuery = true)
    List<Float> getPhSensorsValuesByDeviceId(@Param("deviceId") String deviceId, @Param("timestamp") long timestamp);

    @Query(value = "SELECT temperature FROM water_data WHERE device_id = :deviceId AND timestamp > :timestamp", nativeQuery = true)
    List<Float> getTemperatureSensorsValuesByDeviceId(@Param("deviceId") String deviceId, @Param("timestamp") long timestamp);

    @Query(value = "SELECT tds FROM water_data WHERE device_id = :deviceId AND timestamp > :timestamp", nativeQuery = true)
    List<Integer> getTdsSensorsValuesByDeviceId(@Param("deviceId") String deviceId, @Param("timestamp") long timestamp);
}
