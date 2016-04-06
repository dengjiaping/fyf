package com.company.fyf.net;

import java.util.HashMap;

public class ErrorCodeMsg {

	private static String UNKNOW_ERROR = "未知错误";

	private static HashMap<String, String> map = new HashMap<String, String>();

	static {

		// public
		map.put("param_error", "参数错误");
		map.put("api_not_exist", "API不存在");
		map.put("api_error_or_not_exist", "API不存在或者请求存在语法错误");
		map.put("param_error_timestamp_timeout", "timestamp请求参数超时");

		// member.register
		map.put("register_closed", "APP禁止新用户注册");
		map.put("username_format_error", "username错误参数");
		map.put("password_format_error", "password错误参数");
		map.put("mobile_verify_code_empty", "手机验证码不能为空");
		map.put("mobile_verify_code_notexist", "验证码不存在");
		map.put("mobile_verify_code_overtime", "验证码超时");
		map.put("mobile_has_exist", "此手机号码已存在");
		map.put("operation_failure", "异常，写入数据时失败");

		// member.login
		map.put("username_empty_or_error", "用户名为空或格式不正确");
		map.put("password_emptyor_error", "密码不能为空");
		map.put("password_format_incorrect", "密码格式不正确");
		map.put("password_error_times_max", "密码错误次数太多");
		map.put("username_not_exist", "用户名不存在");
		map.put("password_error", "密码不正确");
		map.put("user_is_lock", "用户被锁定");

		// member.sendCheckCode
		map.put("mobile_format_error", "手机号码格式错误");
		map.put("username_has_exist", "此手机号码已经注册过");
		map.put("today_sendsms_over", "当日发送短信数量超过限制");

		// member.logout

		// member.public_profile
		// map.put("username_not_exist", "用户名不存在");
		// map.put("username_empty_or_error", "用户名为空或格式不正确");

		// member.accountProfile

		// member.editPassword
		map.put("password_format_incorrect", "旧密码格式错误");
		map.put("old_password_incorrect", "旧密码错误");
		map.put("newpassword_format_incorrect", "新密码格式错误");
		map.put("password_newpassword_same", "新密码和旧密码不能相同");

		// member.public_forget_password_mobile
		map.put("mobile_verify_code_error", "短信验证码错误");
		// map.put("mobile_verify_code_overtime", "短信验证码已过期");

		// member.accountProfileEdit

		// checkin.checkin
		map.put("content_lenght_to_long", "签到内容太长");
		map.put("today_has_checkin", "今天已经签到过");

		// checkin.myCheckinList

		// rubbish.uploadRubbish
		map.put("insert_error", "数据定入失败");

		// rubbish.myRubbishList

		// rubbish.public_type

		// credit.myCreditList

		// credit.public_creditRankTotal

		// credit.public_creditRankByUsername

		// credit.public_creditRule

		// credit.creditAddByUsername
		map.put("no_permissions", "权限不足");
		map.put("credit_num_error", "积分数值错误");
		// map.put("username_format_error", "用户名格式错误参数");
		map.put("reason_error", "操作原因不能为空");
		// map.put("username_not_exist", "用户名不存在");
		map.put("no_permissions_addto_yourself	", "不可以给自己添加");
		map.put("no_permissions_addto_administrator	", "不可以给管理组添加积分");

		// feedback.myFeedbackList

		// feedback.add
		map.put("content_empty", "留言内容不能为空");
		map.put("content_lenght_to_long", "留言内容太长");

		// daka.add
		map.put("no_permissions", "权限不足");
		map.put("typeid_error", "类型错误，1：上班，2：下班");
		map.put("today_worktime_has_daka", "今天上班已经打卡");
		map.put("today_workofftime_has_daka", "今天下班已经打卡");

		// daka.myDakaList
		map.put("no_permissions", "权限不足");

		// apptool.splash

		// apptool.deviceInfo

		// apptool.checkUpdate

		// apptool.aboutus

		// apptool.announce

		// apptool.announceView
		map.put("info_does_not_exists", "内容不存在");

		// apptool.banner

		// apptool.areas

	}

	public static String msgByCode(String code) {
		if (map.containsKey(code)) {
			return map.get(code);
		}

		return UNKNOW_ERROR;
	}
	
	public static String getDefaultMsg(int code){
		return "数据解析异常" ;
	}

}
