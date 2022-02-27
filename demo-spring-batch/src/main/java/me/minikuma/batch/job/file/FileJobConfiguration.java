package me.minikuma.batch.job.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minikuma.batch.chunk.processor.FileItemProcessor;
import me.minikuma.batch.domain.Product;
import me.minikuma.batch.domain.ProductEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
                .start(fileStep())
                .build();
    }

    @Bean
    public Step fileStep() {
        return stepBuilderFactory.get("fileStep")
                .<Product, ProductEntity>chunk(10)
                .reader(fileItemReader(null))
                .processor(fileItemProcessor())
                .writer(fileItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Product> fileItemReader(@Value("#{jobParameters['requestDate']}") String requestDate) {
        log.info("request date = {}", requestDate);
        return new FlatFileItemReaderBuilder<Product>()
                .name("flatFile")
                .resource(new ClassPathResource("product_" + requestDate + ".txt"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Product.class)
                .linesToSkip(1) // 첫번째 줄 스킵
                .delimited().delimiter(",")
                .names("id", "name", "price", "type")
                .build();
    }

    @Bean
    public ItemProcessor<Product, ProductEntity> fileItemProcessor() {
        return new FileItemProcessor();
    }

    // TODO: mybatis 로 변경 예정
    @Bean
    public ItemWriter<ProductEntity> fileItemWriter() {
        return new JpaItemWriterBuilder<ProductEntity>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }
}
