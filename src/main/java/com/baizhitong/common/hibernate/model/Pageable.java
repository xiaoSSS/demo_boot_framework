package com.baizhitong.common.hibernate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuah
 * 为datatable 1.10定制
 */
public class Pageable implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 开始显示记录坐标
	 */
	private int start;

	/**
	 * 数据显示记录条数
	 */
	private int length;

	/**
	 * 当前页码数
	 */
	private int page = 1;

	/**
	 * 不知道干嘛的，感觉是每调用一次 就是 +1的
	 */
	private int draw;

	/**
	 * 查询条件过滤
	 */
	private List<Filter> filters = new ArrayList<Filter>();

	/**
	 * 排序
	 */
	private List<Order> orders = new ArrayList<Order>();

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public void addFilter(Filter filter) {
		this.filters.add(filter);
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}

	/**
	 * @return 根据参数获取当前页码数
	 */
	public int getStartPage() {
		return start % length > 0 ? start / length + 2 : start / length + 1;
	}
	
	/**
	 * 初始化分页管理器
	 * @param start
	 * @param length
	 * @return
	 */
	public static Pageable pageable(int start,int length)
	{
		Pageable pageable = new Pageable();
		pageable.setStart(start);
		pageable.setLength(length);
		return pageable;
	}
	
	/**
	 * 初始化分页管理器
	 * @param start
	 * @param length
	 * @return
	 */
	public static Pageable pageable(int length)
	{
		Pageable pageable = new Pageable();
		pageable.setStart(0);
		pageable.setLength(length);
		return pageable;
	}
}