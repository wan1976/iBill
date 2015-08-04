package net.toeach.base;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 群组数据库访问对象 <br/>
 * com.xiesi.demo.dao.BaseDao
 * @author 万云  <br/>
 * @version 1.0
 */
public class TBaseDao<T> {
	protected DbUtils db;
	protected Class<T> clazz;

	/**
	 * 构造函数
	 */
	@SuppressWarnings("unchecked")
	public TBaseDao(DbUtils db) {
		this.db = db;
		
		// 获取泛型T对应的class
		Type genType = getClass().getGenericSuperclass();  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
        clazz = (Class<T>) params[0];
	}
	
	/**
	 * 保存对象
	 * @param bean 数据对象
	 * @throws DbException 异常对象
	 */
	public void save(T bean) throws DbException {
		db.saveOrUpdate(bean);
	}
	
	/**
	 * 保存对象队列
	 * @param beans 数据对象集合
	 * @throws DbException 异常对象
	 */
	public void saveAll(List<T> beans) throws DbException {
		if (beans != null && beans.size() > 0) {
			db.saveOrUpdateAll(beans);
		}
	}
	
	/**
	 * 删除实体对象
	 * @param id 数据标识
	 * @throws DbException 异常对象
	 */
	public void delete(int id) throws DbException {
		db.deleteById(clazz, id);
	}

	/**
	 * 删除实体对象
	 *
	 * @param list 实体对象
	 * @throws DbException 异常对象
	 */
	public void delete(List<T> list) throws DbException {
		db.deleteAll(list);
	}
	
	/**
	 * 查找全部对象
	 * @param orderBy 排序字段
	 * @return 结果集合
	 */
	public List<T> getList(String orderBy, boolean desc) throws DbException {
		Selector selector = Selector.from(clazz).orderBy(orderBy, desc);
		return db.findAll(selector);
	}
}
