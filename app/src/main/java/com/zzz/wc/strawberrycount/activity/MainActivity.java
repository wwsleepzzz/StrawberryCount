package com.zzz.wc.strawberrycount.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zzz.wc.strawberrycount.R;
import com.zzz.wc.strawberrycount.adpter.BoxAdapter;
import com.zzz.wc.strawberrycount.box.Box;
import com.zzz.wc.strawberrycount.box.BoxDAO;
import com.zzz.wc.strawberrycount.database.BackupTask;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.zzz.wc.strawberrycount.util.Tag;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String className = "MainActivity";
    private ListView listview;
    private List<Boolean> listShow;    // 這個用來記錄哪幾個 item 是被打勾的
    private BoxDAO boxDAO;
    private List<Box> list;
    private BoxAdapter adapterItem;

    private SwipeRefreshLayout mSwipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();  //正式 TODO

        AdRequest adRequest = new AdRequest.Builder()
                . addTestDevice("8655B288AA340007F53374D93988FA4C")
                . build();  //測試

        mAdView.loadAd(adRequest);

        boxDAO =  new BoxDAO(this);
        list = getBoxData();

        Log.d(className,"onCreate");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setClass(MainActivity.this, BoxActivity.class);
                startActivity(intent);
            }
        });


        FloatingActionButton fab22 = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "dataRecover", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                dataRecover();

            }
        });

        FloatingActionButton fab33 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fab33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "dataBackupn", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                dataBackup();
            }
        });

//        boxDAO.deleteAll();

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(Color.BLUE); //重整的圖示用藍色

        for(Box box:list){
            Log.d(className," "+box.getDate());
        }

        setView2();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_dataRecover) {
            dataRecover();
            return true;
        }else if (id == R.id.action_dataBackup) {
            dataBackup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {

        Log.d(className,"onResume");
//        if(!list.isEmpty()){
//            Box deleteNumber = list.get(list.size() - 1);
//            list.remove(deleteNumber);
//            adapterItem.notifyDataSetChanged();
//        }

//        loadData();
        adapterItem.notifyDataSetChanged();
        super.onResume();
    }

    private void loadData(){
        list = getBoxData();
        adapterItem = new BoxAdapter(this, list);
        listview.setAdapter(adapterItem);
    }

    public void setView2(){
        listview = (ListView) findViewById(R.id.listview1);


        adapterItem = new BoxAdapter(this, list);
        listview.setAdapter(adapterItem);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Log.d(className,"listview click");

                Box box = list.get(position);
                if(box == null || box.getDate()==null)
                    return;

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(Tag.BundleKey.DATE, boxDAO.converToString(box.getDate())); //將Bundle物件assign給intent
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, BoxActivity.class);
                startActivity(intent);

            }
        });

//        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                openActionDlg(position);
//                return false;
//            }
//        });
    }

    /**
     * 資料恢復
     */
    private void dataRecover() {
        //TODO Auto-generated method stub
        new BackupTask(this).execute("restroeDatabase");
    }


    /**
     * 資料備份
     */
    private void dataBackup() {
        //TODO Auto-generated method stub
        new BackupTask(this).execute("backupDatabase");
    }



    /**
     *
     * @return
     */
    public List getBoxData(){

        return boxDAO.getAll();
    }

    @Override
    public void onRefresh() {
        listview.setAdapter(null); //不显示listview
        list.clear(); //list集合清空，
        new newsThread().start(); //启动线程加载数据
    }

    private class newsThread extends Thread {
        @Override
        public void run() {
            super.run();
            list = getBoxData();
            Message msg = new Message();
            msg.what = 0x12;
            mHandler.sendMessage(msg);
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0x12) {
                mSwipeLayout.setRefreshing(false); //关闭刷新

                adapterItem = new BoxAdapter(MainActivity.this, list);
                listview.setAdapter(adapterItem);

            }
            return false;
        }
    });
}
