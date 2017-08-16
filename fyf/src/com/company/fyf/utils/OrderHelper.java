package com.company.fyf.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.ImageView;

import com.company.fyf.db.CommPreference;

import java.lang.ref.WeakReference;

/**
 * Created by liyaxing on 2017/8/16.
 */
public class OrderHelper {

    private static final long TIME_INTERVAL = 20 * 1000 ;

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

    public static void doOrder(Context context,WeakReference<EditText> inputEtReference,WeakReference<ImageView>  submitBtnReference) {

        EditText inputEt = inputEtReference.get() ;
        if(inputEt == null){
            return;
        }
        String inputValue = inputEt.getText().toString() ;
        if(!FyfUtils.doCheckPhone(context,inputValue)){
            return;
        }
        mark() ;
        ImageView submitBtn = submitBtnReference.get() ;
        if(submitBtn != null) {
            WeakReference sumitBtnReference = new WeakReference<>(submitBtn) ;
            checkMark(sumitBtnReference) ;
        }
    }




}
