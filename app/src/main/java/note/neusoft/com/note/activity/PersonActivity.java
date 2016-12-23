package note.neusoft.com.note.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.BmobConstants;

public class PersonActivity extends Activity {

    @ViewInject(R.id.img_back)
    private ImageButton img_back;
    @ViewInject(R.id.mine_avatar)
    private CircleImageView mine_avatar;
    @ViewInject(R.id.mine_nick)
    private TextView mine_nick;
    @ViewInject(R.id.edit)
    private TextView edit;
    @ViewInject(R.id.mine_sign_relative)
    private RelativeLayout mine_sign_relative;
    @ViewInject(R.id.sign_content)
    private TextView sign_content;
    @ViewInject(R.id.rl_account)
    private RelativeLayout rl_account;
    @ViewInject(R.id.account_content)
    private TextView account_content;
    @ViewInject(R.id.rl_nick)
    private RelativeLayout rl_nick;
    @ViewInject(R.id.tv_nick)
    private TextView tv_nick;
    @ViewInject(R.id.rl_sex)
    private RelativeLayout rl_sex;
    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;
    @ViewInject(R.id.rl_email)
    private RelativeLayout rl_email;
    @ViewInject(R.id.tv_mail)
    private TextView tv_mail;
    @ViewInject(R.id.exit_btn)
    private Button exit_btn;

    private Context context;

    public static Bitmap headimage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ViewUtils.inject(this);
        context=this;

        Init();
    }

    private void Init(){
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        mine_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出选择目录框
                startActivityForResult(new Intent(context,PhotoActivity.class),1);

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到编辑页面
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(headimage!=null){
            mine_avatar.setImageBitmap(headimage);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
