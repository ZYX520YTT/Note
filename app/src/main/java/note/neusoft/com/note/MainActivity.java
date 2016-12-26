package note.neusoft.com.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import note.neusoft.com.note.activity.AboutActivity;
import note.neusoft.com.note.activity.BaseActivity;
import note.neusoft.com.note.activity.EditActivity;
import note.neusoft.com.note.activity.PersonActivity;
import note.neusoft.com.note.activity.SettingActivity;
import note.neusoft.com.note.activity.SkinActivity;
import note.neusoft.com.note.adapter.ContentAdapter;
import note.neusoft.com.note.db.NoteDatabase;
import note.neusoft.com.note.domain.NoteInfo;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private NavigationView nav_view;
    private LinearLayout ll_head;

    private Context context;

    private RecyclerView review;
    private ContentAdapter adapter;
    private ArrayList<NoteInfo> noteInfos;
    private NoteDatabase db;

    private boolean isCoulm;//判断是否是2列或者1列，true表示2列false表示1列


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;


        fab = (FloatingActionButton) findViewById(R.id.fab);
        nav_view= (NavigationView) findViewById(R.id.nav_view);

        View headerView = nav_view.getHeaderView(0);//先获取头布局
        ll_head= (LinearLayout) headerView.findViewById(R.id.ll_head);//接下来再找ID
        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PersonActivity.class));
                overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
            }
        });


        review = (RecyclerView) findViewById(R.id.review);
        isCoulm = true;

        //点击添加按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, EditActivity.class));
                overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        db = new NoteDatabase(context);
        noteInfos = db.finAll();

        adapter = new ContentAdapter(context, noteInfos);
        review.setLayoutManager(new GridLayoutManager(context, 2));
        review.setAdapter(adapter);
        review.setItemAnimator(null);


        ItemTouchHelper helper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags;
                if(isCoulm){
                     dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//拖拽
                }else{
                    dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
                }
                return makeMovementFlags(dragFlags,0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(noteInfos,viewHolder.getAdapterPosition(),target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }




        });

        helper.attachToRecyclerView(review);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (noteInfos != null) {
            noteInfos.clear();
        }
        noteInfos = db.finAll();
        if (adapter != null) {
            adapter = null;
        }
        adapter = new ContentAdapter(context, noteInfos);
        review.setAdapter(adapter);
    }


    private boolean isExit=false;//判读是否要退出当前程序

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (isExit) {
                super.onBackPressed();
                ((NApplacation)this.getApplication()).destoryAllActivity();
                finish();
                // System.exit(0);
            } else {
                isExit = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit=false;
                    }
                },2000);
                Toast.makeText(getApplicationContext(), "再点击一次退出程序", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//        }

        if (id == R.id.action_colunm) {
//            Toast.makeText(context, "列表", Toast.LENGTH_SHORT).show();
            if (isCoulm) {
                review.setLayoutManager(new GridLayoutManager(context, 1));
                adapter.notifyDataSetChanged();
                isCoulm = false;
            } else {
                review.setLayoutManager(new GridLayoutManager(context, 2));
                adapter.notifyDataSetChanged();
                isCoulm = true;
            }
            return true;
        } else if (id == R.id.action_sort) {
//            Toast.makeText(context, "排序", Toast.LENGTH_SHORT).show();
//            ArrayList<NoteInfo> noteInfos1=new ArrayList<>();
//            for(int i=noteInfos.size()-1;i>=0;i--)
//                noteInfos1.add(noteInfos.get(i));
            Collections.reverse(noteInfos);//反转结合，进行排序
//            if (adapter != null) {
//                adapter = null;
//            }
//            adapter = new ContentAdapter(context, noteInfos);
//            review.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {//进入首页
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_skin) {//进入更换皮肤页
            startActivity(new Intent(context, SkinActivity.class));
            overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
        } else if (id == R.id.nav_setting) {//进入设置页面
            startActivity(new Intent(context, SettingActivity.class));
            overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
        } else if (id == R.id.nav_share) {//弹出分享页面

        } else if (id == R.id.nav_about) {//弹出关于APP介绍的页面
            startActivity(new Intent(context, AboutActivity.class));
            overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
        } else if (id == R.id.goout) {//退出程序
            ((NApplacation) this.getApplication()).destoryAllActivity();
        }

        return true;
    }

}
