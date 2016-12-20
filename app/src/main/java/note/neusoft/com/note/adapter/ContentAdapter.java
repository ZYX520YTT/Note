package note.neusoft.com.note.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import note.neusoft.com.note.R;
import note.neusoft.com.note.domain.NoteInfo;

/**
 * 作者：张宇翔
 * 创建日期： by 2016/12/20 on 16:24.
 * 描述：
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<NoteInfo> noteInfos;

    public ContentAdapter(Context context, ArrayList<NoteInfo> noteinfos){
        this.context=context;
        this.noteInfos=noteinfos;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.content_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NoteInfo noteInfo=noteInfos.get(position);
        holder.tv_note_detail.setText(noteInfo.getContent());
        holder.tv_note_detail.setBackgroundColor(noteInfo.getColor());

        holder.note_detail_titlebar.setBackgroundColor(noteInfo.getTitleColor());
        holder.note_detail_tv_date.setText(noteInfo.getDate());
        int[] editcolor = new int[]{0xffe5fce8,// 绿色
                0xffccf2fd,//蓝色
                0xfff7f5f6,// 紫色
                0xfffffdd7,// 黄色
                0xffffddde,// 红色
        };

        if(noteInfo.getColor()==editcolor[0]){
            holder.iv_color.setImageResource(R.drawable.green);
        }else if(noteInfo.getColor()==editcolor[1]){
            holder.iv_color.setImageResource(R.drawable.blue);
        }else if(noteInfo.getColor()==editcolor[1]){
            holder.iv_color.setImageResource(R.drawable.purple);
        }else if(noteInfo.getColor()==editcolor[1]){
            holder.iv_color.setImageResource(R.drawable.yellow);
        }else{
            holder.iv_color.setImageResource(R.drawable.red);
        }
    }

    @Override
    public int getItemCount() {
        return noteInfos.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_note_detail;
        private RelativeLayout note_detail_titlebar;
        private TextView note_detail_tv_date;
        private ImageView iv_color;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_note_detail= (TextView) itemView.findViewById(R.id.tv_note_detail);
            note_detail_titlebar= (RelativeLayout) itemView.findViewById(R.id.note_detail_titlebar);
            note_detail_tv_date= (TextView) itemView.findViewById(R.id.note_detail_tv_date);
            iv_color= (ImageView) itemView.findViewById(R.id.iv_color);
        }
    }
}
