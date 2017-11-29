//package com.baizhitong.dao;
//
//import com.baizhitong.common.dao.jdbc.BaseDaoSupport;
//import com.baizhitong.common.dao.jdbc.BasePageDaoSupport;
//import com.baizhitong.entity.DemoEntity;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
///**
// * Created by wangsj on 2017/11/28.
// */
//@Component
//public class DemoDao extends BasePageDaoSupport<DemoEntity,Integer>{
//
//    @Override
//    protected String getPKColumn() {
//        return "id";
//    }
//
//    @Override
//    @Resource(name = "dataSource")
//    public void setDataSource(DataSource dataSource) {
//        super.setDataSourceReadOnly(dataSource);
//        super.setDataSourceWrite(dataSource);
//    }
//
//
//}
