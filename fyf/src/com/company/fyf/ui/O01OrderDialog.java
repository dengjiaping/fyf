package com.company.fyf.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.db.CommPreference;
import com.company.fyf.utils.OrderHelper;

import java.lang.ref.WeakReference;

/**
 * Created by liyaxing on 2017/8/15.
 */
public class O01OrderDialog extends B03BaseDialog implements View.OnClickListener{

    private View closeView;
    private EditText inputEt;
    private ImageView submitBtn;
    private View callLL ;
    private View manualCallTipTv;
    private TextView callPhoneTv;

    private Context context ;

    public O01OrderDialog(Context context) {
        super(context);
        this.context = context ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_o01_layout);
        initView() ;
    }

    private void initView() {

        closeView = findViewById(R.id.closeView) ;
        closeView.setOnClickListener(this);
        inputEt = (EditText) findViewById(R.id.inputEt);
        String phone = CommPreference.INSTANCE.getUserInfo().getUsername() ;
        inputEt.setText(phone);
        submitBtn = (ImageView) findViewById(R.id.submitBtn);
        OrderHelper.checkMark(new WeakReference<>(submitBtn));
        submitBtn.setOnClickListener(this);
        callLL = findViewById(R.id.callLL)  ;
        manualCallTipTv = findViewById(R.id.manualCallTipTv) ;
        callPhoneTv = (TextView) findViewById(R.id.callPhoneTv);
        OrderHelper.getManualOrdelPhonenum(getContext(),manualCallTipTv,callLL,callPhoneTv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.closeView:
                dismiss();
                break;
            case R.id.submitBtn:
                doOrder() ;
                break;
//            case R.id.callLL:
//                doCall() ;
//                break;
        }
    }

    private void doCall() {
//        FyfUtils.doTelAction(getContext());
    }

    private void doOrder() {
        OrderHelper.doOrder(context,new WeakReference<>(inputEt),new WeakReference<>(submitBtn));
    }
}
