package com.zzz.wc.strawberrycount.adpter;
/**
 * Box Adapter
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzz.wc.strawberrycount.R;
import com.zzz.wc.strawberrycount.checker.Checker;
import com.zzz.wc.strawberrycount.util.Tag;
import com.zzz.wc.strawberrycount.box.Box;
import com.zzz.wc.strawberrycount.util.BoxUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BoxAdapter extends BaseAdapter {

    private Context context;
    private List<Box> boxes;            //原始資料
    private List<Boolean> checked;
    private List<Checker>checkers;
    private HashMap<String, Double> map;
    private OnClickListener onClickListener;


    public BoxAdapter(Context context, List<Box> cards ,List<Checker>checkerList) {
        this.context = context;
        this.boxes = (cards == null || cards.isEmpty()) ? new ArrayList<Box>() : cards;
        this.checkers = (checkerList == null || checkerList.isEmpty()) ? new ArrayList<Checker>() : checkerList;
        map =  BoxUtil.getCheckerMap(checkers);

        checked = new ArrayList<Boolean>(cards.size());
        for (int i = 0; i < cards.size(); i++) {
            checked.add(false);
        }


//        onClickListener = new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                int btnPosition = (Integer) v.getTag();
//                CheckBox cb = (CheckBox) v;
//                checked.set(btnPosition, cb.isChecked());
//
//            }
//        };



    }

    @Override
    public int getCount() {
//		return showCards.size();
        return boxes.size();
    }

    @Override
    public Object getItem(int position) {
        return boxes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CardHolder holder = null;
        View row = convertView;
        holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.item_box, parent, false);

            holder = new CardHolder();

            holder.tvDate = getView(row, R.id.txtview_date);
            holder.tvDate2 = getView(row, R.id.tvDate2);
            holder.tvFlat_minus = getView(row, R.id.tv_flat_minus);
            holder.tvFlat_plus = getView(row, R.id.tv_flat);
            holder.tvTop_minus = getView(row, R.id.tv_top_minus);
            holder.tvTop_plus = getView(row, R.id.tv_top);
            holder.tvblack_minus = getView(row, R.id.tv_black_minus);
            holder.tvBlak_plus = getView(row, R.id.tv_black);
            holder.tvCube_minus = getView(row, R.id.tv_cube_minus);
            holder.tvCube_plus = getView(row, R.id.tv_cube_plus);
            holder.tvTotal = getView(row, R.id.tv_total);
            holder.tvFlat_pack = getView(row, R.id.tv_flat_pack);
            holder.tvTop_pack = getView(row, R.id.tv_top_pack);
            holder.tvblack_pack = getView(row, R.id.tv_black_pack);
            holder.tvCube_pack = getView(row, R.id.tv_cube_pack);
            holder.tvCheckerTime = getView(row, R.id.tv_checkertime);
            holder.getTvCheckerTotal = getView(row,R.id.tv_checkertotal);



            row.setTag(holder);
        } else {
            holder = (CardHolder) row.getTag();
        }

        Box box = (Box) getItem(position);



        holder.tvDate.setText(new SimpleDateFormat("yyyy").format(box.getDate()));
        holder.tvDate2.setText(new SimpleDateFormat("MM/dd").format(box.getDate()));

        holder.tvFlat_minus.setText(String.valueOf(box.getFlat()));
        holder.tvFlat_plus.setText(String.valueOf(box.getFlat_plus()));
        holder.tvblack_minus.setText(String.valueOf(box.getBlack()));
        holder.tvBlak_plus.setText(String.valueOf(box.getBlack_plus()));
        holder.tvTop_minus.setText(String.valueOf(box.getTop()));
        holder.tvTop_plus.setText(String.valueOf(box.getTop_plus()));
        holder.tvCube_minus.setText(String.valueOf(BoxUtil.getCube(box,Tag.BoxPlusMinus.Minus)));
        holder.tvCube_plus.setText(String.valueOf(BoxUtil.getCube(box,Tag.BoxPlusMinus.Plus)));



        holder.tvFlat_pack.setText("0");
        holder.tvblack_pack.setText("0");
        holder.tvTop_pack.setText("0");
        holder.tvCube_pack.setText("0");
        holder.tvTotal.setText("$0");

        //checker 錢錢

        if(map!=null && !map.isEmpty()){

            if(map.get(BoxUtil.converToString(box.getDate()))!=null){
                double total =Double.valueOf(map.get(BoxUtil.converToString(box.getDate()))) ;
                if(total>0){
                    holder.getTvCheckerTotal.setText("$ " + String.format("%.2f",total ));
                }
            }


        }
        else{
            holder.getTvCheckerTotal.setText(" X");
        }


        //box 錢錢
        if(position < boxes.size()-1){
            Box preBox = (Box) getItem(position+1);
            if(preBox !=null){
                holder.tvFlat_pack.setText(String.valueOf(BoxUtil.calBox(preBox,box,Tag.BoxName.FLAT)));
                holder.tvblack_pack.setText(String.valueOf(BoxUtil.calBox(preBox,box,Tag.BoxName.BLACK)));
                holder.tvTop_pack.setText(String.valueOf(BoxUtil.calBox(preBox,box,Tag.BoxName.FLATTOP)));
                holder.tvCube_pack.setText(String.valueOf(BoxUtil.calBox(preBox,box,Tag.BoxName.CUBE_NOMAL)));
                //動態算的
                holder.tvTotal.setText("$" + String.format("%.2f", BoxUtil.handleTotal(box, preBox)));
            }
        }else{
            holder.tvTotal.setText("$0");
        }




        return row;
    }



    public void reload(List<Box> list){
        boxes = list;
        notifyDataSetChanged();
    }


    @SuppressWarnings("unchecked")
    private <E extends View> E getView(View view, int id) {
        return (E) view.findViewById(id);
    }




    class CardHolder {
        TextView tvDate;
        TextView tvDate2;
        TextView tvFlat_minus;
        TextView tvFlat_plus;
        TextView tvblack_minus;
        TextView tvBlak_plus;
        TextView tvTop_minus;
        TextView tvTop_plus;
        TextView tvCube_minus;
        TextView tvCube_plus;
        TextView tvTotal;
        TextView tvFlat_pack;
        TextView tvblack_pack;
        TextView tvTop_pack;
        TextView tvCube_pack;
        TextView tvCheckerTime;
        TextView getTvCheckerTotal;
    }



}
