package com.baizhitong.common.hibernate.impl;

import com.baizhitong.common.hibernate.BaseDao;
import com.baizhitong.common.hibernate.model.Order;
import com.baizhitong.common.hibernate.model.Order.Direction;
import com.baizhitong.common.hibernate.model.QueryRule;
import com.baizhitong.common.hibernate.model.Filter;
import com.baizhitong.common.hibernate.model.Filter.Operator;
import com.baizhitong.common.hibernate.resultTransformer.MapClassAliasedTupleSubsetResultTransformer;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;



@SuppressWarnings("unchecked")
public class BaseDaoImpl implements BaseDao {
	
	private Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);

	public Object findByPrimaryKey(String entityName, Serializable key)
			throws HibernateException {
		Object obj = getSession().get(entityName, key);
		return obj;
	}

	public <T> T findByPrimaryKey(Class<T> clazz, Serializable key) {
		T obj = (T) getSession().get(clazz, key);
		return obj;
	}

	public <T> T loadByPrimaryKey(Class<T> clazz, Serializable key) {
		T obj = (T) getSession().load(clazz, key);
		return obj;
	}

	public Query getNamedQuery(String namedQuery) {
		return getSession().getNamedQuery(namedQuery);
	}

	public void update(Object obj) {
		getSession().update(obj);
	}

	public Serializable insert(Object obj) {
		return getSession().save(obj);
	}

	public void saveOrUpdate(Object obj)
			throws HibernateException {
		getSession().saveOrUpdate(obj);
	}

	public void excuteSql(Object obj) throws HibernateException {
		if (obj == null || "".equals(obj))
			return;
		this.getSession().createSQLQuery(obj.toString()).executeUpdate();
	}

	public void excuteHql(String hql) {
		this.getSession().createQuery(hql).executeUpdate();
	}

	public void delete(Object obj) {
		this.getSession().delete(obj);
	}

	public List findByHql(String hql) {
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public <RV> RV uniqueResult(String hql, Class<RV> valueType) {
		Query query = this.getSession().createQuery(hql);
		//返回值如果为map的子类，则使用beanToMap转换器
		if(valueType.getSuperclass() == HashMap.class)
		{
			query.setResultTransformer(new MapClassAliasedTupleSubsetResultTransformer(valueType));
		}
		//如果返回值不是map，则启用自定义结果转换器
		else if(valueType != Map.class)
		{
			AliasToBeanResultTransformer aliasToBeanResultTransformer = new AliasToBeanResultTransformer(valueType);
			query.setResultTransformer(aliasToBeanResultTransformer);
		}
		List resultList = query.list();
		if (resultList != null && resultList.size() == 1) {
			return (RV) resultList.get(0);
		} else if (resultList != null && resultList.size() != 1) {
			log.error("当前结果的返回值个数为:{0} 实际希望的返回个数为:1 查询条件:{1}", resultList.size(),
					hql);
			return null;
		} else {
			log.error("当前条件查询结果为null，异常！查询条件:{0}", hql);
			return null;
		}
	}

	public List findBySql(String sql) {
		Query query = this.getSession().createSQLQuery(sql)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	public List searchHql(String hql, int start, int limit) {
		Query query = this.getSession().createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.list();
	}

	public List searchSql(String sql, int start, int limit) {
		Query query = this.getSession().createSQLQuery(sql)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.list();
	}

	public List query(String hql, Object[] objs) {
		Query query = this.getSession().createQuery(hql);

		int i = 0;
		for (Object obj : objs) {
			if (obj.getClass().equals(String.class)) {
				query.setString(i, (String) obj);
			}
			if (obj.getClass().equals(Date.class)) {
				query.setDate(i, (Date) obj);
			}
			i++;
		}

		return query.list();
	}

	public <T> Collection<T> insert(Collection<T> objs) {
		List result = new ArrayList();
		if (objs == null || objs.isEmpty())
			return result;
		Object t;
		for (Iterator iterator = objs.iterator(); iterator.hasNext();) {
			t = (Object) iterator.next();
			result.add(insert(t));
		}
		return result;
	}

	public <T> void saveOrUpdate(Collection<T> objs) throws HibernateException {
		if (objs == null || objs.isEmpty())
			return;
		Object t;
		for (Iterator iterator = objs.iterator(); iterator.hasNext(); saveOrUpdate(t))
			t = (Object) iterator.next();
	}

	public <T> void update(Collection<T> objs) {
		if (objs == null || objs.isEmpty())
			return;
		Object t;
		for (Iterator iterator = objs.iterator(); iterator.hasNext(); update(t))
			t = (Object) iterator.next();
	}

	public <T> void delete(Collection<T> objs) {
		if (objs == null || objs.isEmpty())
			return;
		Object t;
		for (Iterator iterator = objs.iterator(); iterator.hasNext(); delete(t))
			t = (Object) iterator.next();
	}

	public <T> void excuteSql(Collection<T> objs) throws HibernateException {
		if (objs == null || objs.isEmpty())
			return;
		Object t;
		for (Iterator iterator = objs.iterator(); iterator.hasNext(); excuteSql(t))
			t = (Object) iterator.next();
	}

	public <T> void excuteHql(Collection<T> hqls) {
		if (hqls == null || hqls.isEmpty())
			return;
		String hql;
		for (Iterator iterator = hqls.iterator(); iterator.hasNext(); excuteHql(hql))
			hql = (String) iterator.next().toString();
	}

	public Object merge(Object object) {
		return getSession().merge(object);
	}

	@Override
	public List query(QueryRule queryRule) {
		Criteria criteria = toCriteria(queryRule);
		return criteria.list();
	}

	@Override
	public <RV> RV uniqueResult(QueryRule queryRule, Class<RV> valueType) {
		Criteria criteria = toCriteria(queryRule);
		if(valueType != null && valueType != Map.class)
		{
			criteria.setResultTransformer(new AliasToBeanResultTransformer(valueType));
		}
		Object o = criteria.uniqueResult();
		return (RV) o;
	}

	@Override
	public List query(QueryRule queryRule, int start, int limit) {
		Criteria criteria = toCriteria(queryRule);
		// 设置分页信息
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);

		return criteria.list();
	}


	protected Criteria toCriteria(QueryRule queryRule) {
		Class persistentClass = queryRule.getPersistentClass();
		// 构建对应实体类对象
		Criteria criteria = getSession().createCriteria(persistentClass);

		// 条件查询
		List<Filter> filterList = queryRule.getFilters();
		for (Filter filter : filterList) {
			Operator operator = filter.getOperator();
			/** 等于 */
			if (operator == Operator.eq) {
				criteria.add(Restrictions.eq(filter.getProperty(),
						filter.getValue()));
			}

			/** 不等于 */
			else if (operator == Operator.ne) {
				criteria.add(Restrictions.ne(filter.getProperty(),
						filter.getValue()));
			}

			/** 大于 */
			else if (operator == Operator.gt) {
				criteria.add(Restrictions.gt(filter.getProperty(),
						filter.getValue()));
			}

			/** 小于 */
			else if (operator == Operator.lt) {
				criteria.add(Restrictions.lt(filter.getProperty(),
						filter.getValue()));
			}

			/** 大于等于 */
			else if (operator == Operator.ge) {
				criteria.add(Restrictions.ge(filter.getProperty(),
						filter.getValue()));
			}

			/** 小于等于 */
			else if (operator == Operator.le) {
				criteria.add(Restrictions.le(filter.getProperty(),
						filter.getValue()));
			}

			/** 相似 */
			else if (operator == Operator.like) {
				criteria.add(Restrictions.like(filter.getProperty(),
						filter.getValue()));
			}

			/** 包含 */
			else if (operator == Operator.in) {
				criteria.add(Restrictions.in(filter.getProperty(),
						(Collection) filter.getValue()));
			}

			/** 为Null */
			else if (operator == Operator.isNull) {
				criteria.add(Restrictions.isNull(filter.getProperty()));
			}

			/** 不为Null */
			else if (operator == Operator.isNotNull) {
				criteria.add(Restrictions.isNotNull(filter.getProperty()));
			}
			;
		}

		// 排序
		List<Order> orders = queryRule.getOrders();
		for (Order order : orders) {
			if (order.getDirection() == Direction.asc) {
				criteria.addOrder(org.hibernate.criterion.Order.asc(order
						.getProperty()));
			} else if (order.getDirection() == Direction.desc) {
				criteria.addOrder(org.hibernate.criterion.Order.desc(order
						.getProperty()));
			}
		}

		// 查询投影
		Projection projection = queryRule.getProjection();
		if (projection != null) {
			criteria.setProjection(projection);
			//如果使用了投影，则默认转换为map的list
			criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		}

		return criteria;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
