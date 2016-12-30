package note.neusoft.com.note.utils;

import android.content.Context;

import com.leo.gesturelibray.util.ConfigUtil;

/**
 * 作者：张宇翔
 * 创建日期： by 2016/12/30 on 18:53.
 * 描述：
 */

public class PasswordUtil {
    /**
     * 获取设置过的密码
     */
    public static String getPin(Context context) {
        String password = ConfigUtil.getInstance(context).getString(Contants.PASS_KEY);
        return password;
    }
}
