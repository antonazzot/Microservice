package resourceservice.logger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import resourceservice.logger.influxdb.InfluxDBWriter;

import java.util.stream.LongStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogGenerator {

    private final InfluxDBWriter influxDBWriter;

    public void generate(String info, String loginf) {
        log.info("Start generating logs");
        log.info(info+"={}", loginf);
        try {
            influxDBWriter.writeLog(info, loginf);
        }
        catch (Exception e) {
            log.error("loger eror={} ", e.getMessage());
        }

    }
}