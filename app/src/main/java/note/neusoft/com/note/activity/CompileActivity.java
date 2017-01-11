package note.neusoft.com.note.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import note.neusoft.com.note.R;

public class CompileActivity extends Activity implements OnDismissListener, OnItemClickListener {

    @ViewInject(R.id.tv_cacel)
    private TextView tv_cacel;
    @ViewInject(R.id.tv_finish)
    private TextView tv_finish;
    @ViewInject(R.id.ll_nickname)
    private LinearLayout ll_nickname;
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
    @ViewInject(R.id.ll_personnumber)
    private LinearLayout ll_personnumber;
    @ViewInject(R.id.et_personnumber)
    private EditText et_personnumber;
    @ViewInject(R.id.ll_email)
    private LinearLayout ll_email;
    @ViewInject(R.id.et_email)
    private EditText et_email;
    @ViewInject(R.id.et_signature)
    private EditText et_signature;

    OptionsPickerView optionsPickerView;
    TimePickerView pvTime;
    private ArrayList<String> Sex;

    private AlertView mAlertViewExt_nickname,mAlertViewExt_personnumber,mAlertViewExt_Email;
    private EditText etName1,etName2,etName3;


    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compile);
        ViewUtils.inject(this);
        context=this;
        InitData();
        Init();


    }

    @Override
    public void onBackPressed() {

        if(optionsPickerView.isShowing()){
            optionsPickerView.dismiss();
            tv_sex.setTextColor(Color.parseColor("#000000"));
        }else if(mAlertViewExt_Email.isShowing()){
            mAlertViewExt_Email.dismiss();
        }else if(mAlertViewExt_nickname.isShowing()){
            mAlertViewExt_nickname.dismiss();
        }else if(mAlertViewExt_personnumber.isShowing()){
            mAlertViewExt_personnumber.dismiss();
        }else if(pvTime.isShowing()){
            pvTime.dismiss();
        }
        else{
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


        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                tv_date.setText(getTime(date));
            }
        });



        mAlertViewExt_nickname = new AlertView("提示", "请输入您的昵称！", "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, this);
        mAlertViewExt_personnumber=new AlertView("提示", "请输入您的个人账号！", "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, this);
        mAlertViewExt_Email=new AlertView("提示", "请输入您的E-Mail地址！", "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, this);

        ViewGroup extView1= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        etName1= (EditText) extView1.findViewById(R.id.etName);
        mAlertViewExt_nickname.addExtView(extView1);

        ViewGroup extView2= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        etName2= (EditText) extView2.findViewById(R.id.etName);
        etName2.setHint("请您输入个人账号");
        mAlertViewExt_personnumber.addExtView(extView2);

        ViewGroup extView3= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        etName3= (EditText) extView3.findViewById(R.id.etName);
        etName3.setHint("请输入您的E-Mail");
        mAlertViewExt_Email.addExtView(extView3);
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

        rl_sex.setOnClickListener(new View.OnClickListener() {//点击进行性别选择
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
               tv_sex.setTextColor(Color.parseColor("#ff00ddff"));//对颜色进行转换
                optionsPickerView.show();
            }
        });





//        rl_nickname.setOnClickListener(new View.OnClickListener() {//点击昵称栏，弹出输入昵称的框
//            @Override
//            public void onClick(View v) {
//                mAlertViewExt_nickname.show();
//            }
//        });
//        rl_personnumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAlertViewExt_personnumber.show();
//            }
//        });
//        rl_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAlertViewExt_Email.show();
//            }
//        });

        rl_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
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


    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {

        if(o==mAlertViewExt_nickname&&position!=AlertView.CANCELPOSITION){
            et_nickname.setText(etName1.getText().toString());
            etName1.setText("");
        }
        if(o==mAlertViewExt_Email&&position!=AlertView.CANCELPOSITION){
            et_email.setText(etName3.getText().toString());
            etName3.setText("");
        }
        if(o==mAlertViewExt_personnumber&&position!=AlertView.CANCELPOSITION){
            et_personnumber.setText(etName2.getText().toString());
            etName2.setText("");
        }
    }

    //得到对应时间
    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        //算星座
        SimpleDateFormat Moth=new SimpleDateFormat("MM");
        int moth= Integer.parseInt(Moth.format(date));
        SimpleDateFormat Day=new SimpleDateFormat("dd");
        int day= Integer.parseInt(Day.format(date));
        String Constellation=getConstellation(moth,day);
        return format.format(date)+"("+Constellation+")";
    }

    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23,
            23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座",
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
            "天蝎座", "射手座", "摩羯座" };

    /**
     * Java通过生日计算星座
     *
     * @param month
     * @param day
     * @return
     */
    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1]
                : constellationArr[month];
    }
    /**
     * 开启软键盘
     */
    private void openKeyboard(View view){
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,0);
    }

    /**
     * 关闭软键盘
     * @param view
     */
    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(view.getWindowToken(),0);
    }
}
