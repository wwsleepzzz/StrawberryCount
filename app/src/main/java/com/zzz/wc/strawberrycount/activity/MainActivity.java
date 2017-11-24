package com.zzz.wc.strawberrycount.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zzz.wc.strawberrycount.R;
import com.zzz.wc.strawberrycount.adpter.BoxAdapter;
import com.zzz.wc.strawberrycount.box.Box;
import com.zzz.wc.strawberrycount.box.BoxDAO;
import com.zzz.wc.strawberrycount.checker.Checker;
import com.zzz.wc.strawberrycount.checker.CheckerDAO;
import com.zzz.wc.strawberrycount.database.BackupData;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.zzz.wc.strawberrycount.util.BoxUtil;
import com.zzz.wc.strawberrycount.util.Tag;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        NavigationView.OnNavigationItemSelectedListener, BackupData.OnBackupListener {

    private String className = "MainActivity";
    private ListView listview;
    private List<Boolean> listShow;    // 這個用來記錄哪幾個 item 是被打勾的
    private BoxDAO boxDAO;
    private CheckerDAO checkerDAO;
    private List<Box> boxList;
    private List<Checker> checkerList;
    private BoxAdapter adapterItem;
    private SwipeRefreshLayout mSwipeLayout;
    private BackupData backupData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        Log.d(className,"onCreate");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();  //正式 TODO
        AdRequest adRequest = new AdRequest.Builder()
                . addTestDevice("8655B288AA340007F53374D93988FA4C")
                . build();  //測試

        mAdView.loadAd(adRequest);

        boxDAO =  new BoxDAO(this);
        checkerDAO = new CheckerDAO(this);
        getData();





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setClass(MainActivity.this, BoxActivity.class);
                startActivity(intent);
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        boxDAO.deleteAll();

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(Color.BLUE); //重整的圖示用藍色


        setView();

        backupData = new BackupData(MainActivity.this);
        backupData.setOnBackupListener( this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {

        Log.d(className,"onResume");

        loadData();

        super.onResume();
    }





    private void loadData(){
        getData();
        adapterItem = new BoxAdapter(this, boxList,checkerList);
        listview.setAdapter(adapterItem);
        adapterItem.notifyDataSetChanged();
    }

    public void setView(){
        listview = (ListView) findViewById(R.id.listview1);


        adapterItem = new BoxAdapter(this, boxList,checkerList);
        listview.setAdapter(adapterItem);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Log.d(className,"listview click");

                Box box = boxList.get(position);
                if(box == null || box.getDate()==null)
                    return;

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(Tag.BundleKey.DATE, BoxUtil.converToString(box.getDate())); //將Bundle物件assign給intent
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, BoxActivity.class);
                startActivity(intent);

            }
        });

    }



    public List getBoxData(){

        return boxDAO.getAll();
    }

    @Override
    public void onRefresh() {
        listview.setAdapter(null); //不显示listview
        boxList.clear(); //list集合清空，
        new newsThread().start(); //启动线程加载数据
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_backup) {

            backupData.exportToSD();

        } else if (id == R.id.nav_recover) {
            backupData.importFromSD();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void onFinishExport(String error) {
        String notify = error;
        if (error == null) {
            notify = "Export success";
        }
        Toast.makeText(this, notify, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishImport(String error) {
        String notify = error;
        if (error == null) {
            notify = "Import success";

            loadData();
        }
        Toast.makeText(this, notify, Toast.LENGTH_SHORT).show();
    }

    private class newsThread extends Thread {
        @Override
        public void run() {
            super.run();
            getData();
            Message msg = new Message();
            msg.what = 0x12;
            mHandler.sendMessage(msg);
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0x12) {
                mSwipeLayout.setRefreshing(false); //關閉刷新

                adapterItem = new BoxAdapter(MainActivity.this, boxList,checkerList);
                listview.setAdapter(adapterItem);

            }
            return false;
        }
    });



    private void getData(){
        boxList = getBoxData();
        checkerList = checkerDAO.getAll();
    }
}
