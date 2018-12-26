package com.common.utils.watcher;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by zengjing on 17/12/15.
 */

public class BankWatcher  implements TextWatcher {


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == null || s.length() == 0) return;
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < s.length(); i++) {
//            if (i != 4 && i != 9 && i != 14 && s.charAt(i) == ' ') {
//                continue;
//            } else {
//                sb.append(s.charAt(i));
//                if ((sb.length() == 5 || sb.length() == 10 || sb.length() == 15) && sb.charAt(sb.length() - 1) != ' ') {
//                    sb.insert(sb.length() - 1, ' ');
//                }
//            }
//        }
//        if(!sb.toString().equals(s.toString())){
//            s.replace(0, s.length(), sb.toString());
//        }
        String temp = s.toString();
        s.clear();
        s.append(temp.replaceAll("(.{4})", "$1 ").trim());
    }
}
