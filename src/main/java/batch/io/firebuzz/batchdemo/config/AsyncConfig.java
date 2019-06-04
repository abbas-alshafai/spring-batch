package batch.io.firebuzz.batchdemo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Data
@Configuration
@EnableConfigurationProperties
@ComponentScan({"batch.io.firebuzz.batchdemo"})
public class AsyncConfig implements AsyncConfigurer {

    private int coreSize;
    private int maxSize;
    private int queueCapacity;
    private String threadNamePrefix;

    @Autowired private Environment env;

    public Executor getAsyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(Integer.parseInt(env.getProperty("async.coreSize")));
        executor.setMaxPoolSize(Integer.parseInt(env.getProperty("async.maxSize")));
        executor.setQueueCapacity(Integer.parseInt(env.getProperty("async.queueCapacity")));
        executor.setThreadNamePrefix(env.getProperty("threadNamePrefix"));
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();

        return executor;
    }
}
