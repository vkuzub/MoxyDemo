package com.moxydemo.base;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;


/**
 * Created by Vyacheslav on 12.09.2016.
 */
public interface TextInputLayoutSupport {

    void showTextInputLayoutError(@NonNull TextInputLayout til, @NonNull String msg);

    void showTextInputLayoutSuccess(@NonNull TextInputLayout til);
}
