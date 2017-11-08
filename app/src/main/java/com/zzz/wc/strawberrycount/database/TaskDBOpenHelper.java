package com.zzz.wc.strawberrycount.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskDBOpenHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "strawberry";   // 資料庫名稱
	private static final int DATABASE_VERSION = 1;  // 版本,此一數字一改(不管變大變小),資料即刪並重建!
	
	private static TaskDBOpenHelper mInstance;
	

	public static final String TABLE = "box";

	//新增欄位
	public static final String checkerTime = "checkerTime"; //欄位名稱
	public static final String checkerTime_end = "checkerTime_end"; //欄位名稱
	public static final String checkerPrice = "checkerPrice";


	public TaskDBOpenHelper(Context context)
	  {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	public synchronized static TaskDBOpenHelper getInstance(Context context){
		if (mInstance == null) {
			mInstance = new TaskDBOpenHelper(context);
		}
		return mInstance;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("TaskDBOpenHelper" , "onCreate");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE
				+ " (date DATETIME PRIMARY KEY,flat INTEGER, flat_plus INTEGER,"
				+ "black INTEGER , black_plus INTEGER ,top INTEGER,"
				+ "top_plus INTEGER , cubeYellow INTEGER , cubeYellow_plus INTEGER,"
				+ "cubePink INTEGER , cubePink_plus INTEGER , cubeYummy INTEGER,"
				+ "cubeYummy_plus INTEGER , cubeNormal INTEGER , cubeNormal_plus INTEGER,"
				+ "total DOUBLE , print_flat DOUBLE , print_black DOUBLE , print_top DOUBLE,"
				+ "print_cube DOUBLE , " + checkerTime + " DATETIME," + checkerTime_end +" DATETIME,"
				+ checkerPrice +" DOUBLE" +
				");");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("DROP TABLE IF EXISTS "+EMPLOYEE_TABLE);
		
//		dropTable(db);
//		onCreate(db);


//		if(newVersion > oldVersion) {
//
//			db.beginTransaction();
//
//			if(oldVersion == 1) {
//				try {
//					// 新增一個日期欄位，預設為NULL字串
//
//					db.execSQL("ALTER TABLE " + TABLE
//							+ " ADD COLUMN " + checkerPrice + " DOUBLE");
//
//					Log.d("SQLite", "版本更新成功，結束交易...");
//					db.setTransactionSuccessful();
//					db.endTransaction();
//
//				} catch (Exception e) {
//					Log.d("SQLite", "版本更新失敗，結束交易...");
//					e.printStackTrace();
//					db.endTransaction();
//				}
//
//			} else {
//				onCreate(db);
//			}
//
//		}

	}
	

	public void dropTable(SQLiteDatabase db)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);

		//===========上傳DB不DROP============================
	}

}
