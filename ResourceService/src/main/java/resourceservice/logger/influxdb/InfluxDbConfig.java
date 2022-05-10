package resourceservice.logger.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class InfluxDbConfig {
    @Value("${spring.influx.url}")
    String url;
    @Value("${spring.influx.user}")
    String user;
    @Value("${spring.influx.password}")
    String password;
    @Value("${spring.influx.token}")
    String token;
    @Value("${spring.influx.org}")
    String org;
    @Value("${spring.influx.bucket}")
    String bucket;
    @Value("${spring.influx.database}")
    String database;

    @Bean
    public InfluxDB influxDB () {
        InfluxDB influxDB = InfluxDBFactory.connect(url, user, password);
        try {
            influxDB.setDatabase(database)
                    .enableBatch(100,2000, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            influxDB.setRetentionPolicy("autogen");
        }
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        return influxDB;
    }
//        influxDB.query(new Query("CREATE DATABASE my_log"));
//        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
//        return influxDB;

//        influxDB.createRetentionPolicy(
//                "defaultPolicy", "baeldung", "30d", 1, true);


    @Bean
    public InfluxDBClient influxDBClient () {
       return InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
//        return InfluxDBClientFactory.create(url, token.toCharArray());
    }

}
