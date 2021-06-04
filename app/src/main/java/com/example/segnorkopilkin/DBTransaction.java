package com.example.segnorkopilkin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.segnorkopilkin.ui.chart.Transaction;

import java.io.Serializable;
import java.util.ArrayList;

public class DBTransaction implements Serializable {
    public static final String DATABASE_NAME = "transactions.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "transactions";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPIENT = "recipient";
//    public static final String COLUMN_RECIPIENT_IV = "recipient_iv";
    public static final String COLUMN_SENDER = "sender";
//    public static final String COLUMN_SENDER_IV = "sender_iv";
    public static final String COLUMN_SUM = "sum";
//    public static final String COLUMN_SUM_IV = "sum_iv";
    public static final String COLUMN_DATE = "date";
//    public static final String COLUMN_DATE_IV = "date_iv";

    //    public static final int NUM_COLUMN_ID = 0;
//    public static final int NUM_COLUMN_RECIPIENT = 1;
//    public static final int NUM_COLUMN_RECIPIENT_IV = 2;
//    public static final int NUM_COLUMN_SENDER = 3;
//    public static final int NUM_COLUMN_SENDER_IV = 4;
//    public static final int NUM_COLUMN_SUM = 5;
//    public static final int NUM_COLUMN_SUM_IV = 6;
//    public static final int NUM_COLUMN_DATE = 7;
//    public static final int NUM_COLUMN_DATE_IV = 8;
    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_RECIPIENT = 1;
    public static final int NUM_COLUMN_SENDER = 2;
    public static final int NUM_COLUMN_SUM = 3;
    public static final int NUM_COLUMN_DATE = 4;
//    public static final String ALIAS = COLUMN_ID + COLUMN_RECIPIENT + COLUMN_SENDER + COLUMN_SUM + COLUMN_DATE;

    private static SQLiteDatabase database;
//    private EnCryptor enCryptor;
//    private DeCryptor deCryptor;

    public DBTransaction(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        database = openHelper.getWritableDatabase();
//        enCryptor = new EnCryptor();
//        try {
//            deCryptor = new DeCryptor();
//        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
//            e.printStackTrace();
//        }
    }

    public void deleteAll() {
        database.delete(TABLE_NAME, null, null);
    }

    public void delete(int id) {
        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void insert(String recipient, String sender, float sum, Long date) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RECIPIENT, recipient);
//        cv.put(COLUMN_RECIPIENT_IV, encryptorText(recipient)[1]);
        cv.put(COLUMN_SENDER, sender);
//        cv.put(COLUMN_SENDER_IV, encryptorText(sender)[1]);
        cv.put(COLUMN_SUM, sum);
//        cv.put(COLUMN_SUM_IV, encryptorText(String.valueOf(sum))[1]);
        cv.put(COLUMN_DATE, date);
//        cv.put(COLUMN_DATE_IV, encryptorText(String.valueOf(date))[1]);
        database.insert(TABLE_NAME, null, cv);
    }

    public Transaction select(int id) {
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();

//        String recipient = decryptorText(new String[]{cursor.getString(NUM_COLUMN_RECIPIENT), cursor.getString(NUM_COLUMN_RECIPIENT_IV)});
//        String sender = decryptorText(new String[]{cursor.getString(NUM_COLUMN_SENDER),cursor.getString(NUM_COLUMN_SENDER_IV)});
//        float sum = Float.parseFloat(decryptorText(new String[]{cursor.getString(NUM_COLUMN_SUM),cursor.getString(NUM_COLUMN_SUM_IV)}));
//        Long date = Long.getLong(decryptorText(new String[]{cursor.getString(NUM_COLUMN_DATE),cursor.getString(NUM_COLUMN_DATE_IV)}));
        String recipient = cursor.getString(NUM_COLUMN_RECIPIENT);
        String sender = cursor.getString(NUM_COLUMN_SENDER);
        float sum = cursor.getFloat(NUM_COLUMN_SUM);
        Long date = cursor.getLong(NUM_COLUMN_DATE);
        return new Transaction(id, sender, recipient, sum, date);
    }

    public ArrayList<Transaction> selectAll() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Transaction> transactions = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
            do {
                int id = cursor.getInt(NUM_COLUMN_ID);
//                String recipient = decryptorText(new String[]{cursor.getString(NUM_COLUMN_RECIPIENT), cursor.getString(NUM_COLUMN_RECIPIENT_IV)});
//                String sender = decryptorText(new String[]{cursor.getString(NUM_COLUMN_SENDER), cursor.getString(NUM_COLUMN_SENDER_IV)});
//                float sum = Float.parseFloat(decryptorText(new String[]{cursor.getString(NUM_COLUMN_SUM), cursor.getString(NUM_COLUMN_SUM_IV)}));
//                Long date = Long.getLong(decryptorText(new String[]{cursor.getString(NUM_COLUMN_DATE), cursor.getString(NUM_COLUMN_DATE_IV)}));
                String recipient = cursor.getString(NUM_COLUMN_RECIPIENT);
                String sender = cursor.getString(NUM_COLUMN_SENDER);
                float sum = cursor.getFloat(NUM_COLUMN_SUM);
                Long date = cursor.getLong(NUM_COLUMN_DATE);
                transactions.add(new Transaction(id, sender, recipient, sum, date));
            } while (cursor.moveToNext());
        cursor.close();
        return transactions;

    }

//    private String[] encryptorText(String text) {
//        try {
//            return new String[]{Base64.encodeToString(enCryptor.encryptText(ALIAS, text), Base64.NO_PADDING), Base64.encodeToString(enCryptor.getIv(), Base64.NO_PADDING)};
//        } catch (UnrecoverableEntryException | NoSuchAlgorithmException | KeyStoreException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | IOException | InvalidAlgorithmParameterException | SignatureException | BadPaddingException | IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }
//        return new String[]{};
//    }
//
//    private String decryptorText(String[] b) {
//        try {
//            System.out.println(Arrays.toString(b));
//            return deCryptor.decryptData(ALIAS, Base64.decode(b[0],Base64.NO_PADDING),Base64.decode(b[1],Base64.NO_PADDING));
//        } catch (UnrecoverableEntryException | NoSuchAlgorithmException | KeyStoreException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | IOException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    private class OpenHelper extends SQLiteOpenHelper {


        public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
//            String query = "CREATE TABLE " + TABLE_NAME + " (" +
//                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_RECIPIENT + " BLOB, " +
//                    COLUMN_RECIPIENT_IV + " BLOB, " +
//                    COLUMN_SENDER + " BLOB, " +
//                    COLUMN_SENDER_IV + " BLOB, " +
//                    COLUMN_SUM + " BLOB, " +
//                    COLUMN_SUM_IV + " BLOB, " +
//                    COLUMN_DATE + "BLOB," +
//                    COLUMN_DATE_IV + " BLOB);";
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RECIPIENT + " TEXT, " +
                    COLUMN_SENDER + " TEXT, " +
                    COLUMN_SUM + " FLOAT, " +
                    COLUMN_DATE + " TEXT);";

            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }
}
