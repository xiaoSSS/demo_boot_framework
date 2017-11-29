package com.baizhitong.service.impl;

//import com.baizhitong.dao.DemoDao;
import com.baizhitong.common.datasource.DataSourceType;
import com.baizhitong.common.hibernate.BaseDao;
import com.baizhitong.entity.DemoEntity;
import com.baizhitong.service.ITestService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.sql.DataSourceDefinition;
import java.util.UUID;

/**
 * Created by wangsj on 2017/11/28.
 */
@Service
public class TestServiceImpl implements ITestService{

   // @Autowired
   // private DemoDao demoDao;

    @Autowired
    BaseDao baseDao;

    @Override
    @DataSourceType(DataSourceType.READONLY)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String getInfo(){
        DemoEntity demo = baseDao.findByPrimaryKey(DemoEntity.class,1);
        return demo.getName();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public Integer saveInfo(){
        DemoEntity demo = new DemoEntity();
        demo.setName(UUID.randomUUID().toString());
        return (Integer)baseDao.insert(demo);
    }
}
