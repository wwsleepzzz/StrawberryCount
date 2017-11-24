package com.zzz.wc.strawberrycount.box;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.zzz.wc.strawberrycount.database.BoxDBOpenHelper;
import com.zzz.wc.strawberrycount.database.SQLiteDAOBase;
import com.zzz.wc.strawberrycount.util.BoxUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoxDAO extends SQLiteDAOBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String TABLE_NAME = BoxDBOpenHelper.TABLE_BOX;
    private String classname = "BoxDAO";

	String[] columns = new String[] { "date" , "flat" ,"flat_plus" ,"black" , "black_plus",
			"top","top_plus","cubeYellow","cubeYellow_plus","cubePink","cubePink_plus",
			"cubeYummy" ,"cubeYummy_plus" ,"cubeNormal" ,"cubeNormal_plus","total",
			"print_flat","print_black","print_top","print_cube",
			BoxDBOpenHelper.checkerTime,
			BoxDBOpenHelper.checkerTime_end,
			BoxDBOpenHelper.checkerPrice
			 };

	public BoxDAO(Context context)
	{
		super(context);
	}
	
	public long saveByDate(Box pl)
	{

		SQLiteDatabase db = this.taskDBOpenHelper.getWritableDatabase();
		long rows = 0;

		ContentValues cv = ObjectToContentValues(pl);
		String selection = "date =?";
		String[] selectionArgs = new String[] {BoxUtil.converToString(pl.getDate()) };

		rows = db.update(TABLE_NAME, cv, selection, selectionArgs);
		if (rows <= 0) {
			Log.d(classname,"rows <= 0");
			rows = db.insert(TABLE_NAME, null, cv);
		}else{
			Log.d(classname,"rows > 0");
		}

		return rows;
	}



	/**
	 * 用date找出一筆資料
	 */
	public Box getByDate(String date){

		SQLiteDatabase db = taskDBOpenHelper.getReadableDatabase();

		Box box = null;

		String selection = "date = ?";
		String selectionArgs[] =  { date};
		String groupBy = null;
		String having = null;
		String orderBy = "date DESC";
		Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

		if(c.getCount()>0)
		{
			Log.d("cc","取到一筆!");
			c.moveToFirst();
			for(int i=0; i<c.getCount();i++){

				box = dataToObject(c);

				c.moveToNext();
			}

		}
		c.close();
		return box;
	}




	/**
	 * 用name找出一筆資料
	 */
	public Box getLast(){

		SQLiteDatabase db = taskDBOpenHelper.getReadableDatabase();

		Box cr = null;

		String selection = null;
		String selectionArgs[] = null;
		String groupBy = null;
		String having = null;
		String orderBy = "date DESC";
		Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
//
//		String selectionArgs[] =  {Util.converToString(date) };
//		Cursor c = db.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE date = ?", selectionArgs);

		if(c.getCount()>0)
		{
			Log.d(classname,"getLast 取到一筆!");
			c.moveToFirst();
			for(int i=0; i<c.getCount();i++){

				cr = dataToObject(c);

				c.moveToNext();
				break;
			}

		}
		c.close();
		return cr;
	}



	public long delete(Box ace)
	{
		SQLiteDatabase db = this.taskDBOpenHelper.getWritableDatabase();
		long rows = 0;

		String selection = "date =?";
		String[] selectionArgs = new String[] { BoxUtil.converToString(ace.getDate()) };
		
		rows = db.delete(TABLE_NAME, selection, selectionArgs);

		return rows;
	}

	public long deleteAll()
	{
		SQLiteDatabase db = this.taskDBOpenHelper.getWritableDatabase();
		long rows = 0;


		rows = db.delete(TABLE_NAME, null,null);

		return rows;
	}

	@Override
	protected ContentValues ObjectToContentValues(Object obj) {

		Box box = (Box) obj;
		ContentValues cv = new ContentValues();

		int i =0;
		cv.put(columns[i], box.getDate()==null?null: dateFormat.format(box.getDate()));
		cv.put(columns[++i], box.getFlat());
		cv.put(columns[++i],box.getFlat_plus());
		cv.put(columns[++i], box.getBlack());
		cv.put(columns[++i],box.getBlack_plus());
		cv.put(columns[++i],box.getTop());
		cv.put(columns[++i],box.getTop_plus());
		cv.put(columns[++i],box.getCubeYellow());
		cv.put(columns[++i],box.getCubeYellow_plus());
		cv.put(columns[++i],box.getCubePink());
		cv.put(columns[++i],box.getCubePink_plus());
		cv.put(columns[++i],box.getCubeYummy());
		cv.put(columns[++i],box.getCubeYummy_plus());
		cv.put(columns[++i],box.getCubeNormal());
		cv.put(columns[++i],box.getCubeNormal_plus());
		cv.put(columns[++i],box.getTotal());
		cv.put(columns[++i] ,box.getPrint_flat());
		cv.put(columns[++i],box.getPrint_black());
		cv.put(columns[++i],box.getPrint_top());
		cv.put(columns[++i],box.getPrint_cube());
		cv.put(columns[++i],box.getCheckerTime()==null?null: hourFormat.format(box.getCheckerTime()));
		cv.put(columns[++i],box.getCheckerTime_end()==null?null: hourFormat.format(box.getCheckerTime_end()));
		cv.put(columns[++i],box.getCheckerPrice());

//		cv.put("flat", box.getFlat());
//		cv.put("flat_plus",box.getFlat_plus());
//		cv.put("black", box.getBlack());
//		cv.put("black_plus",box.getBlack_plus());
//		cv.put("top",box.getTop());
//		cv.put("top_plus",box.getTop_plus());
//		cv.put("cubeYellow",box.getCubeYellow());
//		cv.put("cubeYellow_plus",box.getCubeYellow_plus());
//		cv.put("cubePink",box.getCubePink());
//		cv.put("cubePink_plus",box.getCubePink_plus());
//		cv.put("cubeYummy",box.getCubeYummy());
//		cv.put("cubeYummy_plus",box.getCubeYummy_plus());
//		cv.put("cubeNormal",box.getCubeNormal());
//		cv.put("cubeNormal_plus",box.getCubeNormal_plus());
//		cv.put("total",box.getTotal());
//		cv.put("print_flat" ,box.getPrint_flat());
//		cv.put("print_black",box.getPrint_black());
//		cv.put("print_top",box.getPrint_top());
//		cv.put("print_cube",box.getPrint_cube());







		return cv;
	}
	
	
	
	/**
	 */
	public List<Box> getAll(){
		List<Box> aList = new ArrayList<Box>();
		SQLiteDatabase db = taskDBOpenHelper.getReadableDatabase();
		String selection = null;
		String selectionArgs[] =  {};
		String groupBy = null;
		String having = null;
		String orderBy = "date DESC";
		Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

//		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, selectionArgs);
		
		if(c.getCount()>0)
		{
			Log.d("getall","getCount>0");
			c.moveToFirst();
			for(int i=0; i<c.getCount();i++){

				Box a = dataToObject(c);
				aList.add(a);
				c.moveToNext();
			}
			
		}
		c.close();
		return aList;
	}
	
	public Box dataToObject(Cursor c)
	{
		Box box= new Box();

		box.setDate(converterDate(c,columns[0],dateFormat));
		box.setFlat(c.getInt(c.getColumnIndex("flat")));
		box.setFlat_plus(c.getInt(c.getColumnIndex("flat_plus")));
		box.setBlack(c.getInt(c.getColumnIndex("black")));
		box.setBlack_plus(c.getInt(c.getColumnIndex("black_plus")));
		box.setTop(c.getInt(c.getColumnIndex("top")));
		box.setTop_plus(c.getInt(c.getColumnIndex("top_plus")));
		box.setCubeYellow(c.getInt(c.getColumnIndex("cubeYellow")));
		box.setCubeYellow_plus(c.getInt(c.getColumnIndex("cubeYellow_plus")));
		box.setCubePink(c.getInt(c.getColumnIndex("cubePink")));
		box.setCubePink_plus(c.getInt(c.getColumnIndex("cubePink_plus")));
		box.setCubeYummy(c.getInt(c.getColumnIndex("cubeYummy")));
		box.setCubeYummy_plus(c.getInt(c.getColumnIndex("cubeYummy_plus")));
		box.setCubeNormal(c.getInt(c.getColumnIndex("cubeNormal")));
		box.setCubeNormal_plus(c.getInt(c.getColumnIndex("cubeNormal_plus")));
		box.setTotal(c.getDouble(c.getColumnIndex("total")));
		box.setPrint_flat(c.getDouble(c.getColumnIndex("print_flat")));
		box.setPrint_black(c.getDouble(c.getColumnIndex("print_black")));
		box.setPrint_top(c.getDouble(c.getColumnIndex("print_top")));
		box.setPrint_cube(c.getDouble(c.getColumnIndex("print_cube")));
		box.setCheckerTime(converterDate(c, BoxDBOpenHelper.checkerTime,hourFormat));
		box.setCheckerTime_end(converterDate(c, BoxDBOpenHelper.checkerTime_end,hourFormat));
		box.setCheckerPrice(c.getDouble(c.getColumnIndex(BoxDBOpenHelper.checkerPrice)));



		return box;
	}

	private Date converterDate(Cursor c, String column, SimpleDateFormat format){
		Date date = null;
		int timeCnt = c.getColumnIndex(column);
		if(c.getString(timeCnt)!=null){
			try {
				date = format.parse(c.getString(timeCnt));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}





	//清除/重建表單
	public void clearUpLoadRecord()
	{
		SQLiteDatabase db = this.taskDBOpenHelper.getWritableDatabase();
//		db.execSQL("DROP TABLE IF EXISTS " + BoxDBOpenHelper.ANN_READ_TO_UPLOAD_TABLE);
//		db.execSQL("CREATE TABLE IF NOT EXISTS " + BoxDBOpenHelper.ANN_READ_TO_UPLOAD_TABLE + " (empId TEXT,annUnid TEXT,readDate DATETIME,annType TEXT);");

	}
	


	
	/**
	 * Use @ Setting.java
	 * 依unidList 刪除
	 * @return
	 */
	public long deleteByUnidList(List<String> unidList)
	{
		SQLiteDatabase db = this.taskDBOpenHelper.getWritableDatabase();
		long rows = 0;

		if(unidList ==null || unidList.isEmpty())
			return rows;
		
		String selection = "";
		String unidStr = " =?";
		String orStr=" or ";
		
		for(int i=0; i<unidList.size(); i++){
			selection = selection+unidStr+orStr;
		}
		
		if(selection!=null && !selection.equals("")){
			selection = selection.substring(0, selection.lastIndexOf(orStr));
		}
		
		String[] selectionArgs =(String[]) unidList.toArray();
		
		rows = db.delete(TABLE_NAME, selection, selectionArgs);

		return rows;
	}






	public void insertRowData(SQLiteDatabase tempDB){
		/** copy all row of subject table */
		Cursor cursor = tempDB.query(true, TABLE_NAME, null, null, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Box note = dataToObject(cursor);
			insertBox(note);
			cursor.moveToNext();
		}
		cursor.close();
	}
	public long insertBox(Box box) {
		return insert(TABLE_NAME,ObjectToContentValues(box));
	}

	public long insert(String table, ContentValues values) {
		SQLiteDatabase db = this.taskDBOpenHelper.getWritableDatabase();
		long index = db.insert(table, null, values);


		if (db != null && db.isOpen()) {
			try {
				db.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return index;
	}

}
