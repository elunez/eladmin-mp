package me.zhengjie.infra.thread;

import com.alibaba.ttl.TtlRunnable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 创建自定义的线程池
 * @author Zheng Jie
 * @description
 * @date 2023-06-08
 **/
@Configuration
public class CustomExecutorConfig {

    /**
     * 自定义线程池，用法 @Async
     * @return Executor
     */
    @Bean
    @Primary
    public Executor elAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(AsyncTaskProperties.corePoolSize);
        executor.setMaxPoolSize(AsyncTaskProperties.maxPoolSize);
        executor.setQueueCapacity(AsyncTaskProperties.queueCapacity);
        executor.setThreadNamePrefix("el-async-");
        executor.setKeepAliveSeconds(AsyncTaskProperties.keepAliveSeconds);
        // DiscardOldestPolicy，抛弃最早的任务，将新任务加入队列。
        // AbortPolicy，拒绝执行新任务，并抛出异常。
        // CallerRunsPolicy，交由调用者线程执行新任务，如果调用者线程已关闭，则抛弃任务。
        // DiscardPolicy，直接抛弃新任务。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 设置包装器
        executor.setTaskDecorator(TtlRunnable::get);
        executor.initialize();
        return executor;
    }

    /**
     * 自定义线程池，用法 @Async("otherAsync")
     * @return Executor
     */
    @Bean
    public Executor otherAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(15);
        executor.setQueueCapacity(50);
        executor.setKeepAliveSeconds(AsyncTaskProperties.keepAliveSeconds);
        executor.setThreadNamePrefix("el-task-");
        // DiscardOldestPolicy，抛弃最早的任务，将新任务加入队列。
        // AbortPolicy，拒绝执行新任务，并抛出异常。
        // CallerRunsPolicy，交由调用者线程执行新任务，如果调用者线程已关闭，则抛弃任务。
        // DiscardPolicy，直接抛弃新任务。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 设置包装器
        executor.setTaskDecorator(TtlRunnable::get);
        executor.initialize();
        return executor;
    }
}
