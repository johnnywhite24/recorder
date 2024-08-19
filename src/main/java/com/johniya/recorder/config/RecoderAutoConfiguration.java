package com.johniya.recorder.config;

import com.johniya.recorder.OperateLogService;
import com.johniya.recorder.OperateUser;
import com.johniya.recorder.annotation.OperateLog;
import com.johniya.recorder.context.LogContextFactory;
import com.johniya.recorder.handlers.*;
import com.johniya.recorder.interceptor.DefaultLogMethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.List;

@Configuration
@AutoConfigureAfter(LogHandler.class)
public class RecoderAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(RecoderAutoConfiguration.class);

    @Bean
    public OperateLogServiceWrapper operateLogServiceWrapper(@Nullable List<OperateLogService> operateLogServices) {
        return new OperateLogServiceWrapper(operateLogServices);
    }

    @Bean
    public ServiceBeforeLogHandler serviceBeforeLogHandler(OperateLogServiceWrapper operateLogServiceWrapper) {
        return new ServiceBeforeLogHandler(operateLogServiceWrapper);
    }

    @Bean
    public ServiceAfterLogHandler serviceAfterLogHandler(OperateLogServiceWrapper operateLogServiceWrapper) {
        return new ServiceAfterLogHandler(operateLogServiceWrapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public LogHandlerRegister logHandlerRegister(List<BeforeLogHandler> beforeLogHandlers, List<AfterLogHandler> afterLogHandlers) {
        LogHandlerRegister logHandlerRegister = new LogHandlerRegister();
        int beforeOrder = 0;
        for (int i = 0; i < beforeLogHandlers.size(); i ++) {
            LogHandler temp = beforeLogHandlers.get(i);
            logHandlerRegister.registerBefore(beforeOrder, temp);
            logger.info("Recorder register before logHandler [{}]", temp.getClass().getName());
            beforeOrder ++;
        }
        int afterOrder = 0;
        for (int i = 0; i < afterLogHandlers.size(); i ++) {
            LogHandler temp = afterLogHandlers.get(i);
            logHandlerRegister.registerAfter(afterOrder, temp);
            logger.info("Recorder register after logHandler [{}]", temp.getClass().getName());
            afterOrder ++;
        }
        return logHandlerRegister;
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(ApplicationContext context,
                                                         LogHandlerRegister logHandlerRegister,
                                                         @Nullable LogContextFactory logContextFactory,
                                                         @Nullable OperateUser operateUser) {
        DefaultLogMethodInterceptor interceptor = new DefaultLogMethodInterceptor(context, logHandlerRegister, operateUser);
        if (logContextFactory != null) {
            interceptor.setLogContextFactory(logContextFactory);
        }
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, OperateLog.class);

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}
