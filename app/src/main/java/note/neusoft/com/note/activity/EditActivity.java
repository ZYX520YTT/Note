package note.neusoft.com.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;

import note.neusoft.com.note.R;
import note.neusoft.com.note.db.NoteDatabase;
import note.neusoft.com.note.domain.NoteInfo;
import note.neusoft.com.note.utils.AnimationsUtils;
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

    private int Color;
    private int TitleColor;


    private NoteInfo noteInfo;


    private boolean isFirst=true;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ViewUtils.inject(this);

        Intent intent=getIntent();
        noteInfo= (NoteInfo) intent.getSerializableExtra("noteinfo");

        if(noteInfo!=null){
            isFirst=false;
            note_detail_edit.setText(noteInfo.getContent());
            note_detail_edit.setSelection(noteInfo.getContent().length());//设置输入框光标选中的位置，在最后
            note_detail_tv_date.setText(noteInfo.getDate());
            if(noteInfo.getColor()==editcolor[0]){
               iv_color.setImageResource(R.drawable.green);
            }else if(noteInfo.getColor()==editcolor[1]){
                iv_color.setImageResource(R.drawable.blue);
            }else if(noteInfo.getColor()==editcolor[1]){
                iv_color.setImageResource(R.drawable.purple);
            }else if(noteInfo.getColor()==editcolor[1]){
                iv_color.setImageResource(R.drawable.yellow);
            }else{
                iv_color.setImageResource(R.drawable.red);
            }
            note_detail_edit.setBackgroundColor(noteInfo.getColor());
            note_detail_titlebar.setBackgroundColor(noteInfo.getTitleColor());
            Color=noteInfo.getColor();
            TitleColor=noteInfo.getTitleColor();

            date=noteInfo.getDate();
            timeId=noteInfo.getTimeId();

        }

        Init();
    }

    @Override
    public void onBackPressed() {//当用户点击返回按钮时，弹出一个对话框，让用户选择是否保存
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
                mAlertView = new AlertView("修改", "是否保存修改？", "取消", new String[]{"确定"},
                        null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);
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
                iv_color.setImageResource(R.drawable.green);
                Color=editcolor[0];
                TitleColor=titlecolor[0];
                break;
            case R.id.note_detail_img_blue:
                note_detail_titlebar.setBackgroundColor(titlecolor[1]);
                note_detail_edit.setBackgroundColor(editcolor[1]);
                iv_color.setImageResource(R.drawable.blue);
                Color=editcolor[1];
                TitleColor=titlecolor[1];
                break;
            case R.id.note_detail_img_purple:
                note_detail_titlebar.setBackgroundColor(titlecolor[2]);
                note_detail_edit.setBackgroundColor(editcolor[2]);
                iv_color.setImageResource(R.drawable.purple);
                Color=editcolor[2];
                TitleColor=titlecolor[2];
                break;
            case R.id.note_detail_img_yellow:
                note_detail_titlebar.setBackgroundColor(titlecolor[3]);
                note_detail_edit.setBackgroundColor(editcolor[3]);
                iv_color.setImageResource(R.drawable.yellow);
                Color=editcolor[3];
                TitleColor=titlecolor[3];
                break;
            case R.id.note_detail_img_red:
                note_detail_titlebar.setBackgroundColor(titlecolor[4]);
                note_detail_edit.setBackgroundColor(editcolor[4]);
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
            }else{
                if(isFirst){
                    Toast.makeText(EditActivity.this,"保存内容不能为空!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditActivity.this,"修改内容不能为空!",Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        }
    }

    @Override
    public void onDismiss(Object o) {

    }
}
