package com.common.utils.watcher;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by zengjing on 17/12/19.
 */

public class DecimalWatcher implements TextWatcher {
    public DecimalWatcher() {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //限制小数点为两位
        String temp = s.toString();
        int posDot = temp.indexOf(".");
        if (posDot <= 0) return;
        if (temp.length() - posDot - 1 > 2){
            s.delete(posDot + 3, posDot + 4);
        }

    }
}
