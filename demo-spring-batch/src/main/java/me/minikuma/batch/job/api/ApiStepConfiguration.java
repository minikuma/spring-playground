package me.minikuma.batch.job.api;

import lombok.RequiredArgsConstructor;
import me.minikuma.batch.classfier.ProcessorClassifier;
import me.minikuma.batch.domain.ApiRequestDto;
import me.minikuma.batch.domain.Product;
import me.minikuma.batch.domain.ProductDto;
import me.minikuma.batch.partitioner.ProductPartitioner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ApiStepConfiguration {

    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory sqlSessionFactory;
    static final int CHUNK_SIZE = 10;

    @Bean
    public Step apiMasterStep() {
        return stepBuilderFactory.get("apiMasterStep")
                .partitioner(apiSlaveStep().getName(), partitioner())
                .step(apiSlaveStep())
                .gridSize(3)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(6);
        executor.setThreadNamePrefix("api-thread-");
        return executor;
    }

    @Bean
    public Step apiSlaveStep() {
        return stepBuilderFactory.get("apiSlaveStep")
                .<ProductDto, ProductDto>chunk(CHUNK_SIZE)
                .reader(itemReader(null))
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ProductPartitioner partitioner() {
        ProductPartitioner productPartitioner = new ProductPartitioner();
        productPartitioner.setSqlSessionFactory(sqlSessionFactory);
        return productPartitioner;
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader<ProductDto> itemReader(@Value("#{stepExecutionContext[product]}") ProductDto productDto) {
        // query parameters
        Map<String, Object> param = new HashMap<>();
        param.put("type", productDto.getType());

        return new MyBatisPagingItemReaderBuilder<ProductDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("selectList")
                .pageSize(CHUNK_SIZE)
                .parameterValues(param)
                .build();
    }

    @Bean
    public ItemProcessor itemProcessor() {
        ClassifierCompositeItemProcessor<ProductDto, ApiRequestDto> processor = new ClassifierCompositeItemProcessor<>();
        ProcessorClassifier<ProductDto, ItemProcessor<?, ? extends ApiRequestDto>> classifier = new ProcessorClassifier<>();
        return processor;
    }

    @Bean
    public MyBatisBatchItemWriter<Product> itemWriter() {
        // TODO: 개발 필요
        return null;
    }
}
