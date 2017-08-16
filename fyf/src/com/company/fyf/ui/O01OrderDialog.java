package com.company.fyf.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.company.fyf.R;
import com.company.fyf.utils.FyfUtils;
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

    public O01OrderDialog(Context context) {
        super(context);
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
        submitBtn = (ImageView) findViewById(R.id.submitBtn);
        OrderHelper.checkMark(new WeakReference<>(submitBtn));
        submitBtn.setOnClickListener(this);
        callLL = findViewById(R.id.callLL)  ;
        callLL.setOnClickListener(this);

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
            case R.id.callLL:
                doCall() ;
                break;
        }
    }

    private void doCall() {
        FyfUtils.doTelAction(getContext());
    }

    private void doOrder() {
        OrderHelper.doOrder(getContext(),new WeakReference<>(inputEt),new WeakReference<>(submitBtn));
    }
}
