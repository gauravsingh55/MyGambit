package com.example.mg.mygambit.src;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import android.content.Context;

import android.util.Log;

import com.example.mg.mygambit.src.board.BoardList;

public class InternalStorage implements Serializable {

    private  final String TAG = "InternalStorage";

    static File contextPath;

    BoardList bl;

    private InternalStorage() {}
    public static InternalStorage getInstance() {
        InternalStorage is = new InternalStorage();
        return is;
    }

    public File getContextPath(){
        return contextPath;
    }

    public boolean save(String path, BoardList newBl, Context parentActivity){
        bl = newBl;
        String filePath = parentActivity.getFilesDir() + "/" + path + ".chess";

        File newF =  new File(filePath);

        Log.i(TAG,  "filePath is:"+filePath);
        contextPath = parentActivity.getFilesDir();

        ObjectOutputStream oos;

        try{
            FileOutputStream fos = new FileOutputStream(newF);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(newBl);
            oos.close();
            fos.close();
            Log.i(TAG, "SAVED:"+newF);
        } catch( Exception e ){
            Log.i(TAG, "Not Saved" + e.toString() );
//            e.printStackTrace();
            return false;
        }

        return false;
    }

    public BoardList load(File file, Context parentActivity){
        ObjectInputStream ois;

        try{
            FileInputStream fileInput = new FileInputStream(file);
            ois = new ObjectInputStream(fileInput);
            bl = (BoardList)ois.readObject();
            ois.close();
            fileInput.close();
            Log.i(TAG, "Loaded");
            return bl;
        } catch( Exception e ){
            Log.i(TAG, "Not Loaded" + e.toString());
        }

        return null;
    }

}
/*

public static void writeObject(Context context, String key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }

*/
