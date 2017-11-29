package com.baizhitong.common.hibernate.resultTransformer;

import java.util.HashMap;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MapClassAliasedTupleSubsetResultTransformer<T extends HashMap> extends AliasedTupleSubsetResultTransformer{

	private Logger logger = LoggerFactory.getLogger(MapClassAliasedTupleSubsetResultTransformer.class);
	
	private Class<T> targetClass;
	
	public MapClassAliasedTupleSubsetResultTransformer(Class<T> targetClass) {
		this.targetClass = targetClass;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		try{
			HashMap result = targetClass.newInstance();
			for ( int i=0; i<tuple.length; i++ ) {
				String alias = aliases[i];
				if ( alias!=null ) {
					result.put( alias, tuple[i] );
				}
			}
			return result;
		}catch(Exception e)
		{
			logger.error("hibernate 返回值类型转换为 {0} 出现异常",targetClass.getName(),e);
			return null;
		}
	}

	@Override
	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

}
