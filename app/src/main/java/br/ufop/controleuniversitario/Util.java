package br.ufop.controleuniversitario;

import android.text.TextUtils;
import android.widget.EditText;

public class Util {
    static public boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

}
