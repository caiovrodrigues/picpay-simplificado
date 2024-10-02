package com.desafio.picpay.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class TaskSchedulerConfig {

    @Bean(name = "asyncNotificacao")
    public TaskScheduler executor(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setErrorHandler(exception -> {
            System.out.println("Houve um erro com o threadPoolTaskScheduler " + exception.getMessage());
        });
        threadPoolTaskScheduler.setThreadNamePrefix("Thread para comunicação notificação externo ");
        return threadPoolTaskScheduler;
    }

}
