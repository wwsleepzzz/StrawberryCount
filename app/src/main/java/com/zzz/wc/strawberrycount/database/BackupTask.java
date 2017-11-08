package com.zzz.wc.strawberrycount.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by YC on 2017/11/5.
 */

public class BackupTask extends AsyncTask<String, Void, Integer> {
    private static final String COMMAND_BACKUP = "backupDatabase";
    public static final String COMMAND_RESTORE = "restroeDatabase";

    public static final String packageName = "com.zzz.wc.strawberrycount";
    public  Context mCoNtext;

    public BackupTask(Context coNtext) {
        this.mCoNtext = coNtext;
    }

    @Override
    protected Integer doInBackground(String... params) {
        //獲得正在使用的資料庫路徑，我的是 sdcard 目錄下的 /dlion/db_dlion.db預設路徑是 /data/data/(包名)/databases/*.db
        File dbFile = mCoNtext.getDatabasePath(Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/"+packageName+"/"+TaskDBOpenHelper.DATABASE_NAME+".db");
        File exportDir = new File(Environment.getExternalStorageDirectory(),
                "strawberryBackup");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File backup = new File(exportDir, dbFile.getName());
        String command = params[0];
        if (command.equals(COMMAND_BACKUP)) {
            try {
                backup.createNewFile();
                fileCopy(dbFile, backup);
                return Log.d("backup", "ok");
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
                return Log.d("backup", "fail");
            }
        } else if (command.equals(COMMAND_RESTORE)) {
            try {
                fileCopy(backup, dbFile);
                return Log.d("restore", "success");
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
                return Log.d("restore", "fail");
            }
        } else {
            return null;
        }
    }



    private void fileCopy(File dbFile, File backup) throws IOException {
        //TODO Auto -generated method stub
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            //TODO Auto -generated catch block
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }
}
