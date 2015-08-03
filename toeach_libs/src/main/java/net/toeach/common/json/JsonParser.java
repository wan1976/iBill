package net.toeach.common.json;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

public abstract class JsonParser<T> {
	private T data;// 数据对象
	
	/**
	 * 构造函数
	 */
	public JsonParser() {
	}
	
	/**
	 * 转换对象<br/>
	 * @param json json字符串
	 * @return 转换对象
	 */
	public T parseObject(String json, Type type) throws JSONException {
		// 首先转为Response对象
		Response resp = JSON.parseObject(json, Response.class);
		
		// 再次转换data对象为指定的类型
		data = JSON.parseObject(process(resp), type);
		return data;
	}
	
	/**
	 * 解密方法<br/>
	 * @param resp Response对象
	 * @return 解密后字符串
	 */
	public abstract String process(Response resp);
}
