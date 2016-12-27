package note.neusoft.com.note.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import note.neusoft.com.note.R;
import note.neusoft.com.note.widget.ToggleButton;

public class SettingActivity extends Activity {

    @ViewInject(R.id.img_back)
    private ImageButton img_back;
    @ViewInject(R.id.tb_lock)
    private ToggleButton tb_lock;
    @ViewInject(R.id.tb_tran)
    private ToggleButton tb_tran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ViewUtils.inject(this);
        Init();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
    }

    private void Init() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            }
        });


    }
}
