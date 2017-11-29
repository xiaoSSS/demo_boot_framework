package com.baizhitong.common.hibernate.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projection;

/**
 * @author Administrator
 * 查询规则
 */
public class QueryRule {
	
	private QueryRule(){
		
	}
	
	/**
	 * 返回QueryRule
	 * @return
	 */
	public static QueryRule new0(){
		return new QueryRule();
	}
	
	/**
	 * 返回QueryRule
	 * @return
	 */
	public static QueryRule new0(Class persistentClass){
		QueryRule queryRule =  new QueryRule();
		queryRule.setPersistentClass(persistentClass);
		return queryRule;
	}
	
	/**
	 * 查询对应的持久化类
	 */
	Class persistentClass;
	
	/**
	 * where语句
	 */
	private List<Filter> filters = new ArrayList<Filter>();
	
	/**
	 * 排序
	 */
	private List<Order> orders = new ArrayList<Order>();

	/**
	 * 分页相关信息，如果为空，则不分页
	 */
	Pageable pageable;
	
	/**
	 * 投影，用于某些字段的查询，以及groupby的使用
	 *  Projections.projectionList()
     *   .add( Projections.rowCount(), "catCountByColor" )
	 */
	Projection projection;

	public void addFilter(Filter filter)
	{
		filters.add(filter);
	}
	
	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public Class getPersistentClass() {
		return persistentClass;
	}

	public void setPersistentClass(Class persistentClass) {
		this.persistentClass = persistentClass;
	}
	
}
