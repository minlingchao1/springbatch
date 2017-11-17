package com.xnumall.cloud.springbatch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Gimgoog on 2017/7/4.
 */
@Slf4j
@Service
public class PersonServiceImpl {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("importUserJob")
    Job importUserJob;

    public void startBatch() {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("date", new Date().toString());
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        try {
            jobLauncher.run(importUserJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("PersonServiceImpl start!");
    }

}
