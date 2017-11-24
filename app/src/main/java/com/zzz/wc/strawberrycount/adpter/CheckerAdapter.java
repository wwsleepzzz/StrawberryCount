package com.zzz.wc.strawberrycount.adpter;
/**
 * Checker Adapter
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zzz.wc.strawberrycount.R;
import com.zzz.wc.strawberrycount.checker.Checker;
import com.zzz.wc.strawberrycount.util.BoxUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class CheckerAdapter extends BaseAdapter {

    private Context context;
    private List<Checker> checkers;            //原始資料


    public CheckerAdapter(Context context, List<Checker> list) {
        this.context = context;
        this.checkers = (list == null || list.isEmpty()) ? new ArrayList<Checker>() : list;

    }

    @Override
    public int getCount() {
        return checkers.size();
    }

    @Override
    public Object getItem(int position) {
        return checkers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CheckerHolder holder;
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.item_checker, parent, false);

            holder = new CheckerHolder();

            holder.tv_time = getView(row, R.id.tv_checker);
            holder.ib_delete = getView(row, R.id.ib_delete);
            holder.tv_total = getView(row,R.id.tv_total);

            row.setTag(holder);

        } else {
            holder = (CheckerHolder) row.getTag();
        }


        Checker checker = (Checker) getItem(position);


        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");


        //checker
        if(checker.getCheckerTime()!=null && checker.getCheckerTime_end()!=null){
            double hr = BoxUtil.handleCheckerHour(checker.getCheckerTime().getTime(),checker.getCheckerTime_end().getTime());
            holder.tv_time.setText(hourFormat.format(checker.getCheckerTime()) + "~"
                    + hourFormat.format(checker.getCheckerTime_end())  + " (" + String.format("%.2f",hr) + " hr)" );

            holder.tv_total.setText("$ " + String.format("%.2f", hr*checker.getCheckerPrice()));
        }
        else{
            holder.tv_time.setText(" X");
            holder.tv_total.setText("$0");
        }

        holder.ib_delete.setTag(position);
        holder.ib_delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Checker checker = (Checker) getItem(position);
                Log.d("CheckerAdapter","Adapter btn click");
                checkerListenerInterface.onClick(checker);
            }
        });


        return row;
    }



    public void reload(List<Checker> list){
        checkers = list;
        notifyDataSetChanged();
    }


    @SuppressWarnings("unchecked")
    private <E extends View> E getView(View view, int id) {
        return (E) view.findViewById(id);
    }




    class CheckerHolder {
        TextView tv_time;
        TextView tv_total;
        ImageButton ib_delete;
    }



    //介面
    private CheckerListenerInterface checkerListenerInterface;

    public interface CheckerListenerInterface {
        void onClick(Checker checker);

    }

    public void setCheckerListenerInterface(CheckerListenerInterface checkerListenerInterface) {
        this.checkerListenerInterface = checkerListenerInterface;
    }



}
