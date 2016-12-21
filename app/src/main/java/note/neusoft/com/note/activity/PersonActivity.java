package note.neusoft.com.note.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.lidroid.xutils.ViewUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import note.neusoft.com.note.R;

public class PersonActivity extends Activity {

    private ImageButton img_back;
    private CircleImageView mine_avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ViewUtils.inject(this);
    }
}
