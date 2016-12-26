package note.neusoft.com.note.activity;

import android.app.Activity;
import android.os.Bundle;

import note.neusoft.com.note.R;

public class SkinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
    }
}
