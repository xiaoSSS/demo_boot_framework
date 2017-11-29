package com.baizhitong.common.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.baizhitong.common.hibernate.model.QueryRule;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;



/**
 * @author Administrator
 * 数据库操作基础dao
 */
public interface BaseDao {
	/**
	 * @param entityName
	 * @param key
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public <T> T findByPrimaryKey(String entityName, Serializable key)
			throws org.hibernate.HibernateException;

	/**
	 * @param clazz
	 * @param key
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public <T> T findByPrimaryKey(Class<T> clazz, Serializable key)
			throws org.hibernate.HibernateException;

	/**
	 * @param clazz
	 * @param key
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public <T> T loadByPrimaryKey(Class<T> clazz, Serializable key)
			throws org.hibernate.HibernateException;

	/**
	 * @param obj
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public Serializable insert(Object obj)
			throws org.hibernate.HibernateException;

	/**
	 * @param objs
	 * @return
	 */
	public <T> Collection<T> insert(Collection<T> objs);

	/**
	 * @param obj
	 * @throws org.hibernate.HibernateException
	 */
	public void saveOrUpdate(Object obj)
			throws org.hibernate.HibernateException;

	/**
	 * @param objs
	 * @throws org.hibernate.HibernateException
	 */
	public <T> void saveOrUpdate(Collection<T> objs)
			throws org.hibernate.HibernateException;

	/**
	 * @param obj
	 */
	public void update(Object obj);

	/**
	 * @param objs
	 */
	public <T> void update(Collection<T> objs);

	/**
	 * @param obj
	 * @throws org.hibernate.HibernateException
	 */
	public void delete(Object obj) throws org.hibernate.HibernateException;

	/**
	 * @param objs
	 */
	public <T> void delete(Collection<T> objs);

	/**
	 * @param obj
	 * @throws org.hibernate.HibernateException
	 */
	public void excuteSql(Object obj) throws org.hibernate.HibernateException;

	/**
	 * @param objs
	 * @throws org.hibernate.HibernateException
	 */
	public <T> void excuteSql(Collection<T> objs)
			throws org.hibernate.HibernateException;

	/**
	 * @param hql
	 * @throws org.hibernate.HibernateException
	 */
	public void excuteHql(String hql) throws org.hibernate.HibernateException;

	/**
	 * @param hqls
	 * @throws org.hibernate.HibernateException
	 */
	public <T> void excuteHql(Collection<T> hqls)
			throws org.hibernate.HibernateException;

	/**
	 * @param namedQuery
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public Query getNamedQuery(String namedQuery)
			throws org.hibernate.HibernateException;

	/**
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public Session getSession() throws org.hibernate.HibernateException;

	/**
	 * @param object
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public Object merge(Object object) throws org.hibernate.HibernateException;

	/**
	 * @param hql
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public List findByHql(String hql) throws org.hibernate.HibernateException;

	/**
	 * @param hql
	 * @param valueType
	 * @return 查询hql 返回为唯一的值
	 */
	public <RV> RV uniqueResult(String hql, Class<RV> valueType) ;
	
	/**
	 * @param sql
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public List findBySql(String sql) throws org.hibernate.HibernateException;

	/**
	 * @param hql
	 * @param start
	 * @param limit
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public List searchHql(String hql, int start, int limit)
			throws org.hibernate.HibernateException;

	/**
	 * @param sql
	 * @param start
	 * @param limit
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public List searchSql(String sql, int start, int limit)
			throws org.hibernate.HibernateException;

	/**
	 * @param hql
	 * @param objs
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	public List query(String hql, Object[] objs)
			throws org.hibernate.HibernateException;
	
	/**
	 * 查询对应条件的数据
	 * @param queryRule
	 * @return
	 */
	public List query(QueryRule queryRule);
	
	/**
	 * @param queryRule
	 * @param valueType
	 * @return 查询唯一值
	 */
	public <RV> RV uniqueResult(QueryRule queryRule, Class<RV> valueType) ;
	
	/**
	 * 查询对应条件的值
	 * @param queryRule
	 * @param start
	 * @param limit
	 * @return
	 */
	public List query(QueryRule queryRule, int start, int limit);

	
}
