package note.neusoft.com.note.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.util.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.Contants;
import note.neusoft.com.note.utils.PasswordUtil;
import note.neusoft.com.note.utils.PrefUtils;
import note.neusoft.com.note.widget.ToggleButton;

public class SettingActivity extends Activity {

    @ViewInject(R.id.img_back)
    private ImageButton img_back;
    @ViewInject(R.id.tb_lock)
    private ToggleButton tb_lock;
    @ViewInject(R.id.tb_tran)
    private ToggleButton tb_tran;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context=this;
        ViewUtils.inject(this);
        Init();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(StringUtils.isEmpty(PasswordUtil.getPin(context))){
            tb_lock.setToggleOff();
        }else{
            tb_lock.setToggleOn();
        }
    }

    private void Init() {
        //初始化开关
        if(PrefUtils.getAlphaBg(context)){
            tb_tran.setToggleOn();
        }else{
            tb_tran.setToggleOff();
        }

        if(PrefUtils.GetLock(context)){
            tb_lock.setToggleOn();
        }else{
            tb_lock.setToggleOff();
        }



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            }
        });

        tb_lock.setOnToggleChanged(new ToggleButton.OnToggleChanged() {//“用户加密”滑动监听
            @Override
            public void onToggle(boolean on) {
                PrefUtils.SetLock(context,on);//保存应用是否加密
                if(on){//加锁，进行页面跳转
                    handler.sendEmptyMessageDelayed(0,200);//延时处理一下，有交互性
                }else{//不加锁，不操作
                    return;
                }
            }
        });

        tb_tran.setOnToggleChanged(new ToggleButton.OnToggleChanged() {//“应用透明背景”滑动监听
            @Override
            public void onToggle(boolean on) {
                PrefUtils.setAlphaBg(context,on);
            }
        });

    }



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(StringUtils.isEmpty(PasswordUtil.getPin(context))){//如果没有密码，跳到设置密码页面
                actionSecondActivity(LockMode.SETTING_PASSWORD);//设置密码
                overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
            }else{
                //如果有密码，跳到是否要修改密码的界面
//                actionSecondActivity(LockMode.VERIFY_PASSWORD);//验证密码
                Intent intent=new Intent(SettingActivity.this,UpdateLockActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
            }
        }
    };

    /**
     * 跳转到密码处理界面
     */
    private void actionSecondActivity(LockMode mode) {
        if (mode != LockMode.SETTING_PASSWORD) {
            if (StringUtils.isEmpty(PasswordUtil.getPin(this))) {
                Toast.makeText(getBaseContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
        intent.putExtra("isFirst",false);//设置是否是刚进入APP
        startActivity(intent);
        overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
    }
}
