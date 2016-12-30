package note.neusoft.com.note.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrListener;

import butterknife.ButterKnife;
import note.neusoft.com.note.utils.SlidrUtil;

/**
 * 作者：张宇翔
 * 创建日期： by 2016/12/30 on 19:03.
 * 描述：基础锁屏界面
 */

public abstract class BaseLockActivity extends AppCompatActivity implements SlidrListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInitView();
        ButterKnife.bind(this);//调用注解框架
        initView();
        initListener();
        initData();
        Slidr.attach(this, SlidrUtil.getConfig(this, this));
    }

    public abstract void beforeInitView();

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();
    /**
     * 以下4个方法为手势返回上一页监听回调，子类如需要用到，重写即可
     */
    @Override
    public void onSlideStateChanged(int state) {

    }

    @Override
    public void onSlideChange(float percent) {

    }

    @Override
    public void onSlideOpened() {

    }

    @Override
    public void onSlideClosed() {

    }
}
