package com.xuecheng.framework.export;

/**
 * Ecif传递过来的公共码值，用来得到中文对照名，组成模板短信发送内容
 **/
public class EcifTempParam {
	private String name = "";
	private String value = "";

	public EcifTempParam(String n, String v) {
		this.name = n;
		this.value = v;
	}

	public EcifTempParam() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
