package com.common.utils.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 输入身份证号码的时候，改为 “370625 19690214 8532” 中间带两个空格的格式
 * Created by wuyq on 2015-04-01 下午2:17.
 */
public class IdentityWatcher implements TextWatcher {
    private EditText text;

    public IdentityWatcher(EditText text) {
        this.text = text;
    }

    public IdentityWatcher() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == null || s.length() == 0) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 6 && i != 15 && s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 7 || sb.length() == 16) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if(!sb.toString().equals(s.toString())){
            s.replace(0, s.length(), sb.toString());
        }
    }
}
