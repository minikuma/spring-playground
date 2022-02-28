package me.minikuma.batch.job.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minikuma.batch.chunk.processor.FileItemProcessor;
import me.minikuma.batch.domain.Product;
import me.minikuma.batch.domain.ProductDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory sqlSessionFactory;

    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
                .start(fileStep())
                .build();
    }

    @Bean
    public Step fileStep() {
        return stepBuilderFactory.get("fileStep")
                .<ProductDto, Product>chunk(10)
                .reader(fileItemReader(null))
                .processor(fileItemProcessor())
                .writer(fileItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ProductDto> fileItemReader(@Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info("request date = {}", requestDate);
        return new FlatFileItemReaderBuilder<ProductDto>()
                .name("flatFile")
                .resource(new ClassPathResource("product_" + requestDate + ".txt"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ProductDto.class)
                .linesToSkip(1) // 첫번째 줄 스킵
                .delimited().delimiter(",")
                .names("id", "name", "price", "type")
                .build();
    }

    @Bean
    public ItemProcessor<ProductDto, Product> fileItemProcessor() {
        return new FileItemProcessor();
    }

    @Bean
    public MyBatisBatchItemWriter<Product> fileItemWriter() {
        return new MyBatisBatchItemWriterBuilder<Product>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("insertProduct")
                .build();
    }
}
