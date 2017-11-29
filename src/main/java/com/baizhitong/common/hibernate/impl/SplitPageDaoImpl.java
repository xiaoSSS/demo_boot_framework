package com.baizhitong.common.hibernate.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baizhitong.common.hibernate.SplitPageDao;
import com.baizhitong.common.hibernate.model.Page;
import com.baizhitong.common.hibernate.model.QueryRule;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;



/**
 * @author Administrator
 * @date 2012-3-13
 * @time 下午11:18:29
 * 分页dao
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class SplitPageDaoImpl extends BaseDaoImpl implements SplitPageDao {

	private Logger log = LoggerFactory.getLogger(getClass());

	public Page findByHql(String hql, int nowpage, int pageSize) {
		Page page = new Page();
		try {
			//如果nowPage为-1 列出所有信息，不进行分页
			if (nowpage == -1) {
				page.setPage(1);
				page.setPages(1);
				page.setData(findByHql(hql));
			} else {
				String countQueryString = "SELECT COUNT(*) "
						+ removeSelect(removeOrders(hql));
				int totalRecords = Integer.parseInt(hqlUniqueResult(
						countQueryString).toString());
				page.setRecordsTotal(totalRecords);

				int start = pageSize * (nowpage - 1);
				int totalPage = totalRecords / pageSize;
				if (totalPage * pageSize < totalRecords) {
					totalPage++;
				}
				page.setPages(totalPage);

				List data = super.searchHql(hql, start, pageSize);
				page.setData(data);
				page.setPage(nowpage);
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
		return page;
	}

	public Page findByHql(String hql, int nowpage) {
		return findByHql(hql, nowpage, PAGE_SIZE);
	}

	public Page findBySql(String sql, int nowpage, int pageSize) {
		Page page = new Page();
		try {
			String countSql = "SELECT COUNT(*) TOTAL_RECORDS FROM (" + sql
					+ ") as Total";
			String strCount = this.getSession().createSQLQuery(countSql)
					.uniqueResult().toString();
			Long totalRecords = Long.parseLong(strCount);
			page.setRecordsTotal(totalRecords);
			//如果nowPage为-1 列出所有信息，不进行分页
			if (nowpage == -1) {
				page.setPage(1);
				page.setPages(1);
				page.setData(findBySql(sql));
			} else {
				int start = pageSize * (nowpage - 1);
				long totalPage = totalRecords / pageSize;
				if (totalPage * pageSize < totalRecords) {
					totalPage++;
				}
				page.setPages(totalPage);

				List data = super.searchSql(sql, start, pageSize);
				page.setData(data);
				page.setPage(nowpage);
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
		return page;
	}

	public Page findBySql(String sql, int nowpage) {
		return findBySql(sql, nowpage, PAGE_SIZE);
	}

	@Override
	public Page findByQuery(Query query, int nowpage, int pageSize) {
		Page page = new Page();
		try {
			Long totalRecords = getQueryRowNumber(query);
			page.setRecordsTotal(totalRecords);
			//如果nowPage为-1 列出所有信息，不进行分页
			if (nowpage == -1) {
				page.setPage(1);
				page.setPages(1);
				page.setData(query.list());
			} else {
				int start = pageSize * (nowpage - 1);
				int totalPage = (int) (totalRecords / pageSize);
				if (totalPage * pageSize < totalRecords) {
					totalPage++;
				}
				page.setPages(totalPage);

				query.setFirstResult(start);
				query.setMaxResults(pageSize);

				List data = query.list();
				page.setData(data);
				page.setPage(nowpage);
			}
		} catch (Exception e) {
			log.error("findByQuery方法出错：" + e, e);
		}
		return page;
	}

	@Override
	public Page findByQuery(Query query, int nowpage) {
		return findByQuery(query, nowpage, PAGE_SIZE);
	}

	@Override
	public Page findByCriteria(Criteria criteria, int nowpage, int pageSize) {
		Page page = new Page();
		try {
			//			List list = criteria.list();
			//			int totalRecords = list.size();
			//			page.setTotal(totalRecords);

			//如果nowPage为-1 列出所有信息，不进行分页
			if (nowpage == -1) {
				page.setPage(1);
				page.setData(criteria.list());
			} else {
				//先获取当前页的数据，投机的取法
				int start = pageSize * (nowpage - 1);

				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);

				List data = criteria.list();
				page.setData(data);
				page.setPage(nowpage);

				//获取总的页码数
				criteria.setFirstResult(0);
				criteria.setMaxResults(Integer.MAX_VALUE);
				//设置criteria查询结果有缓存
				criteria.setCacheable(true);
				Long totalRecords = (Long) criteria.setProjection(
						Projections.rowCount()).uniqueResult();
				page.setRecordsTotal(totalRecords);
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
		return page;
	}

	@Override
	public Page findByCriteria(Criteria criteria, int nowpage) {
		return findByCriteria(criteria, nowpage, PAGE_SIZE);
	}


	@Override
	public Page query(QueryRule queryRule,int nowpage){
		Criteria criteria = toCriteria(queryRule);
		return findByCriteria(criteria, nowpage);
	}

	private long getQueryRowNumber(Query query) {
		long rowNumber;
		ScrollableResults result = query.scroll();
		result.last();
		if (result.getRowNumber() >= 0) {
			rowNumber = result.getRowNumber() + 1;
		} else {
			rowNumber = 0;
		}
		return rowNumber;
	}

	private Object hqlUniqueResult(String hql) {
		return this.getSession().createQuery(hql).uniqueResult();
	}

	/** 
	 * 去除select 子句，未考虑union的情况 
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword from");
		return hql.substring(beginPos);
	}

	/** 
	 * 去除orderby 子句 
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
