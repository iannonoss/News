package com.democom.news.config.jobConfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
@EnableScheduling
public class BatchJobScheduler {

    @Autowired //gestore dell'avvio del job  JobL-->Job-->step
    private JobLauncher jobLauncher;

    @Autowired
    Job job;


    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    @Scheduled(fixedDelay = 5000, initialDelay = 15000)
    public void scheduleByFixedRate() throws Exception {
        System.out.println("Batch job starting");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
        jobLauncher.run(job, jobParameters);
        System.out.println("Batch job executed successfully\n");
    }
}