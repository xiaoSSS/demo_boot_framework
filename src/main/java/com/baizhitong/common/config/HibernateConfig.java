package com.baizhitong.common.config;

import com.baizhitong.common.hibernate.BaseDao;
import com.baizhitong.common.hibernate.SplitPageDao;
import com.baizhitong.common.hibernate.impl.BaseDaoImpl;
import com.baizhitong.common.hibernate.impl.SplitPageDaoImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

/**
 * Created by wangsj on 2017/11/28.
 */
@Configuration
public class HibernateConfig {

    @Autowired
    SessionFactory sessionFactory;

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

    @Bean
    public BaseDao baseDao(){
        BaseDaoImpl baseDao = new BaseDaoImpl();
        baseDao.setSessionFactory(sessionFactory);
        return baseDao;
    }

    @Bean
    public SplitPageDao splitPageDao(){
        SplitPageDaoImpl splitPageDao = new SplitPageDaoImpl();
        splitPageDao.setSessionFactory(sessionFactory);
        return splitPageDao;
    }
}
