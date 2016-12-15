package note.neusoft.com.note.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.AnimationsUtils;

public class EditActivity extends AppCompatActivity {

    @ViewInject(R.id.note_detail_img_button)
    private ImageView note_detail_img_button;
    @ViewInject(R.id.note_detail_menu)
    private RelativeLayout note_detail_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ViewUtils.inject(this);

        Init();
    }

    private void Init(){

        note_detail_img_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_DOWN==event.getAction()){
                    if(note_detail_menu.getVisibility()==View.GONE){
                        openMenu();
                    }else{
                        closeMenu();
                    }
                }
                return true;
            }
        });
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



}
