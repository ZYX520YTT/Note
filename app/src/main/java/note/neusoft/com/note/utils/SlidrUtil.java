package note.neusoft.com.note.utils;

import android.content.Context;
import android.graphics.Color;

import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;

import note.neusoft.com.note.R;

/**
 * 作者：张宇翔
 * 创建日期： by 2016/12/30 on 18:54.
 * 描述：
 */

public class SlidrUtil {
    /**
     * 获取手势返回上一页库配置
     */
    public static SlidrConfig getConfig(Context context, SlidrListener slidrListener) {
        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(context.getResources().getColor(R.color.colorPrimary))
                .secondaryColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .position(SlidrPosition.LEFT)
                .sensitivity(1f)
                .scrimColor(Color.BLACK)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(2400)
                .distanceThreshold(0.25f)
                .listener(slidrListener)
                .edge(true)
                .edgeSize(0.18f)
                .build();
        return config;
    }
}
