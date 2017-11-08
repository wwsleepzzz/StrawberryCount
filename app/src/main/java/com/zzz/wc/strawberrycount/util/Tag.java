package com.zzz.wc.strawberrycount.util;

/**
 * Created by YC on 2017/9/17.
 */

public class Tag {


    public String pattern1 = "dd/MM/yyyy";
    public class BoxName{
        public final static int BLACK = 1;  //黑盤
        public final static int FLATTOP= 2; //厚底
        public final static int FLAT = 11; //扁盒
        public final static int CUBE_YELLOW = 12;
        public final static int CUBE_PINK = 13;
        public final static int CUBE_YUMMY = 14;
        public final static int CUBE_NOMAL = 15;
    }

    public class BoxPlusMinus{
        public final static int Plus = 1;
        public final static int Minus = 0;
    }

    public class BundleKey{
        public final static String DATE = "date";
    }

}
