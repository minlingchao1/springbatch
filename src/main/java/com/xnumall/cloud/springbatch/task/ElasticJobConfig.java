package com.xnumall.cloud.springbatch.task;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class ElasticJobConfig {

    @Resource
    private DataSource dataSource;

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${banbu.elasticJob.zookeeper.serverList}") final String serverList,
                                             @Value("${banbu.elasticJob.zookeeper.namespace}") final String namespace) {
        ZookeeperRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
        return regCenter;
    }

    @Bean
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(dataSource);
    }
}
