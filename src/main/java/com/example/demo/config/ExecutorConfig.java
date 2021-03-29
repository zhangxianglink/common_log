package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorConfig {

    @Bean("demoAsyncServer")
    public Executor demoAsyncServer(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(core);//设置核心线程数
        executor.setMaxPoolSize(core*2 + 1);//设置最大线程数
        executor.setKeepAliveSeconds(3);//除核心线程外的线程存活时间
        executor.setQueueCapacity(40);//如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setThreadNamePrefix("thread-execute");//线程名称前缀
/*      ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。 默认策略

        ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。 

        ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程） 

        ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务 
        原文链接：https://blog.csdn.net/xunileida/article/details/83956897*/
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//设置拒绝策略
        return executor;
    }
}
