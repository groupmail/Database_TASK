package fci.arabicsignlangtoarabicspeech.scu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	
	FeedReaderDbHelper Helper;
	private static String DB_PATH = "/data/data/arabicsignlangtoarabicspeech.scu/databases/";
    private static String DB_NAME = "ASL.db";

    public SQLiteDatabase getDataBase() {
        return myDataBase;
    }

    private SQLiteDatabase myDataBase;

    private final Context myContext;


	public DbHelper(Context context) {
	

        super(context, DB_NAME, null, 1);
        this.myContext=context;
	}
	
	public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        
        
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    

	public void removeAll() {
		SQLiteDatabase db = Helper.getWritableDatabase();
		db.delete(FeedReaderDbHelper.TABLE_NAME, null, null);
	}

	public String getData(String unicode) {
		// select word from unicodestable where unicode ="?"
		SQLiteDatabase db = Helper.getWritableDatabase();

		String[] columns = { FeedReaderDbHelper.WORDS };
		String[] selectionArgs = { unicode };
		// table name, returned data form column, key to get the value,
		Cursor cursor = db.query(FeedReaderDbHelper.TABLE_NAME, columns,
				FeedReaderDbHelper.UNICODES + " =?", selectionArgs, null, null,
				null);
		StringBuffer buffer = new StringBuffer();
		while (cursor.moveToNext()) {
			int index = cursor.getColumnIndex(FeedReaderDbHelper.WORDS);
			String word = cursor.getString(index);
			buffer.append(word);
			break;

		}
		return buffer.toString();
	}

	// ///////match
	public boolean getDatamatch(String root) {
		boolean g = false;
		// select unicode, word from unicodes table
		SQLiteDatabase db = Helper.getWritableDatabase();

		String[] columns = { FeedReaderDbHelper.UNICODES,
				FeedReaderDbHelper.WORDS };

		Cursor cursor = db.query(FeedReaderDbHelper.TABLE_NAME, columns, null,
				null, null, null, null);

		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(FeedReaderDbHelper.UNICODES);
			int index2 = cursor.getColumnIndex(FeedReaderDbHelper.WORDS);

			MainActivity.unicode = cursor.getString(index1);
			MainActivity.wordDB = cursor.getString(index2);
			if (root.equals(MainActivity.wordDB)) {
				g = true;
				MainActivity.uuu = MainActivity.unicode;
			}

			else if (match(root, MainActivity.wordDB)) {
				MainActivity.matchedSigns.add(MainActivity.unicode);
				MainActivity.words.add(MainActivity.wordDB);
				MainActivity.lengthword.add(MainActivity.wordDB.length());
				// MainActivity.len.add(MainActivity.word.length());
			}
		}
		return g;
	}

	public static boolean match(String rr, String ww) {
		boolean r = false;
		int counter = 0;

		for (int i = 0; i < rr.length(); i++) {
			String ch = rr.substring(i, i + 1);
			boolean s = ww.contains(ch);
			if (s) {
				counter = counter + 1;
			}
		}
		if (counter >= 3) {
			r = true;
		}
		return r;
	}

	// //////////
	static class FeedReaderDbHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "ASL";
		private static final int DATABASE_VERSION = 200;
		private static final String TABLE_NAME = "sheet 1";
		private static final String UID = "_id";
		private static final String UNICODES = "unicode";
		private static final String WORDS = "categoriesdata";
		private static final String QUERY = "SELECT _id, categoriesdata, unicode FROM sheet 1 WHERE _id=?";
                
		private static final String DROP = "DROP TABLE IF EXISTS" + TABLE_NAME;
		private Context context;

		public FeedReaderDbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			// Message.message(context,"constractor called");
			// Message.message(context, QUERY);
			Log.d("minaSamirDBhelper", QUERY);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			// Message.message(context,"onCreate is called");

			try {
				db.execSQL(QUERY);
				// Message.message(context,"onCreate is called");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Message.message(context, "" + e);
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			try {
				db.execSQL(DROP);
				// Message.message(context,"onUpgrade called");
				onCreate(db);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Message.message(context, "" + e);
			}
		}

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	
  

	
	
}