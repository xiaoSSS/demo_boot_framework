//package com.baizhitong.core.commons;
//
//import java.lang.reflect.Method;
//
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.interceptor.TransactionAttribute;
//import org.springframework.transaction.interceptor.TransactionAttributeEditor;
//import org.springframework.transaction.interceptor.TransactionAttributeSource;
//
//import com.baizhitong.core.commons.datasource.DataSourceType;
//
//
//public class BztNameMatchTransactionAttributeSource implements TransactionAttributeSource {
//
//    private TransactionAttributeSource transactionAttributeSource ;
//
//    public BztNameMatchTransactionAttributeSource(TransactionAttributeSource source){
//        transactionAttributeSource=source;
//    }
//
//    @Override
//    public TransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass) {
//        // TODO Auto-generated method stub
//        Transactional annotation = method.getAnnotation(Transactional.class);
//        DataSourceType dataSourceType = method.getAnnotation(DataSourceType.class);
//        if(annotation!=null){
//            try{
//                TransactionAttributeEditor tae = new TransactionAttributeEditor();
//                tae.setAsText("PROPAGATION_"+annotation.propagation().toString());
//                TransactionAttribute attr = (TransactionAttribute) tae.getValue();
//                return attr;
//            }catch(Exception ex){
//                return transactionAttributeSource.getTransactionAttribute(method, targetClass);
//            }
//        }else if(dataSourceType!=null&&DataSourceType.READONLY.equals(dataSourceType.value())){
//            try{
//                TransactionAttributeEditor tae = new TransactionAttributeEditor();
//                tae.setAsText("PROPAGATION_NOT_SUPPORTED");
//                TransactionAttribute attr = (TransactionAttribute) tae.getValue();
//                return attr;
//            }catch(Exception ex){
//                return transactionAttributeSource.getTransactionAttribute(method, targetClass);
//            }
//        }else{
//            return transactionAttributeSource.getTransactionAttribute(method, targetClass);
//        }
//    }
//
//
//}
