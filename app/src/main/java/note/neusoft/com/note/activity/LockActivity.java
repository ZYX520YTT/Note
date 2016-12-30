package note.neusoft.com.note.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.view.CustomLockView;

import butterknife.Bind;
import note.neusoft.com.note.MainActivity;
import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.Contants;
import note.neusoft.com.note.utils.PasswordUtil;

/**
 * 作者：张宇翔
 * 创建日期： by 2016/12/30 on 19:05.
 * 描述：四种关于锁屏的方式：1.设置密码，2，验证密码，3，修改密码，4，清除密码
 * //其他界面进入这里时，可以选择几种方式：
 * 1：actionSecondActivity(LockMode.CLEAR_PASSWORD);//清除密码
 *
 * 2：actionSecondActivity(LockMode.EDIT_PASSWORD);//修改密码
 *
 * 3:actionSecondActivity(LockMode.SETTING_PASSWORD);//设置密码
 *
 * 4:actionSecondActivity(LockMode.VERIFY_PASSWORD);//验证密码
 *
 *
 *
 *    跳转方法：
 *    private void actionSecondActivity(LockMode mode) {
 *        if (mode != LockMode.SETTING_PASSWORD) {
 *                if (StringUtils.isEmpty(PasswordUtil.getPin(this))) {
 *                 Toast.makeText(getBaseContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
 *                 return;
 *            }
 *         }
 *        Intent intent = new Intent(this, SecondActivity.class);
 *        intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
 *       startActivity(intent);
 *     }
 */

public class LockActivity extends BaseLockActivity implements RippleView.OnRippleCompleteListener{

    @Bind(R.id.title)
    FrameLayout title;
    @Bind(R.id.rv_back)
    RippleView rvBack;
    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.lv_lock)
    CustomLockView lvLock;
    @Bind(R.id.tv_hint)
    TextView tvHint;
    private LockMode lockMode;
    private boolean isFirst;


    @Override
    public void beforeInitView() {
        setContentView(R.layout.activity_lock);
    }

    /**
     * 初始化View
     */
    @Override
    public void initView() {
        //显示绘制方向
        lvLock.setShow(true);
        //允许最大输入次数
        lvLock.setErrorNumber(3);
        //密码最少位数
        lvLock.setPasswordMinLength(4);
        //编辑密码或设置密码时，是否将密码保存到本地，配合setSaveLockKey使用
        lvLock.setSavePin(true);
        //保存密码Key
        lvLock.setSaveLockKey(Contants.PASS_KEY);
    }

    /**
     * 设置监听回调
     */
    @Override
    public void initListener() {
        lvLock.setOnCompleteListener(onCompleteListener);
        rvBack.setOnRippleCompleteListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        //设置模式
        lockMode = (LockMode) getIntent().getSerializableExtra(Contants.INTENT_SECONDACTIVITY_KEY);
        isFirst = getIntent().getBooleanExtra("isFirst",false);
        if(isFirst){
            title.setVisibility(View.INVISIBLE);
        }else{
            title.setVisibility(View.VISIBLE);
        }
        setLockMode(lockMode);
    }


    /**
     * 密码输入模式
     */
    private void setLockMode(LockMode mode, String password, String msg) {
        lvLock.setMode(mode);
        lvLock.setErrorNumber(3);
        lvLock.setClearPasssword(false);
        if (mode != LockMode.SETTING_PASSWORD) {
            tvHint.setText("请输入已经设置过的密码");
            lvLock.setOldPassword(password);
        } else {
            tvHint.setText("请输入要设置的密码");
        }
        tvText.setText(msg);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(lockMode==LockMode.VERIFY_PASSWORD){//验证密码
                if(isFirst){
                    Intent intent=new Intent(LockActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
                }else{
                    Intent intent=new Intent(LockActivity.this,UpdateLockActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);

                }
            }else if(lockMode==LockMode.SETTING_PASSWORD){//设置密码
                finish();
                overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            }else if(lockMode==LockMode.EDIT_PASSWORD){//修改密码成功，返回
                finish();
                overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            }
        }
    };


    /**
     * 密码输入监听
     */
    CustomLockView.OnCompleteListener onCompleteListener = new CustomLockView.OnCompleteListener() {
        @Override
        public void onComplete(String password, int[] indexs) {
            tvHint.setText(getPassWordHint());
            handler.sendEmptyMessageDelayed(0,1000);
//            finish();
            //成功后退出界面，这里来一个延时，用户体验更好一些

        }

        @Override
        public void onError(String errorTimes) {
            tvHint.setText("密码错误，还可以输入" + errorTimes + "次");
        }

        @Override
        public void onPasswordIsShort(int passwordMinLength) {
            tvHint.setText("密码不能少于" + passwordMinLength + "个点");
        }

        @Override
        public void onAginInputPassword(LockMode mode, String password, int[] indexs) {
            tvHint.setText("请再次输入密码");
        }


        @Override
        public void onInputNewPassword() {
            tvHint.setText("请输入新密码");
        }

        @Override
        public void onEnteredPasswordsDiffer() {
            tvHint.setText("两次输入的密码不一致");
        }

        @Override
        public void onErrorNumberMany() {
            tvHint.setText("密码错误次数超过限制，不能再输入");
        }

    };


    /**
     * 密码相关操作完成回调提示
     */
    private String getPassWordHint() {
        String str = null;
        switch (lvLock.getMode()) {
            case SETTING_PASSWORD:
                str = "密码设置成功";
                break;
            case EDIT_PASSWORD:
                str = "密码修改成功";
                break;
            case VERIFY_PASSWORD:
                str = "密码正确";
                break;
            case CLEAR_PASSWORD:
                str = "密码已经清除";
                break;
        }
        return str;
    }

    /**
     * 设置解锁模式
     */
    private void setLockMode(LockMode mode) {
        String str = "";
        switch (mode) {
            case CLEAR_PASSWORD:
                str = "清除密码";
                setLockMode(LockMode.CLEAR_PASSWORD, PasswordUtil.getPin(this), str);
                break;
            case EDIT_PASSWORD:
                str = "修改密码";
                setLockMode(LockMode.EDIT_PASSWORD, PasswordUtil.getPin(this), str);
                break;
            case SETTING_PASSWORD:
                str = "设置密码";
                setLockMode(LockMode.SETTING_PASSWORD, null, str);
                break;
            case VERIFY_PASSWORD:
                str = "验证密码";
                setLockMode(LockMode.VERIFY_PASSWORD, PasswordUtil.getPin(this), str);
                break;
        }
        tvText.setText(str);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onComplete(RippleView rippleView) {
        onBackPressed();
    }

}
