package com.xnumall.cloud.springbatch.task;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SimpleJobConfig {
    @Resource
    private ZookeeperRegistryCenter regCenter;
    @Resource
    private JobEventConfiguration jobEventConfig;
    @Value("${banbu.elasticJob.employee.cron}")
    private String cron;
    @Autowired
    private BatchSimpleJob batchSimpleJob;

    @Bean(initMethod = "init")
    public JobScheduler employeeReportJobScheduler() {
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder("employeeReportJob", cron, 1)
                .shardingItemParameters("0=Beijing,1=Shanghai,2=Guangzhou") // 分片参数
                .failover(true)     // 失效转移
                .misfire(false)     // 错过重新执行
                .build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, BatchSimpleJob.class.getCanonicalName());
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).reconcileIntervalMinutes(10).build();
        return new SpringJobScheduler(batchSimpleJob, regCenter, simpleJobRootConfig, jobEventConfig);
    }
}
