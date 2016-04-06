package com.company.fyf.net;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.company.fyf.dao.CreditVo;
import com.company.fyf.db.CommPreference;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.model.Credit;
import com.company.fyf.model.CreditRank;
import com.company.fyf.model.UserInfo;
import com.company.fyf.utils.CommConfig;

public class CreditServer extends AbstractHttpServer {

	public CreditServer() {
		super();
	}

	public CreditServer(Context context) {
		super(context);
	}

	@Override
	protected String getModule() {
		return "credit";
	}

	// 待改为时间戳
	public void myCreditList(String id, final CallBack<List<CreditVo>> back) {

		addParam("act", "myCreditList");

		addParam("id", id);
		addParam("pagesize", CommConfig.PAGE_SIZE);

		doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {

				try {
					List<CreditVo> list = JSON.parseArray(new JSONObject(data)
							.getJSONArray("list").toString(), CreditVo.class);

					if (back != null) {
						back.onSuccess(list);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}

			}
		});
	}

	// 积分总排行榜 GET
	public void publicCreditRankTotal(final CallBack<List<CreditRank>> back) {

		addParam("act", "public_creditRankTotal");

		doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {

				try {
					List<CreditRank> list = JSON.parseArray(
							new JSONObject(data).getJSONArray("list")
									.toString(), CreditRank.class);

					if (back != null) {
						back.onSuccess(list);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}

			}
		});
	}

	// username 是 要查看的用户名 GET
	public void publicCreditRankByUsername(String username,
			final CallBack<Long> back) {

		addParam("act", "public_creditRankByUsername");

		addParam("username", username);

		doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {

				try {
					long l = Long.parseLong(data);
					if (back != null) {
						back.onSuccess(l);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});
	}

	// 积分规则说明 GET
	public void publicCreditRule() {

		addParam("act", "public_creditRule");

		doGet(new FilterAjaxCallBack(null) {
			public void onSuccess(String data) {
				// TODO Auto-generated method stub
				try {
					String content = new JSONObject(data).getString("content");
					CommPreference.INSTANCE.setPointsRule(content);
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(null);
				}
			}
		});
	}

	/*
	 * 对某用户添加积分，普通居民无权限
	 * 
	 * creditadd 是 要增加的积分 username 是 要增加积分的用户名 reason 是 增加积分原因
	 * 
	 * POST
	 */
	public void creditAddByUsername(String creditadd, String username,
			String reason, String from, final CallBack<String> back) {

		addParam("act", "creditAddByUsername");
		addParam("creditadd", creditadd);
		addParam("username", username);
		addParam("reason", reason);
		addParam("from", from);

		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				/*
				 * 返回字段说明 credit 操作后用户现有的积分数
				 */
				try {
					String credit = new JSONObject(data).getJSONObject(
							"userinfo").getString("credit");
					if (back != null) {
						back.onSuccess(credit);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});
	}

	public void addCreditBySelf(final String num,final CallBack<String> callBack) {
		addParam("act", "addCreditBySelf");
		addParam("num", num);
		addParam("from", "useraddbyself");
		addParam("reason", "扫码积分");

		doPost(new FilterAjaxCallBack(callBack) {
			public void onSuccess(String data) {
				try {
					String userinfo = new JSONObject(data).getString("userinfo");
					UserInfo info = JSON.parseObject(userinfo, UserInfo.class);
					if (info != null) {
						if (callBack != null)
							callBack.onSuccess(num);
					} else {
						showAnalyticalException(callBack);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(callBack);
				}
			}
		});

	}

}
