package com.baizhitong.common.datasource;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class DataSourceInterceptor {

    protected Logger log=LoggerFactory.getLogger(getClass());
    
    public void setDataSource(JoinPoint jp){
        Class clazz=jp.getTarget().getClass(); //得到目标对象
        String methodName= jp.getSignature().getName();//得到方法名
        Object[] args=jp.getArgs(); //得到方法参数
        Class[] argTypes=new Class[args.length];
        int i=0;
        for(Object arg:args){
            argTypes[i]=arg==null?null:arg.getClass();
            i++;
        }
        try{
            Method[] methods =clazz.getMethods();
            for(Method method:methods){ //遍历方法
                if(method.getName().equals(methodName)){ //方法名相同
                    Class[] _argTypes=method.getParameterTypes();
                    if(checkArgTypes(_argTypes,argTypes)){  // 比较参数类型
                        if(method.getAnnotation(DataSourceType.class)!=null){ //有注解的 优先注解
                            DataSourceType dataSourceType= method.getAnnotation(DataSourceType.class);
                            String type=dataSourceType.value();
                            DatabaseContextHolder.setCustomerType(type);
                        }else{ //没有注解 判断方法名
                            methodNameHandler(methodName);
                        }
                    }
                }
            }
        }catch(Exception ex){
            log.error("",ex);
        }


    }

    private boolean checkArgTypes(Class[] _argTypes, Class[] argTypes) {
        // TODO Auto-generated method stub
        if(_argTypes.length!=argTypes.length){
            return false;
        }else{
            for(int i=0;i<_argTypes.length;i++){
                if(argTypes[i]==null){
                    continue;
                }else if(!_argTypes[i].getClass().equals(argTypes[i].getClass())){
                    return false;
                }
            }
            return true;
        }

    }

    private void methodNameHandler(String methodName){
           DatabaseContextHolder.setCustomerType("dataSourceWrite");
    }
    
}
