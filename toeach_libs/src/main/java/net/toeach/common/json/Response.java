package net.toeach.common.json;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 通用接口返回对象<br/>
 * com.xiesi.common.json.Response
 * @author 万云  <br/>
 * @version 1.0
 */
public class Response {
	@JSONField(name="status")
	private String status;
	@JSONField(name="secret")
	private int secret;
	@JSONField(name="data")
	private String data;
	@JSONField(name="errorMessage")
	private String errorMessage;

	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSecret() {
		return secret;
	}
	public void setSecret(int secret) {
		this.secret = secret;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String toString() {
		return "Response [status=" + status + ", secret=" + secret + ", data="
				+ data + ", errorMessage=" + errorMessage + "]";
	}	
}
