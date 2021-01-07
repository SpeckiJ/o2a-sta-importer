package org.n52.sta.awiingestor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.n52.jackson.datatype.jts.JtsModule;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.time.Duration;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@SpringBootApplication
public class AwiIngestorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwiIngestorApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        //TODO: afterburner
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        mapper.registerModule(new JtsModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(O2AHarvesterJob.class)
            .storeDurably()
            .withIdentity("o2aHarvesterJobDetail")
            .withDescription("Invocation O2A Harvester")
            .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
            .withIdentity("Qrtz_Trigger")
            .withDescription("Main Trigger")
            .withSchedule(simpleSchedule().repeatForever().withIntervalInHours(100))
            .build();
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig conf = RateLimiterConfig.custom()
            .limitRefreshPeriod(Duration.ofSeconds(2))
            .limitForPeriod(3)
            .timeoutDuration(Duration.ofMinutes(1))
            .build();

        return new RateLimiterRegistry.Builder()
            .addRateLimiterConfig(APIConfig.DATA_API_RATELIMIT_NAME, conf)
            .addRateLimiterConfig(APIConfig.SENSORS_API_RATELIMIT_NAME, conf)
            .build();
    }
}
