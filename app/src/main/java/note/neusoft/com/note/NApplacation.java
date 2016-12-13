package note.neusoft.com.note;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 张宇翔 on 2016/12/13 20:28.
 * Email：1124751755@qq.com
 * 功能：
 */

public class NApplacation extends Application {
    public static String user_number;//用户的手机号码
    public static String user_power;//用户的手机号码
    public static String ImageUrl;//用户用第三方登录时候的头像Url
    public static String nickname;//用户用第三方登录时候的昵称



    private static NApplacation application;
    private static int mainTid;
    private static Handler handler;

    private List AllAcivity;





    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        AllAcivity = new ArrayList<Map<String, String>>();
        application=this;
        mainTid = android.os.Process.myTid();
        handler=new Handler();
    }



    public static Context getApplication() {
        return application;
    }
    public static int getMainTid() {
        return mainTid;
    }
    public static Handler getHandler() {
        return handler;
    }

    public   void addActivity(Activity activity) {
        try {
            AllAcivity.add(activity);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public  void destoryAllActivity() {
        try {
            for (int i = 0; i < AllAcivity.size(); i++) {
                ((Activity) AllAcivity.get(i)).finish();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
