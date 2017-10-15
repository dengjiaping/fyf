package com.company.fyf.net;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.company.fyf.FyfApp;
import com.company.fyf.dao.BannerVo;
import com.company.fyf.dao.MsgVo;
import com.company.fyf.dao.UpdateinfoVo;
import com.company.fyf.db.AreaDb;
import com.company.fyf.db.CommPreference;
import com.company.fyf.db.MemberSettingDao;
import com.company.fyf.model.MemberSetting;
import com.company.fyf.utils.CommConfig;
import com.lyx.utils.CommUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ApptoolServer extends AbstractHttpServer {
	
	public ApptoolServer() {
		super() ;
	}

	public ApptoolServer(Context context) {
		super(context) ;
	}

	@Override
	protected String getModule() {
		return "apptool";
	}

	// 启动画面管理 GET
	public void splash() {

	}

	// 上传设备信息
	public void deviceInfo() {

	}
	
	public void memberSetting(final CallBack<Void> callBack){
		addParam("act", "member_setting");
		doGet(new FilterAjaxCallBack(null) {
			public void onSuccess(String data) {
				MemberSetting memberSetting = JSON.parseObject(data, MemberSetting.class) ;
				MemberSettingDao.INSTANCE.update(memberSetting) ;
				if(callBack != null){
					callBack.onSuccess(null) ;
				}
			}
		});
	}

	// 检查更新
	public void checkUpdate(final CallBack<UpdateinfoVo> callBack) {
		
		addParam("act", "checkUpdate");
		addParam("version", CommUtil.getVersionName(FyfApp.INSTANCE));
		
		doGet(new FilterAjaxCallBack(callBack) {
			public void onSuccess(String data) {
				try {
					JSONObject jsonObj = new JSONObject(data) ;
					String updateinfo = jsonObj.getString("updateinfo") ;
					if(callBack != null){
						callBack.onSuccess(JSON.parseObject(updateinfo, UpdateinfoVo.class)) ;
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(null);
				}
			}
		});

	}

	// 分一分介绍
	public void aboutus() {
		
		addParam("act", "aboutus");
		doGet(new FilterAjaxCallBack(null) {
			public void onSuccess(String data) {
				try {
					
					JSONObject jsonObj = new JSONObject(data) ;
					String content = jsonObj.getString("content") ;
					CommPreference.INSTANCE.setAboutUsContent(content) ;
					
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(null);
				}
			}
		});

	}

	// 本站公告列表
	public void announce(String lastId,final CallBack<List<MsgVo>> callBack) {
		addParam("act", "announce");
		addParam("id", lastId);
		addParam("pagesize", CommConfig.PAGE_SIZE);
		doGet(new FilterAjaxCallBack(callBack) {
			public void onSuccess(String data) {
				try {
					JSONObject jsonObj = new JSONObject(data) ;
					List<MsgVo> msgs = JSON.parseArray(jsonObj.getJSONArray("list").toString(), MsgVo.class) ;
					callBack.onSuccess(msgs);
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(null);
				}
			}
		});

	}

	// 返回本站公告详细
	public void announceView(String id,final CallBack<MsgVo> callBack) {
		
		addParam("act", "announceView");
		addParam("id", id);
		doGet(new FilterAjaxCallBack(callBack) {
			public void onSuccess(String data) {
				try {
					MsgVo msg = JSON.parseObject(data, MsgVo.class) ;
					callBack.onSuccess(msg);
				} catch (Exception e) {
					e.printStackTrace();
					showAnalyticalException(null);
				}
			}
		});

	}

	// 首页banner
	public void banner(final CallBack<List<BannerVo>> callBack) {
		addParam("act", "banner");
		doGet(new FilterAjaxCallBack(callBack) {
			public void onSuccess(String data) {
				try {
					JSONObject jsonObj = new JSONObject(data) ;
					List<BannerVo> msgs = JSON.parseArray(jsonObj.getJSONArray("banner").toString(), BannerVo.class) ;
					callBack.onSuccess(msgs);
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(null);
				}
			}
		});
	}

	// 得到地区列表，可用于个人资料多级联动选择，然后再补充楼号等详细地址
	public void areas(final CallBack<?> back) {

		addParam("act", "areas");
		doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				try {
					
					JSONArray array = new JSONObject(data).getJSONArray("list") ;
					
					HashMap<String , String> map = new HashMap<>() ;
					
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObj = array.getJSONObject(i) ;
						map.put(jsonObj.getString("areaid"), jsonObj.getString("name")) ;
					}
					AreaDb.INSTANCE.update(map) ;
					
					if(back != null){
						back.onSuccess(null) ;
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});

	}

	public void rubbishSetting(final CallBack<String> back) {

		addParam("act", "rubbish_setting");
		doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				try {
					JSONObject jsonObject = new JSONObject(data);
					String rubbish_call_tel = jsonObject.getString("rubbish_call_tel") ;
					String rubbish_call_price_picurl = jsonObject.getString("rubbish_call_price_picurl") ;
					if(back != null){
						back.onSuccess(rubbish_call_tel + "$divide$" + rubbish_call_price_picurl) ;
					}

				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
			}
		});

	}
}
