package com.zzz.wc.strawberrycount.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zzz.wc.strawberrycount.R;
import com.zzz.wc.strawberrycount.box.Box;
import com.zzz.wc.strawberrycount.box.BoxDAO;
import com.zzz.wc.strawberrycount.util.BoxUtil;
import com.zzz.wc.strawberrycount.util.DateUtil;
import com.zzz.wc.strawberrycount.util.Tag;

import java.util.Calendar;

public class BoxActivity extends AppCompatActivity {
    private String className = "BoxActivity";
    private Button btn;
    private EditText edit_flat;
    private EditText edit_top;
    private EditText edit_black;
    private EditText edit_pinkcube;
    private EditText edit_yellowcube;
    private EditText edit_yummycube;
    private EditText edit_normalcube;
    //plus
    private EditText edit_flat_plus;
    private EditText edit_top_plus;
    private EditText edit_black_plus;
    private EditText edit_pinkcube_plus;
    private EditText edit_yellowcube_plus;
    private EditText edit_yummycube_plus;
    private EditText edit_normalcube_plus;
    private EditText edit_flat_price;
    private EditText edit_black_price;
    private EditText edit_top_price;
    private EditText edit_cube_price;

    private Button dateButton;
    private TextView dateText;

    //checker
    private EditText edit_checker_price;
    private TextView txtTime_start;
    private TextView txtTime_end;
    private ImageButton btnTime_start;
    private ImageButton btnTime_end;
    private Button btnStart_reset;
    private Button btnEnd_reset;

    private Box box;
    private Box preBox;
    private BoxDAO boxDAO;
    private int mYear, mMonth, mDay,mHour, mMinute; //DatePicker/TimePicker用



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        boxDAO = new BoxDAO(this);

        String date = null;
        Bundle extras = getIntent().getExtras();
        date = (extras == null ? date : extras.getString(Tag.BundleKey.DATE));


        if (date != null) {
            readData(date);
        } else {//建立新Box
            box = new Box();

            preBox = boxDAO.getLast(); //取最新的一筆
            if (preBox != null) { //若有帶出最近的一筆的資料
                box.setFlat(preBox.getFlat());
                box.setTop(preBox.getTop());
                box.setBlack(preBox.getBlack());
                box.setCubePink(preBox.getCubePink());
                box.setCubeYellow(preBox.getCubeYellow());
                box.setCubeYummy(preBox.getCubeYummy());
                box.setCubeNormal(preBox.getCubeNormal());

                box.setPrint_black(preBox.getPrint_black());
                box.setPrint_cube(preBox.getPrint_cube());
                box.setPrint_flat(preBox.getPrint_flat());
                box.setPrint_top(preBox.getPrint_top());
                box.setDate(DateUtil.getToday());
                box.setCheckerPrice(preBox.getCheckerPrice());
                putToUI(box);

            } else { //若沒有給預設值
                box.setDate(DateUtil.getToday());
                box.setPrint_black(0.25);
                box.setPrint_cube(0.16);
                box.setPrint_flat(0.14);
                box.setPrint_top(0.2);
                box.setCheckerPrice(20.41);
                putToUI(box);
            }

        }


        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                int flat = Integer.parseInt(handleEditText(edit_flat));
                int top = Integer.parseInt(handleEditText(edit_top));
                int black = Integer.parseInt(handleEditText(edit_black));
                int pink = Integer.parseInt(handleEditText(edit_pinkcube));
                int yellow = Integer.parseInt(handleEditText(edit_yellowcube));
                int yummy = Integer.parseInt(handleEditText(edit_yummycube));
                int normal = Integer.parseInt(handleEditText(edit_normalcube));

                int flat_plus = Integer.parseInt(handleEditText(edit_flat_plus));
                int top_plus = Integer.parseInt(handleEditText(edit_top_plus));
                int black_plus = Integer.parseInt(handleEditText(edit_black_plus));
                int pink_plus = Integer.parseInt(handleEditText(edit_pinkcube_plus));
                int yellow_plus = Integer.parseInt(handleEditText(edit_yellowcube_plus));
                int yummy_plus = Integer.parseInt(handleEditText(edit_yummycube_plus));
                int normal_plus = Integer.parseInt(handleEditText(edit_normalcube_plus));
                double price_flat =  Double.parseDouble(handleEditText_double(edit_flat_price));
                double price_black =  Double.parseDouble(handleEditText_double(edit_black_price));
                double price_top =  Double.parseDouble(handleEditText_double(edit_top_price));
                double price_cube =  Double.parseDouble(handleEditText_double(edit_cube_price));
                double checkerPrice = Double.parseDouble(handleEditText_double(edit_checker_price));


                box.setFlat(flat);
                box.setFlat_plus(flat_plus);
                box.setTop(top);
                box.setTop_plus(top_plus);
                box.setBlack(black);
                box.setBlack_plus(black_plus);
                box.setCubePink(pink);
                box.setCubePink_plus(pink_plus);
                box.setCubeYellow(yellow);
                box.setCubeYellow_plus(yellow_plus);
                box.setCubeYummy(yummy);
                box.setCubeYummy_plus(yummy_plus);
                box.setCubeNormal(normal);
                box.setCubeNormal_plus(normal_plus);
                box.setPrint_flat(price_flat);
                box.setPrint_black(price_black);
                box.setPrint_top(price_top);
                box.setPrint_cube(price_cube);
                box.setCheckerPrice(checkerPrice);


                if (box.getDate()==null) {
                    Toast.makeText(BoxActivity.this, "請選日期", Toast.LENGTH_LONG).show();
                    return;
                }

                if(box.getCheckerTime()!=null ){
                    if(box.getCheckerTime_end()==null){
                        Toast.makeText(BoxActivity.this, "請輸入結束時間", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if(box.getCheckerTime_end()!=null ){
                    if(box.getCheckerTime()==null){
                        Toast.makeText(BoxActivity.this, "請輸入開始時間", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if(BoxUtil.isChecker(box)&& BoxUtil.handleCheckerHour(box)<=0){
                    Toast.makeText(BoxActivity.this, "結束時間不能小於開始時間", Toast.LENGTH_LONG).show();
                    return;
                }


//                preBox= boxDAO.getByDate(box);
//                BoxUtil.handleTotal(box,preBox);

                long i = boxDAO.saveByDate(box);
                if (i > 0) {
                    Toast.makeText(BoxActivity.this, "儲存成功", Toast.LENGTH_LONG).show();
                    BoxActivity.this.finish();
                }
            }
        });


    }






    public String handleEditText(EditText edit){
        String ss;

        ss = edit.getText()==null?"0":edit.getText().toString();

        if ("".equals(edit.getText().toString().trim())){
            ss = "0";
        }


        return ss;
    }

    public String handleEditText_double(EditText edit){
        String ss;

        ss = edit.getText()==null?"0.0":edit.getText().toString();

        if ("".equals(edit.getText().toString().trim())){
            ss = "0.0";
        }


        return ss;
    }

    public void init(){
        btn = (Button) findViewById(R.id.button);
        edit_flat = (EditText) findViewById(R.id.edit_flat);
        edit_top = (EditText) findViewById(R.id.edit_top);
        edit_black = (EditText) findViewById(R.id.edit_black);
        edit_pinkcube = (EditText) findViewById(R.id.edit_pinkcube);
        edit_yellowcube = (EditText) findViewById(R.id.edit_yellowcube);
        edit_yummycube = (EditText) findViewById(R.id.edit_yummycube);
        edit_normalcube = (EditText) findViewById(R.id.edit_normalcube);
        edit_flat_plus = (EditText) findViewById(R.id.edit_flat_plus);
        edit_top_plus = (EditText) findViewById(R.id.edit_top_plus);
        edit_black_plus = (EditText) findViewById(R.id.edit_black_plus);
        edit_pinkcube_plus = (EditText) findViewById(R.id.edit_pinkcube_plus);
        edit_yellowcube_plus = (EditText) findViewById(R.id.edit_yellowcube_plus);
        edit_yummycube_plus = (EditText) findViewById(R.id.edit_yummycube_plus);
        edit_normalcube_plus = (EditText) findViewById(R.id.edit_normalcube_plus);
        edit_flat_price = (EditText) findViewById(R.id.edit_flat_price);
        edit_black_price = (EditText) findViewById(R.id.edit_black_price);
        edit_top_price = (EditText) findViewById(R.id.edit_top_price);
        edit_cube_price = (EditText) findViewById(R.id.edit_cube_price);

        dateButton = (Button)findViewById(R.id.dateButton);
        dateText = (TextView)findViewById(R.id.dateText);

        edit_checker_price = (EditText) findViewById(R.id.edit_checker_price);
        btnTime_start = (ImageButton) findViewById(R.id.btnTime_start);
        btnTime_end = (ImageButton)findViewById(R.id.btnTime_end);
        txtTime_start = (TextView)findViewById(R.id.txtTime_start);
        txtTime_end = (TextView)findViewById(R.id.txtTime_end);
        btnStart_reset = (Button) findViewById(R.id.btnStart_reset);
        btnEnd_reset = (Button) findViewById(R.id.btnEnd_reset);


        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(BoxActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format =   setDateFormat(year,month,day);
                        dateText.setText("日期: "+format);
                        box.setDate(DateUtil.stringToDate(format));
                    }

                }, mYear,mMonth, mDay).show();
            }

        });


        btnTime_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 設定初始時間
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // 跳出時間選擇器
                new TimePickerDialog(BoxActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // 完成選擇，顯示時間
                        txtTime_start.setText(hourOfDay + ":" + minute);
                        String format = setTimeFormat(hourOfDay,minute);
                        box.setCheckerTime(DateUtil.stringToTime(format));
                    }
                }, mHour, mMinute, false).show();
            }
        });

        btnTime_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 設定初始時間
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // 跳出時間選擇器
                new TimePickerDialog(BoxActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // 完成選擇，顯示時間
                        txtTime_end.setText(hourOfDay + ":" + minute);
                        String format = setTimeFormat(hourOfDay,minute);
                        box.setCheckerTime_end(DateUtil.stringToTime(format));
                    }
                }, mHour, mMinute, false).show();
            }
        });

        btnStart_reset.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                txtTime_start.setText(null);
                box.setCheckerTime(null);
            }
        });

        btnEnd_reset.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                txtTime_end.setText(null);
                box.setCheckerTime_end(null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_box, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                long ii = boxDAO.delete(box);

                if(ii>0){
                    Toast.makeText(BoxActivity.this, "刪除成功", Toast.LENGTH_SHORT).show();
                    BoxActivity.this.finish();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     *
     * @param
     */
    private void readData(String date){
        if(date == null)
            return;


        box = boxDAO.getByDate(date);
        if(box == null)
            return;

        putToUI(box);
    }

    public void putToUI(Box box){
        edit_flat.setText(String.valueOf(box.getFlat()));
        edit_top.setText(String.valueOf(box.getTop()));
        edit_black.setText(String.valueOf(box.getBlack()));
        edit_pinkcube.setText(String.valueOf(box.getCubePink()));
        edit_yellowcube.setText(String.valueOf(box.getCubeYellow()));
        edit_yummycube.setText(String.valueOf(box.getCubeYummy()));
        edit_normalcube.setText(String.valueOf(box.getCubeNormal()));
        edit_flat_plus.setText(String.valueOf(box.getFlat_plus()));
        edit_top_plus.setText(String.valueOf(box.getTop_plus()));
        edit_black_plus.setText(String.valueOf(box.getBlack_plus()));
        edit_pinkcube_plus.setText(String.valueOf(box.getCubePink_plus()));
        edit_yellowcube_plus.setText(String.valueOf(box.getCubeYellow_plus()));
        edit_yummycube_plus.setText(String.valueOf(box.getCubeYummy_plus()));
        edit_normalcube_plus.setText(String.valueOf(box.getCubeNormal_plus()));

        //讀取日期
        String format = "日期: " + String.valueOf( DateUtil.getYear(box.getDate())) + "-"
                + String.valueOf(DateUtil.getMonth(box.getDate())) + "-"
                + String.valueOf(DateUtil.getDayOfMonth(box.getDate()));
        dateText.setText(format);

        //讀取price
        edit_flat_price.setText(String.valueOf(box.getPrint_flat()));
        edit_black_price.setText(String.valueOf(box.getPrint_black()));
        edit_top_price.setText(String.valueOf(box.getPrint_top()));
        edit_cube_price.setText(String.valueOf(box.getPrint_cube()));

        //讀取checker
        edit_checker_price.setText(String.valueOf(box.getCheckerPrice()));
        txtTime_start.setText(DateUtil.timeToString(box.getCheckerTime()));
        txtTime_end .setText(DateUtil.timeToString(box.getCheckerTime_end()));
    }

    /**
     * 處理日期
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    private String setDateFormat(int year, int monthOfYear, int dayOfMonth) {
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    private String setTimeFormat(int mHour, int mMinute) {
        return String.valueOf(mHour) + ":"
                + String.valueOf(mMinute) + ":"
                + String.valueOf("00");
    }
}
