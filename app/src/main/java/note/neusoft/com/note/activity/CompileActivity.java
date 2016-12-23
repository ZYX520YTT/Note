package note.neusoft.com.note.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import note.neusoft.com.note.R;

public class CompileActivity extends Activity {

    @ViewInject(R.id.tv_cacel)
    private TextView tv_cacel;
    @ViewInject(R.id.tv_finish)
    private TextView tv_finish;
    @ViewInject(R.id.et_nickname)
    private EditText et_nickname;
    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;
    @ViewInject(R.id.tv_date)
    private TextView tv_date;
    @ViewInject(R.id.et_personnumber)
    private EditText et_personnumber;
    @ViewInject(R.id.et_email)
    private EditText et_email;
    @ViewInject(R.id.et_signature)
    private EditText et_signature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compile);
        ViewUtils.inject(this);

        Init();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.out_up_in,R.anim.out_down_out);
    }


    private void Init(){
        tv_cacel.setOnClickListener(new View.OnClickListener() {//取消编辑
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.out_up_in,R.anim.out_down_out);
            }
        });
        tv_finish.setOnClickListener(new View.OnClickListener() {//保存编辑内容
            @Override
            public void onClick(View v) {

                //先保存，然后再退出
                /****************保存************************/


                /****************保存************************/
                finish();
                overridePendingTransition(R.anim.out_up_in,R.anim.out_down_out);
            }
        });
        et_nickname.setOnClickListener(new View.OnClickListener() {//当点击了这个输入框的时候，让光标显示
            @Override
            public void onClick(View v) {
                et_nickname.setCursorVisible(true);
            }
        });


    }
}
