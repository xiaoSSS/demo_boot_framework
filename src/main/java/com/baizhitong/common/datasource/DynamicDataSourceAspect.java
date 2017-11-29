package com.baizhitong.common.datasource;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by wangsj on 2017/11/29.
 */
@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, DataSourceType ds) throws Throwable {
        String dsId = ds.value();
        logger.debug("Use DataSource : {} > {}", dsId, point.getSignature());
        DatabaseContextHolder.setCustomerType(dsId);
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DataSourceType ds) {
        logger.debug("Revert DataSource : {} > {}", ds.value(), point.getSignature());
        DatabaseContextHolder.clearCustomerType();
    }

}