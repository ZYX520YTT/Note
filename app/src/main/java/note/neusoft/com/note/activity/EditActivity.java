package note.neusoft.com.note.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;

import note.neusoft.com.note.R;
import note.neusoft.com.note.db.NoteDatabase;
import note.neusoft.com.note.domain.NoteInfo;
import note.neusoft.com.note.utils.AnimationsUtils;
import note.neusoft.com.note.widget.InputMethodLayout;
import note.neusoft.com.note.widget.NoteItemCircleView;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, OnDismissListener {

    @ViewInject(R.id.note_detail_img_button)
    private ImageView note_detail_img_button;
    @ViewInject(R.id.note_detail_menu)
    private RelativeLayout note_detail_menu;
    @ViewInject(R.id.note_detail_img_green)
    private NoteItemCircleView note_detail_img_green;
    @ViewInject(R.id.note_detail_img_blue)
    private NoteItemCircleView note_detail_img_blue;
    @ViewInject(R.id.note_detail_img_purple)
    private NoteItemCircleView note_detail_img_purple;
    @ViewInject(R.id.note_detail_img_yellow)
    private NoteItemCircleView note_detail_img_yellow;
    @ViewInject(R.id.note_detail_img_red)
    private NoteItemCircleView note_detail_img_red;
    @ViewInject(R.id.note_detail_titlebar)//标题
    private RelativeLayout note_detail_titlebar;
    @ViewInject(R.id.note_detail_edit)//输入框
    private EditText note_detail_edit;
    @ViewInject(R.id.iv_color)//一个钉子的图片
    private ImageView iv_color;
    @ViewInject(R.id.note_detail_tv_date)
    private TextView note_detail_tv_date;
    @ViewInject(R.id.rl_edit)
    private InputMethodLayout rl_edit;
    @ViewInject(R.id.menu_item_text_font)
    private FloatingActionButton menu_item_text_font;
    @ViewInject(R.id.ll_font_small)
    private FrameLayout ll_font_small;
    @ViewInject(R.id.ll_font_normal)
    private FrameLayout ll_font_normal;
    @ViewInject(R.id.ll_font_large)
    private FrameLayout ll_font_large;
    @ViewInject(R.id.ll_font_super)
    private FrameLayout ll_font_super;
    @ViewInject(R.id.iv_small_select)
    private ImageView iv_small_select;
    @ViewInject(R.id.iv_medium_select)
    private ImageView iv_medium_select;
    @ViewInject(R.id.iv_large_select)
    private ImageView iv_large_select;
    @ViewInject(R.id.iv_super_select)
    private ImageView iv_super_select;

    @ViewInject(R.id.font_size_selector)
    private LinearLayout font_size_selector;


    @ViewInject(R.id.floating_action_menu)
    private FloatingActionMenu floating_action_menu;//红圆圈视图


    private int Color;
    private int TitleColor;

    private float textsize=14;


    private NoteInfo noteInfo;


    private boolean isFirst=true;//是不是处于修改状态(true表示创建一个新的，flase表示是从已经存在的进入的)




    private String ModfitytextContent;//将进来的内容赋值给这个字符串，下面用来判断是否已经修改过




    private final int[] editcolor = new int[]{0xffe5fce8,// 绿色
            0xffccf2fd,//蓝色
            0xfff7f5f6,// 紫色
            0xfffffdd7,// 黄色
            0xffffddde,// 红色
    };


    private final int[] titlecolor = new int[]{0xffcef3d4,// 绿色
            0xffa9d5e2,// 蓝色
            0xffddd7d9,// 紫色
            0xffebe5a9,// 黄色
            0xffecc4c3,// 红色
    };
    private AlertView mAlertView;
    private String date;
    private String timeId;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ViewUtils.inject(this);
        context=this;
        Intent intent=getIntent();
        noteInfo= (NoteInfo) intent.getSerializableExtra("noteinfo");

        if(noteInfo!=null){
            isFirst=false;
            note_detail_edit.setText(noteInfo.getContent());
//            note_detail_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//            ChoseTextSize(1);//默认为普通字体
            System.out.println("字体大小为："+noteInfo.getTextSize());
            if(noteInfo.getTextSize()==12){
                ChoseTextSize(0);
            }else if(noteInfo.getTextSize()==14){
                ChoseTextSize(1);
            }else if(noteInfo.getTextSize()==20){
                ChoseTextSize(2);
            }else if(noteInfo.getTextSize()==25){
                ChoseTextSize(3);
            }


            note_detail_edit.setSelection(noteInfo.getContent().length());//设置输入框光标选中的位置，在最后
            ModfitytextContent=noteInfo.getContent();//将进来的内容赋值给这个字符串，下面用来判断是否已经修改过
            note_detail_tv_date.setText(noteInfo.getDate());
            if(noteInfo.getColor()==editcolor[0]){
               iv_color.setImageResource(R.drawable.green);
            }else if(noteInfo.getColor()==editcolor[1]){
                iv_color.setImageResource(R.drawable.blue);
            }else if(noteInfo.getColor()==editcolor[2]){
                iv_color.setImageResource(R.drawable.purple);
            }else if(noteInfo.getColor()==editcolor[3]){
                iv_color.setImageResource(R.drawable.yellow);
            }else{
                iv_color.setImageResource(R.drawable.red);
            }
            note_detail_edit.setBackgroundColor(noteInfo.getColor());
            note_detail_titlebar.setBackgroundColor(noteInfo.getTitleColor());
            rl_edit.setBackgroundColor(noteInfo.getColor());
            Color=noteInfo.getColor();
            TitleColor=noteInfo.getTitleColor();

            date=noteInfo.getDate();
            timeId=noteInfo.getTimeId();


            //Android EditText控件如何禁止往里面输入内容
//            note_detail_edit.setKeyListener(null);


        }

        Init();
    }

    @Override
    public void onBackPressed() {//当用户点击返回按钮时，弹出一个对话框，让用户选择是否保存

        if(TextUtils.isEmpty(note_detail_edit.getText().toString())){
            finish();
            overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            return;
        }

        if(isFirst){
            if(mAlertView==null){

                mAlertView = new AlertView("保存", "是否需要保存？", "取消", new String[]{"确定"},
                        null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);
            }
            if(!mAlertView.isShowing()){
                mAlertView.show();
            }
        }else{
            if(mAlertView==null){
                if(ModfitytextContent.equals(note_detail_edit.getText().toString())){
                    finish();
                    overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
                    return;
                }else{
                    mAlertView = new AlertView("修改", "是否保存修改？", "取消", new String[]{"确定"},
                            null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);
                }

            }
            if(!mAlertView.isShowing()){
                mAlertView.show();
            }
        }
    }

    private void Init() {


        if(isFirst){
            //初始化日期
            note_detail_tv_date.setText(getCurrentDate());
            Color=editcolor[0];
            TitleColor=titlecolor[0];
        }
        note_detail_menu.setVisibility(View.GONE);//初始化画板，保持关闭状态
        note_detail_img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note_detail_menu.getVisibility() == View.GONE) {
                    openMenu();
                } else {
                    closeMenu();
                }
            }
        });


        //点击那5个带颜色的图片
        note_detail_img_green.setOnClickListener(this);
        note_detail_img_blue.setOnClickListener(this);
        note_detail_img_purple.setOnClickListener(this);
        note_detail_img_yellow.setOnClickListener(this);
        note_detail_img_red.setOnClickListener(this);


        //监听软键盘的状态
        rl_edit.setOnkeyboarddStateListener(new InputMethodLayout.onKeyboardsChangeListener() {
            @Override
            public void onKeyBoardStateChange(int state) {
                switch (state) {
                    case InputMethodLayout.KEYBOARD_STATE_SHOW:
//                        Toast.makeText(context, "打开软键盘", Toast.LENGTH_SHORT).show();
                        floating_action_menu.setVisibility(View.GONE);
                        note_detail_edit.setCursorVisible(true);
                        break;
                    case InputMethodLayout.KEYBOARD_STATE_HIDE:
//                        Toast.makeText(context, "关闭软键盘", Toast.LENGTH_SHORT).show();
                        floating_action_menu.setVisibility(View.VISIBLE);
                        note_detail_edit.setCursorVisible(false);
                        break;
                }
            }
        });


        menu_item_text_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(font_size_selector.getVisibility()==LinearLayout.VISIBLE){
                    font_size_selector.setVisibility(View.GONE);
                }else{
                    font_size_selector.setVisibility(View.VISIBLE);
                }
            }
        });

        //小号字体
        ll_font_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseTextSize(0);
            }
        });
        //普通字体
        ll_font_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseTextSize(1);
            }
        });
        //大号字体
        ll_font_large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseTextSize(2);
            }
        });
        //较大字体
        ll_font_super.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseTextSize(3);
            }
        });





    }

    /**
     * 选择字体大小标识
     * @param ID
     */
    private void ChoseTextSize(int ID){
        switch (ID){
            case 0://小
                note_detail_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                textsize=12;
                iv_small_select.setVisibility(View.VISIBLE);
                iv_medium_select.setVisibility(View.GONE);
                iv_large_select.setVisibility(View.GONE);
                iv_super_select.setVisibility(View.GONE);
                break;
            case 1://普通
                note_detail_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                textsize=14;
                iv_small_select.setVisibility(View.GONE);
                iv_medium_select.setVisibility(View.VISIBLE);
                iv_large_select.setVisibility(View.GONE);
                iv_super_select.setVisibility(View.GONE);
                break;
            case 2://大
                note_detail_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                textsize=20;
                iv_small_select.setVisibility(View.GONE);
                iv_medium_select.setVisibility(View.GONE);
                iv_large_select.setVisibility(View.VISIBLE);
                iv_super_select.setVisibility(View.GONE);
                break;
            case 3://较大
                note_detail_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                textsize=25;
                iv_small_select.setVisibility(View.GONE);
                iv_medium_select.setVisibility(View.GONE);
                iv_large_select.setVisibility(View.GONE);
                iv_super_select.setVisibility(View.VISIBLE);
                break;
        }
    }


    /**
     * 切换便签颜色的菜单
     */
    private void openMenu() {
        AnimationsUtils.openAnimation(note_detail_menu, note_detail_img_button, 700);
    }

    /**
     * 切换便签颜色的菜单
     */
    private void closeMenu() {
        AnimationsUtils.closeAnimation(note_detail_menu, note_detail_img_button, 500);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_detail_img_green:
                note_detail_titlebar.setBackgroundColor(titlecolor[0]);
                note_detail_edit.setBackgroundColor(editcolor[0]);
                rl_edit.setBackgroundColor(editcolor[0]);
                iv_color.setImageResource(R.drawable.green);
                Color=editcolor[0];
                TitleColor=titlecolor[0];
                break;
            case R.id.note_detail_img_blue:
                note_detail_titlebar.setBackgroundColor(titlecolor[1]);
                note_detail_edit.setBackgroundColor(editcolor[1]);
                rl_edit.setBackgroundColor(editcolor[1]);
                iv_color.setImageResource(R.drawable.blue);
                Color=editcolor[1];
                TitleColor=titlecolor[1];
                break;
            case R.id.note_detail_img_purple:
                note_detail_titlebar.setBackgroundColor(titlecolor[2]);
                note_detail_edit.setBackgroundColor(editcolor[2]);
                rl_edit.setBackgroundColor(editcolor[2]);
                iv_color.setImageResource(R.drawable.purple);
                Color=editcolor[2];
                TitleColor=titlecolor[2];
                break;
            case R.id.note_detail_img_yellow:
                note_detail_titlebar.setBackgroundColor(titlecolor[3]);
                note_detail_edit.setBackgroundColor(editcolor[3]);
                rl_edit.setBackgroundColor(editcolor[3]);
                iv_color.setImageResource(R.drawable.yellow);
                Color=editcolor[3];
                TitleColor=titlecolor[3];
                break;
            case R.id.note_detail_img_red:
                note_detail_titlebar.setBackgroundColor(titlecolor[4]);
                note_detail_edit.setBackgroundColor(editcolor[4]);
                rl_edit.setBackgroundColor(editcolor[4]);
                iv_color.setImageResource(R.drawable.red);
                Color=editcolor[4];
                TitleColor=titlecolor[4];
                break;
            default:
                break;
        }
    }


    /**
     * 得到当前日期
     */

    public String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

    /**
     * 得到当前时间，用来做保存的ID
     */
    private  String getTimeId(){
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
        String format1 = format.format(date);
        return format1;
    }

    @Override
    public void onItemClick(Object o, int position) {
        if(position==AlertView.CANCELPOSITION){//点击了取消按钮，不保存
//            Toast.makeText(EditActivity.this,"取消",Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
        }else{//点击了确定按钮,保存
//            Toast.makeText(EditActivity.this,"确定",Toast.LENGTH_SHORT).show();
            if(!TextUtils.isEmpty(note_detail_edit.getText().toString())){
                //保存
                NoteDatabase noteDatabase=new NoteDatabase(EditActivity.this);
                if(isFirst){
                    date = getCurrentDate();
                    timeId = getTimeId();
                }
                String content = note_detail_edit.getText().toString();
                NoteInfo noteInfo= new NoteInfo();
                noteInfo.setDate(date);
                noteInfo.setTitleColor(TitleColor);
                noteInfo.setColor(Color);
                noteInfo.setTimeId(timeId);
                noteInfo.setContent(content);
                noteInfo.setTextSize(textsize);
                if(isFirst){
                    boolean insert = noteDatabase.insert(noteInfo);
                    if(insert){
                        Toast.makeText(EditActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(EditActivity.this,"保存失败！",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    noteDatabase.update(timeId,noteInfo);
                    Toast.makeText(EditActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                }
            }
            finish();
            overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
        }
    }

    @Override
    public void onDismiss(Object o) {

    }



}
