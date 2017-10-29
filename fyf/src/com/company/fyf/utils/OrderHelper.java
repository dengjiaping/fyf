package com.company.fyf.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.fyf.R;
import com.company.fyf.db.CommPreference;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.RubbishServer;
import com.lyx.utils.ImageLoaderUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by liyaxing on 2017/8/16.
 */
public class OrderHelper {

    private static final long TIME_INTERVAL = 5 * 60 * 1000 ;

    public static void mark(){
        CommPreference.INSTANCE.setOrderMark();
    }

    public static void checkMark(WeakReference<ImageView> submitBtnReference){
        Logger.d("OrderHelper","checkMark");
        ImageView imageView = submitBtnReference.get() ;
        Logger.d("OrderHelper","checkMark imageView = " + imageView);
        if(imageView == null) return;
        long orderMarkTime = CommPreference.INSTANCE.getOrderMark() ;
        long timeInterval = System.currentTimeMillis() - orderMarkTime ;
        if( timeInterval > TIME_INTERVAL){
            imageView.setEnabled(true);
        }else {
            imageView.setEnabled(false);
            checkMarkDelay(submitBtnReference,TIME_INTERVAL - timeInterval) ;
        }
    }

    public static void checkMarkDelay(final WeakReference<ImageView> submitBtnReference,final long delayTime){
        Handler mainHandler = new Handler(Looper.getMainLooper()) ;
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkMark(submitBtnReference) ;
            }
        },delayTime) ;
    }

    public static void doOrder(final Context context,WeakReference<EditText> inputEtReference,final WeakReference<ImageView>  submitBtnReference) {

        EditText inputEt = inputEtReference.get() ;
        if(inputEt == null){
            return;
        }
        String inputValue = inputEt.getText().toString() ;
        if(!FyfUtils.doCheckPhone(context,inputValue)){
            return;
        }
        RubbishServer rubbishServer = new RubbishServer(context) ;
        rubbishServer.callAdd(inputValue, new CallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                mark() ;
                ImageView submitBtn = submitBtnReference.get() ;
                if(submitBtn != null) {
                    WeakReference sumitBtnReference = new WeakReference<>(submitBtn) ;
                    checkMark(sumitBtnReference) ;
                    Toast.makeText(context, "预约成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void getManualOrdelPhonenum(final Context context, final View manualCallTipTv, final View callLL, final TextView callPhoneTv, final ImageView priceImage, final LinearLayout priceList){
        ApptoolServer apptoolServer = new ApptoolServer() ;
        apptoolServer.rubbishSetting2(new CallBack<JSONObject>() {

            @Override
            public void onSuccess(final JSONObject jsonObject) {
                super.onSuccess(jsonObject);

                try {
                    final String tel = jsonObject.getString("rubbish_call_tel") ;
                    final String pricePicurl = jsonObject.getString("rubbish_call_price_picurl") ;
                    final JSONArray priceArray = jsonObject.getJSONArray("rubbish_call_price") ;

                    priceImage.setVisibility(View.VISIBLE);
                    if(priceImage != null && !TextUtils.isEmpty(pricePicurl)){
                        ImageLoaderUtils.displayPicWithAutoStretch(pricePicurl,priceImage);
                    }

                    manualCallTipTv.setVisibility(View.VISIBLE);
                    callLL.setVisibility(View.VISIBLE);
                    priceList.setVisibility(View.VISIBLE);
                    callPhoneTv.setText(tel);

                    for (int i = 0;i < priceArray.length(); i++){
                        LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.i_f01_viewpager_order_ll_price, priceList, true);
                        view = (LinearLayout) view.getChildAt(i);
                        JSONObject o = priceArray.getJSONObject(i) ;
                        String name = o.getString("name");
                        String price = o.getString("price") + "/" + o.getString("unit") ;
                        TextView nameView = (TextView) view.getChildAt(0);
                        nameView.setText(name);
                        TextView priceView = (TextView) view.getChildAt(1);
                        priceView.setText(price);
                    }

                    TextView remarkView = new TextView(context) ;
                    remarkView.setText("备注：以当天市场回收价格为准");
                    remarkView.setTextColor(Color.rgb(160,160,160));
                    remarkView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                    remarkView.setPadding(0,5,0,0);
                    priceList.addView(remarkView);

                    callLL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                                context.startActivity(intent);
                            } catch (SecurityException e) {
                                e.printStackTrace();
                                Toast.makeText(context,"请设置电话权限",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void getManualOrdelPhonenum(final Context context,final View manualCallTipTv,final View callLL,final TextView callPhoneTv,final ImageView priceImage){
        ApptoolServer apptoolServer = new ApptoolServer() ;
        apptoolServer.rubbishSetting(new CallBack<String>() {

            @Override
            public void onSuccess(final String s) {
                super.onSuccess(s);
                if(TextUtils.isEmpty(s)){
                    return;
                }
                String[] strings = s.split("\\$divide\\$");

                final String tel = strings[0] ;
                final String pricePicurl = strings[1];

                if(priceImage != null && !TextUtils.isEmpty(pricePicurl)){
                    ImageLoaderUtils.displayPicWithAutoStretch(pricePicurl,priceImage);
                }

                manualCallTipTv.setVisibility(View.VISIBLE);
                callLL.setVisibility(View.VISIBLE);
                callPhoneTv.setText(tel);

                callLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                            context.startActivity(intent);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                            Toast.makeText(context,"请设置电话权限",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }





}
