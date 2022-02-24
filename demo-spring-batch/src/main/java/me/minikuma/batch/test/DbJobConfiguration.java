package me.minikuma.batch.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DbJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job dbJob() {
        return jobBuilderFactory.get("dbJob1")
                .start(dbStep1())
                .next(dbStep2())
                .build();
    }

    @Bean
    public Step dbStep1() {
        return stepBuilderFactory.get("dbStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        // job parameter access (contribution)
                        JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
                        jobParameters.getString("name");
                        jobParameters.getLong("seq");
                        jobParameters.getDouble("height");
                        jobParameters.getDate("date");

                        // job parameter access (chunkContext) -> map 활용 방식
                        Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();

                        // TODO: Do Something
                        System.out.println("=============================");
                        System.out.println(" >> DB Job Execute           ");
                        System.out.println("=============================");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build()
                ;
    }

    @Bean
    public Step dbStep2() {
        return stepBuilderFactory.get("dbStep2")
                .tasklet(new CustomTasklet())
                .build()
                ;
    }
}
