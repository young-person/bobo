
package com.bobo.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;



public enum LogTypeEnum {
	/**
	 * 操作日志
	 */
	OPERATION_LOG("10", "操作日志"),
	/**
	 * 登录日志
	 */
	LOGIN_LOG("20", "登录日志"),
	/**
	 * 异常日志
	 */
	EXCEPTION_LOG("30", "异常日志");

	String type;
	String name;

	LogTypeEnum(String type, String name) {
		this.type = type;
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public String getName() {
		return name;
	}

	public static String getName(String type) {
		for (LogTypeEnum ele : LogTypeEnum.values()) {
			if (type.equals(ele.getType())) {
				return ele.getName();
			}
		}
		return null;
	}

	public static LogTypeEnum getEnum(String type) {
		for (LogTypeEnum ele : LogTypeEnum.values()) {
			if (type.equals(ele.getType())) {
				return ele;
			}
		}
		return LogTypeEnum.OPERATION_LOG;
	}


	public static List<Map<String, Object>> getList() {
		List<Map<String, Object>> list = Lists.newArrayList();
		for (LogTypeEnum ele : LogTypeEnum.values()) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("key", ele.getType());
			map.put("value", ele.getName());
			list.add(map);
		}
		return list;
	}

}
