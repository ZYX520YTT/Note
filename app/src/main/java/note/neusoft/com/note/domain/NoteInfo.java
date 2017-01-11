package note.neusoft.com.note.domain;


import java.io.Serializable;

/**
 * 作者：张宇翔
 * 创建日期： by 2016/12/19 on 20:23.
 * 描述：日记的信息
 */

public class NoteInfo implements Serializable{

    private String Date;//日期
    private  String TimeId;//时间戳
    private int Color;//颜色
    private String Content;//保存的内容
    private int TitleColor;
    private float TextSize;

    public NoteInfo(){

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getTitleColor() {
        return TitleColor;
    }

    public void setTitleColor(int titleColor) {
        TitleColor = titleColor;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public String getTimeId() {
        return TimeId;
    }

    public void setTimeId(String timeId) {
        TimeId = timeId;
    }

    public float getTextSize() {
        return TextSize;
    }

    public void setTextSize(float textSize) {
        TextSize = textSize;
    }
}
