package batch.io.firebuzz.batchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@ComponentScan({"batch.io.firebuzz.batchdemo"})
@EnableConfigurationProperties
public class BatchConfig {

    @Autowired private JobBuilderFactory jobBuilder;
    @Autowired private StepBuilderFactory stepBuilder;

    @Bean
    public Job job(){
        return jobBuilder
                .get("JobName")
                .incrementer(new RunIdIncrementer()).preventRestart()
                .start(start())
                .build();
    }


    private Step start() {
        return stepBuilder.get("startStepName").tasklet((sc, cc)->{
            System.out.println("First step");
            return RepeatStatus.FINISHED;
        }).build();
    }
}
