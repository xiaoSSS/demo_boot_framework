//package com.baizhitong.core.commons;
//
//import org.springframework.transaction.interceptor.TransactionAttributeSource;
//import org.springframework.transaction.interceptor.TransactionInterceptor;
//
///**
// * Created by wangsj on 2017/11/28.
// */
//public class BztTransactionInterceptor extends TransactionInterceptor{
//
//    @Override
//    public TransactionAttributeSource getTransactionAttributeSource() {
//        TransactionAttributeSource attribute=super.getTransactionAttributeSource();
//        return new BztNameMatchTransactionAttributeSource(attribute);
//    }
//
//}
//
//
//
