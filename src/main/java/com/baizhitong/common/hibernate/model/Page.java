package com.baizhitong.common.hibernate.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @param <T>
 * 分页对象数据
 */
public class Page<T> implements Serializable {

	public Page() {
	}

	/**
	 * @param rows
	 * @param total
	 * @param pageable
	 *            构造函数
	 */
	public Page(List<T> rows, long total, Pageable pageable) {
		this.data = rows;
		this.recordsFiltered = total;
		this.recordsTotal = total;
	}

	/**
	 * 一直加+1
	 */
	private int draw;

	/**
	 * 记录总条数
	 */
	private long recordsFiltered;

	/**
	 * 总记录数
	 */
	private long recordsTotal;

	/**
	 * 当前页码
	 */
	private long page;

	/**
	 * 总页码数
	 */
	private long pages;

	private List<T> data;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsFiltered() {
		return recordsFiltered == 0 ? recordsTotal : recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	/**
	 * @param pageSize
	 *            每页数目
	 * @param totalRecords
	 *            总记录数
	 * @return
	 * @todo 获取页面总页数 静态方法
	 */
	public static int getTotalPage(int pageSize, int totalRecords) {
		int totalPage = totalRecords / pageSize;
		if (totalPage * pageSize < totalRecords) {
			totalPage++;
		}
		return totalPage;
	}

	/**
	 * @param pageSize
	 *            每页显示记录
	 * @param displayStart
	 *            显示开始的记录页面
	 * @return
	 */
	public static int getNowPage(int pageSize, int displayStart) {
		return displayStart / pageSize + 1;
	}
}
