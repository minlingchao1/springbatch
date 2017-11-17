package com.xnumall.cloud.springbatch.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.xnumall.cloud.springbatch.service.PersonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchSimpleJob implements SimpleJob {

    @Autowired
    private PersonServiceImpl personServiceImpl;

    @Override
    public void execute(ShardingContext shardingContext) {
        personServiceImpl.startBatch();
        log.info("开启SimpleJob");
    }
}
