package com.company.fyf.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by liyaxing on 2017/7/19.
 */
public class B03BaseDialog extends Dialog  {

    public B03BaseDialog(Context context) {
        super(context,android.R.style.Theme_Translucent_NoTitleBar);
    }

    public B03BaseDialog(Context context, int theme) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    protected B03BaseDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
