package com.baizhitong.common.hibernate;

import com.baizhitong.common.hibernate.model.Page;
import com.baizhitong.common.hibernate.model.QueryRule;
import org.hibernate.Criteria;
import org.hibernate.Query;



public interface SplitPageDao extends BaseDao {
	public final int PAGE_SIZE = 10;

	/**
	 * @param query
	 * @param nowpage
	 * @param pageSize
	 * @return 根据query查询出分页结果
	 */
	public Page findByQuery(Query query, int nowpage, int pageSize);

	/**
	 * @param query
	 * @param nowpage
	 * @return
	 */
	public Page findByQuery(Query query, int nowpage);

	/**
	 * @param criteria
	 * @param nowpage
	 * @param pageSize
	 * @return
	 * 根据criteria 查询出分页结果
	 */
	public Page findByCriteria(Criteria criteria, int nowpage, int pageSize);

	/**
	 * @param criteria
	 * @param nowpage
	 * @return
	 */
	public Page findByCriteria(Criteria criteria, int nowpage);

	/**
	 * 根据查询条件查询对应的数据
	 * @param queryRule
	 * @param nowpage
	 * @return
	 */
	public Page query(QueryRule queryRule, int nowpage);
	
	/**
	 * @param hql
	 * @param nowpage
	 * @param pageSize
	 * @return 返回指定hql的分页Page
	 */
	public Page findByHql(String hql, int nowpage, int pageSize);

	/**
	 * @param hql
	 * @param nowpage
	 * @return 返回指定hql的分页Page
	 */
	public Page findByHql(String hql, int nowpage);

	/**
	 * @param sql
	 * @param nowpage
	 * @param pageSize
	 * @return 返回指定sql的分页Page
	 */
	public Page findBySql(String sql, int nowpage, int pageSize);

	/**
	 * @param sql
	 * @param nowpage
	 * @return 返回指定sql的分页Page
	 */
	public Page findBySql(String sql, int nowpage);

}
