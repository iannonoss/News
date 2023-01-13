package com.democom.news.config.jobConfig;

import com.democom.news.entity.Subscription;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReaderConfiguration itemReaderConfiguration;

    @Autowired
    private ItemWriterConfiguration itemWriterConfiguration;

    @Autowired
    private ItemProcessorConfig itemProcessorConfig;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Bean
    public Job createJob() {
        return jobBuilderFactory.get("first")
                .incrementer(new RunIdIncrementer())
                .flow(createStep()).end().build();
    }

    @Bean
    public Step createStep() {
        return stepBuilderFactory.get("step")
                .<Subscription, Subscription> chunk(1)
                .reader(itemReaderConfiguration)
                .processor(itemProcessorConfig)
                .writer(itemWriterConfiguration)
                .build();
    }


    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
        return factory.getObject();
    }

}
