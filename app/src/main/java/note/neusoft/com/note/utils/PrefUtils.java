package note.neusoft.com.note.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：张宇翔
 * 创建日期： by 2016/12/27 on 11:25.
 * 描述：
 */

public class PrefUtils {

    public static final String PREF_NAME = "config";

    public static final String BGIMAGE="bgimage";

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }


    /**
     * 设置应用背景是否透明，ok=true表示透明，ok=false表示不透明
     * @param context
     * @param ok
     */
    public static void setAlphaBg(Context context,boolean ok){
        setBoolean(context,"AlphaBg",ok);
    }

    /**
     * 得到应用背景是否透明，true表示透明，false表示不透明(默认不透明)
     * @param context
     */
    public static boolean getAlphaBg(Context context){
        return getBoolean(context,"AlphaBg",false);
    }


    /**
     *保存皮肤
     * @param context
     * @param imageid
     */
    public static void SaveBg(Context context,int imageid){
        SharedPreferences sp=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(BGIMAGE,imageid).commit();
    }

    /**
     * 得到皮肤
     * @param context
     * @return
     */
    public static int GetBg(Context context){
        SharedPreferences sp=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sp.getInt(BGIMAGE,-1);
    }







}
