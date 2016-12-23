package note.neusoft.com.note.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import note.neusoft.com.note.R;

public class CompileActivity extends Activity {

    @ViewInject(R.id.tv_cacel)
    private TextView tv_cacel;
    @ViewInject(R.id.tv_finish)
    private TextView tv_finish;
    @ViewInject(R.id.et_nickname)
    private EditText et_nickname;
    @ViewInject(R.id.rl_sex)
    private RelativeLayout rl_sex;
    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;
    @ViewInject(R.id.rl_date)
    private RelativeLayout rl_date;
    @ViewInject(R.id.tv_date)
    private TextView tv_date;
    @ViewInject(R.id.et_personnumber)
    private EditText et_personnumber;
    @ViewInject(R.id.et_email)
    private EditText et_email;
    @ViewInject(R.id.et_signature)
    private EditText et_signature;

    OptionsPickerView optionsPickerView;
    private ArrayList<String> Sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compile);
        ViewUtils.inject(this);
        InitData();
        Init();
    }

    @Override
    public void onBackPressed() {

        if(optionsPickerView.isShowing()){
            optionsPickerView.dismiss();
            tv_sex.setTextColor(Color.parseColor("#000000"));
        }else{
            finish();
            overridePendingTransition(R.anim.out_up_in,R.anim.out_down_out);
        }

    }

    private void InitData(){
        Sex=new ArrayList<>();
        Sex.add("男");
        Sex.add("女");


        optionsPickerView=new OptionsPickerView(this);
        optionsPickerView.setPicker(Sex);
        //三级联动效果
//        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
//        //设置选择的三级单位
////        pwOptions.setLabels("省", "市", "区");
//        pvOptions.setTitle("选择城市");
//        pvOptions.setCyclic(false, true, true);
//        //设置默认选中的三级项目
//        //监听确定选择按钮
//        pvOptions.setSelectOptions(1, 1, 1);
//        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3) {
//                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(option2)
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
//                tvOptions.setText(tx);
//                vMasker.setVisibility(View.GONE);
//            }
//        });

        optionsPickerView.setTitle("选择性别");
        optionsPickerView.setCyclic(false);
        optionsPickerView.setSelectOptions(1);
        optionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String sex=Sex.get(options1);
                tv_sex.setText(sex);
                tv_sex.setTextColor(Color.parseColor("#000000"));
            }
        });




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

        rl_sex.setOnClickListener(new View.OnClickListener() {//点击进行性别选择
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
               tv_sex.setTextColor(Color.parseColor("#ff00ddff"));//对颜色进行转换
                optionsPickerView.show();
            }
        });

        rl_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){//监控/拦截/屏蔽返回键
            if(optionsPickerView.isShowing()){
                optionsPickerView.dismiss();
                tv_sex.setTextColor(Color.parseColor("#000000"));
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}
