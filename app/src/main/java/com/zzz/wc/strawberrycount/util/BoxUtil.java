package com.zzz.wc.strawberrycount.util;

import android.content.Context;

import com.zzz.wc.strawberrycount.box.Box;
import com.zzz.wc.strawberrycount.checker.Checker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by YC on 2017/10/25.
 */

public class BoxUtil {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static int getCube(Box box , int cal) {

        int amount = 0;
        if(cal == Tag.BoxPlusMinus.Minus){
            amount = box.getCubeYellow() + box.getCubePink() + box.getCubeYummy() + box.getCubeNormal();
        }
        else{
            amount = box.getCubeYellow_plus() + box.getCubePink_plus() + box.getCubeYummy_plus() + box.getCubeNormal_plus();
        }

        return amount;
    }


    public static int calBox(Box preBox, Box box , int cal) {

        int amount = 0;

        switch (cal){
            case Tag.BoxName.FLAT:
                amount = preBox.getFlat() + box.getFlat_plus() - box.getFlat();
                break;

            case Tag.BoxName.FLATTOP:
                amount = preBox.getTop() + box.getTop_plus() - box.getTop();
                break;

            case Tag.BoxName.BLACK:
                amount = preBox.getBlack() + box.getBlack_plus() - box.getBlack();
                break;

            case Tag.BoxName.CUBE_NOMAL:
                amount = (getCube(preBox,Tag.BoxPlusMinus.Minus)
                        + getCube(box,Tag.BoxPlusMinus.Plus)
                        -getCube(box,Tag.BoxPlusMinus.Minus));
                break;

        }

        return amount;
    }

    /**
     * 算total
     * @param box
     */
    public static double handleTotal(Box box,Box preBox){
        double amount = 0.0;
        if(preBox == null)
            return amount;

        int falt = BoxUtil.calBox(preBox,box,Tag.BoxName.FLAT);
        int black = BoxUtil.calBox(preBox,box,Tag.BoxName.BLACK);
        int top = BoxUtil.calBox(preBox,box,Tag.BoxName.FLATTOP);
        int cubeTotal = BoxUtil.calBox(preBox,box,Tag.BoxName.CUBE_NOMAL);


        amount = (box.getPrint_flat()*falt) + (box.getPrint_black() * black)
                + (box.getPrint_top() * top) + box.getPrint_cube() * cubeTotal;


        return amount;
    }


    public static double handleCheckerHour(long start, long end){
        double hr = 0;
        long diff =end - start;
        hr = Double.valueOf((diff/(1000*60)))/60;


		System.out.println("----------差幾小時? = "+hr);

        return hr;
    }

    public static HashMap<String, Double> getCheckerMap(List<Checker> list){
        HashMap<String, Double> hashMap = new HashMap<String, Double>();

        double price; //hr * 時薪
        double value;
        double all; //加總byDay
        for (Checker checker : list) {
            String key =converToString(checker.getCheckerDate());
            double hr = BoxUtil.handleCheckerHour(checker.getCheckerTime().getTime(),checker.getCheckerTime_end().getTime());

            price = hr * checker.getCheckerPrice();

            if (hashMap.get(key) != null && !hashMap.isEmpty()) {
                value = hashMap.get(key);
                all = value + price;
                hashMap.put(key, Double.valueOf(all));
                System.out.println("the element:"+price+" is repeat");
            } else {
                price = hr * checker.getCheckerPrice();
                hashMap.put(key, Double.valueOf(price));
            }
        }

        return hashMap;
    }

    public static String converToString(Date date){
        String rtn = "";
        rtn = dateFormat.format(date);

        return rtn;
    }
}
