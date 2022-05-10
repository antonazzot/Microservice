package resourceservice.logger.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.WriteOptions;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import lombok.RequiredArgsConstructor;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class InfluxDBWriter {
    @Value("${spring.influx.org}")
    String org;
    @Value("${spring.influx.bucket}")
    String bucket;
    @Value("${spring.influx.database}")
    String database;
    private final InfluxDB influxDB;
    private final InfluxDBClient influxDBClient;

    public void writeLog (String info, String loginfo) {

        BatchPoints batchPoints = BatchPoints
                .database(database)
                .retentionPolicy("defaultPolicy")
                .build();

        Point point = Point
                .measurement("mem")
                .addField(info, loginfo);

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        influxDBClient.getWriteApi().writePoint(point);
        writeApi.writePoint(bucket, org, point);


    }
}
