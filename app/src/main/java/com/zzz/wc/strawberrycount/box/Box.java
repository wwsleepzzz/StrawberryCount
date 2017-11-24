package com.zzz.wc.strawberrycount.box;

import com.zzz.wc.strawberrycount.checker.Checker;

import java.util.Date;
import java.util.List;

/**
 * Created by YC on 2017/9/17.
 */

public class Box {
    private Date date;
    private int flat;
    private int flat_plus;
    private int black;
    private int black_plus;
    private int top;
    private int top_plus;
    private int cubeYellow;
    private int cubeYellow_plus;
    private int cubePink;
    private int cubePink_plus;
    private int cubeYummy;
    private int cubeYummy_plus;
    private int cubeNormal;
    private int cubeNormal_plus;
    private double total; //作廢
    private double print_flat;
    private double print_black;
    private double print_top;
    private double print_cube;


    private Date checkerTime; //作廢
    private Date checkerTime_end; //作廢
    private double checkerPrice; //作廢



    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPrint_flat() {
        return print_flat;
    }

    public void setPrint_flat(double print_flat) {
        this.print_flat = print_flat;
    }

    public double getPrint_black() {
        return print_black;
    }

    public void setPrint_black(double print_black) {
        this.print_black = print_black;
    }

    public double getPrint_top() {
        return print_top;
    }

    public void setPrint_top(double print_top) {
        this.print_top = print_top;
    }

    public double getPrint_cube() {
        return print_cube;
    }

    public void setPrint_cube(double print_cube) {
        this.print_cube = print_cube;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getFlat_plus() {
        return flat_plus;
    }

    public void setFlat_plus(int flat_plus) {
        this.flat_plus = flat_plus;
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public int getBlack_plus() {
        return black_plus;
    }

    public void setBlack_plus(int black_plus) {
        this.black_plus = black_plus;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getTop_plus() {
        return top_plus;
    }

    public void setTop_plus(int top_plus) {
        this.top_plus = top_plus;
    }

    public int getCubeYellow() {
        return cubeYellow;
    }

    public void setCubeYellow(int cubeYellow) {
        this.cubeYellow = cubeYellow;
    }

    public int getCubeYellow_plus() {
        return cubeYellow_plus;
    }

    public void setCubeYellow_plus(int cubeYellow_plus) {
        this.cubeYellow_plus = cubeYellow_plus;
    }

    public int getCubePink() {
        return cubePink;
    }

    public void setCubePink(int cubePink) {
        this.cubePink = cubePink;
    }

    public int getCubePink_plus() {
        return cubePink_plus;
    }

    public void setCubePink_plus(int cubePink_plus) {
        this.cubePink_plus = cubePink_plus;
    }

    public int getCubeYummy() {
        return cubeYummy;
    }

    public void setCubeYummy(int cubeYummy) {
        this.cubeYummy = cubeYummy;
    }

    public int getCubeYummy_plus() {
        return cubeYummy_plus;
    }

    public void setCubeYummy_plus(int cubeYummy_plus) {
        this.cubeYummy_plus = cubeYummy_plus;
    }

    public int getCubeNormal() {
        return cubeNormal;
    }

    public void setCubeNormal(int cubeNormal) {
        this.cubeNormal = cubeNormal;
    }

    public int getCubeNormal_plus() {
        return cubeNormal_plus;
    }

    public void setCubeNormal_plus(int cubeNormal_plus) {
        this.cubeNormal_plus = cubeNormal_plus;
    }

    public Date getCheckerTime() {
        return checkerTime;
    }

    public void setCheckerTime(Date checkerTime) {
        this.checkerTime = checkerTime;
    }

    public double getCheckerPrice() {
        return checkerPrice;
    }

    public void setCheckerPrice(double checkerPrice) {
        this.checkerPrice = checkerPrice;
    }

    public Date getCheckerTime_end() {
        return checkerTime_end;
    }

    public void setCheckerTime_end(Date checkerTime_end) {
        this.checkerTime_end = checkerTime_end;
    }


}
