package com.company.fyf.net;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.company.fyf.dao.SweepVo;
import com.company.fyf.db.UserDb;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.model.User;
import com.company.fyf.model.UserInfo;
import com.company.fyf.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MemberServer extends AbstractHttpServer {

	public MemberServer() {
		super();
	}

	public MemberServer(Context context) {
		super(context);
	}

	@Override
	protected String getModule() {
		// TODO Auto-generated method stub
		return "member";
	}

	/**
	 * username 手机号 password 密码 mobile_verify 短信验证码
	 * 
	 * POST
	 */
	public void register(String username, String password,String areaid,String address,
			String mobile_verify, final CallBack<UserInfo> back) {
		addParam("act", "register");

		addParam("username", username);
		addParam("areaid", areaid);
		addParam("address", address);
		addParam("password", md5(password));
		addParam("mobile_verify", mobile_verify);
		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				try {
					String userinfo = new JSONObject(data)
							.getString("userinfo");

					UserInfo info = JSON.parseObject(userinfo, UserInfo.class);

					if (info != null) {
						if (back != null)
							back.onSuccess(info);
						UserInfoDb.INSTANCE.update(info);
					} else {
						showAnalyticalException(back);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});
	}

	/**
	 * username 是 用户名。格式：手机号码 password 是 密码 cookietime 否
	 * 整型。单位：秒。建议设置较长的时间以使客户端维持登陆状态
	 * 
	 * POST
	 */

	public void login(String username, String password,
			final CallBack<User> back) {
		addParam("act", "login");

		addParam("username", username);
		addParam("password", md5(password));
		addParam("cookietime", String.valueOf(Integer.MAX_VALUE));

		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				try {
					String userinfo = new JSONObject(data)
							.getString("userinfo");

					UserInfo info = JSON.parseObject(userinfo, UserInfo.class);

					if (info != null) {
						UserInfoDb.INSTANCE.update(info);
						User user = new User();
						user.setLocalLoginTime(System.currentTimeMillis());
						user.setUsername(info.getUsername());
						UserDb.INSTANCE.update(user);
						if (back != null)
							back.onSuccess(user);
					} else {
						showAnalyticalException(back);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});
	}

	/*
	 * sendCheckCode
	 * 
	 * phonenum 是 手机号
	 * 
	 * POST
	 */
	public void sendCheckCode(String phonenum, final CallBack<String> back) {

		addParam("act", "sendCheckCode");

		addParam("phonenum", phonenum);

		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				// 待补充。。。
				// {"result":{"success":"9098"},"success":1}
				if (back != null) {
					try {
						JSONObject obj = new JSONObject(data);
						back.onSuccess(obj.getString("success"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	/*
	 * logout
	 * 
	 * //待补充。。。
	 */
	public void logout(final CallBack<UserInfo> back) {

		addParam("act", "logout");

		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				// 待补充。。。
			}
		});
	}

	/*
	 * public_profile
	 * 
	 * GET
	 * 
	 * 查看他人资料或者积分等。
	 */
	public void publicProfile(String username, final CallBack<SweepVo> back) {

		addParam("act", "public_profile");

		addParam("username", username);

		doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				try {
					String userinfo = new JSONObject(data)
							.getString("userinfo");
					UserInfo info = JSON.parseObject(userinfo, UserInfo.class);
					if (info == null) {
						back.onSuccess(null);
					} else {
						back.onSuccess(new SweepVo(info));
					}
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});
	}

	/**
	 * 查看个人资料。需要发送cookie登陆 accountProfile
	 * 
	 */
	public void accountProfile(String username, final CallBack<UserInfo> back) {

		addParam("act", "accountProfile");

		addParam("username", username);

		doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				try {
					String userinfo = new JSONObject(data)
							.getString("userinfo");

					UserInfo info = JSON.parseObject(userinfo, UserInfo.class);

					if (info != null) {
						if (back != null)
							back.onSuccess(info);
					} else {
						showAnalyticalException(back);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});
	}

	/*
	 * editPassword
	 * 
	 * password 是 旧密码 newpassword 是 新密码
	 * 
	 * 修改密码。需要登陆
	 * 
	 * POST
	 */
	public void editPassword(String password, String newpassword,
			final CallBack<UserInfo> back) {

		addParam("act", "editPassword");

		addParam("password", password);
		addParam("newpassword", newpassword);

		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				// 待补充。。。
			}
		});
	}

	/*
	 * mobile 是 用户名 mobile_verify 是 短信验证码
	 * 
	 * POST
	 */
	public void publicForgetPasswordMobile(String mobile, String mobile_verify,
			final CallBack<Void> back) {

		addParam("act", "public_forget_password_mobile");

		addParam("mobile", mobile);
		addParam("mobile_verify", mobile_verify);

		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				if (back != null)
					back.onSuccess(null);
			}
		});
	}

	/*
	 * mobile 是 用户名 mobile_verify 是 短信验证码
	 * 
	 * POST
	 */
	public void publicForgetPasswordMobile2(String newpassword,
			final CallBack<Void> back) {

		addParam("act", "public_forget_password_mobile2");

		addParam("newpassword", md5(newpassword));

		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				if (back != null)
					back.onSuccess(null);
			}
		});
	}

	/*
	 * sex 否 birthday 否 areaid 否 address 否 nickname 否
	 * 
	 * POST
	 */
	public void accountProfileEdit(String sex, String birthday, String areaid,
			String address, String nickname, final CallBack<UserInfo> back) {

		addParam("act", "accountProfileEdit");
		Logger.d("MemberServer", "birthday = " + birthday);
		addParam("sex", sex);
		addParam("birthday", birthday);
		addParam("areaid", areaid);
		addParam("address", address);
		addParam("nickname", nickname);

		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				try {
					Logger.focus(getClass(), "[accountProfileEdit]") ;
					Logger.focus(getClass(), "[onSuccess]") ;
					String userinfo = new JSONObject(data)
							.getString("userinfo");
					Logger.focus(getClass(), "[userinfo] = " + userinfo) ;
					UserInfo info = JSON.parseObject(userinfo, UserInfo.class);
					Logger.focus(getClass(), "[info] = " + info) ;
					if (info != null) {
						if (back != null)
							back.onSuccess(info);
						UserInfoDb.INSTANCE.update(info);
					} else {
						showAnalyticalException(back);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});

	}

	public void publicCheckUsername(String username,
			final CallBack<Integer> back) {
		addParam("act", "public_check_username");

		addParam("username", username);

		doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				try {
					JSONObject obj = new JSONObject(data);
					if (back != null) {
						back.onSuccess(Integer.parseInt(obj
								.getString("isexist")));
					}
				} catch (Exception e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});
	}

	private String md5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		StringBuffer hexValue = new StringBuffer();
		try {
			byte[] byteArray = inStr.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(byteArray);
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hexValue.toString();
	}
}
