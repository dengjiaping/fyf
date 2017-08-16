package com.company.fyf.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.fyf.db.CommPreference;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.RubbishServer;

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

    public static void getManualOrdelPhonenum(final Context context,final View manualCallTipTv,final View callLL,final TextView callPhoneTv){
        ApptoolServer apptoolServer = new ApptoolServer() ;
        apptoolServer.rubbishSetting(new CallBack<String>() {

            @Override
            public void onSuccess(final String s) {
                super.onSuccess(s);
                manualCallTipTv.setVisibility(View.VISIBLE);
                callLL.setVisibility(View.VISIBLE);
                callPhoneTv.setText(s);

                callLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + s));
						context.startActivity(intent);
                    }
                });

            }
        });
    }





}
