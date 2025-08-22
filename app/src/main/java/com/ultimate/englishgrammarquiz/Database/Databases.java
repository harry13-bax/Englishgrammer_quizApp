package com.ultimate.englishgrammarquiz.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ultimate.englishgrammarquiz.model.CategoryModel;
import com.ultimate.englishgrammarquiz.model.LevelModel;
import com.ultimate.englishgrammarquiz.model.OptionModel;
import com.ultimate.englishgrammarquiz.model.QuestionModel;
import com.ultimate.englishgrammarquiz.model.ResultModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class Databases extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "english_test.db";
    private static final String DB_PATH_SUFFIX = "/databases/";

    @SuppressLint("StaticFieldLeak")
    private static Context myContext;

    public Databases(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
    }

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);


        String outFileName = getDatabasePath();


        File f = new File(myContext.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();


        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private static String getDatabasePath() {
        return myContext.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public void openDataBase() throws SQLException {
        File dbFile = myContext.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying success from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Databases.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        try {
            CopyDataBaseFromAsset();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.w(Databases.class.getName(), "Data base is upgraded  ");

    }

    public ArrayList<CategoryModel> getCate() {
        ArrayList<CategoryModel> categoryModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from categories", null);
        while (res.moveToNext()) {
            String id = res.getInt(res.getColumnIndex("id")) + "";
            String name = res.getString(res.getColumnIndex("name"));
            categoryModels.add(new CategoryModel(name, id));
        }
        res.close();
        return categoryModels;
    }

    public ArrayList<LevelModel> getTopics(int cate_id) {
        ArrayList<LevelModel> topicModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = myDataBase.rawQuery("select categories.id, level.categories_id,  level.name from categories inner join level on categories.id = level.categories_id where categories.categories_id = " + cate_id, null);
        Cursor res = db.rawQuery("select level.categories_id, level.name, level.id, level_score.score, level_score.id as levelID from level left join level_score on level_score.level_id = level.id inner join categories on level.categories_id = categories.id where categories.id = "+cate_id, null);
        while (res.moveToNext()) {
            int id = res.getInt(res.getColumnIndex("id"));
            int idCate = res.getInt(res.getColumnIndex("categories_id"));
            String name = res.getString(res.getColumnIndex("name"));
            ArrayList<QuestionModel> questionModels = getQuestion(id);
            LevelModel levelModel = new LevelModel(name, id, idCate);
            Integer i = res.getInt(res.getColumnIndex("score"));
            Integer lID = res.getInt(res.getColumnIndex("levelID"));
            levelModel.setScore(i);
            levelModel.setLevelscore_id(lID);
            levelModel.setSl(questionModels.size());
            topicModels.add(levelModel);
        }
        res.close();
        return topicModels;
    }

    public ArrayList<QuestionModel> getQuestion(int id_que) {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuestionModel> questionModelArrayList = new ArrayList<>();
        Cursor res = db.rawQuery("select * from question where id in ( select question_id from level_question where level_id = " + id_que +" );", null);



        while (res.moveToNext()) {
            QuestionModel questionModel = new QuestionModel();
            questionModel.setId(res.getInt(0)) ;
            questionModel.setContent(res.getString(1));
            questionModelArrayList.add(questionModel);
        }
        res.close();
        return questionModelArrayList;
    }

    public ArrayList<OptionModel> getOption(int id) {
        ArrayList<OptionModel> optionModelArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select option.id,option.question_id,option.content,option.correct from option inner join question on option.question_id = question.id where option.question_id = "+id, null);
        while (res.moveToNext()) {
            id = res.getInt(res.getColumnIndex("id"));
            int questionId = res.getInt(res.getColumnIndex("question_id"));
            String content = res.getString(res.getColumnIndex("content"));
            boolean correct = Boolean.parseBoolean(res.getString(res.getColumnIndex("correct")));
            optionModelArrayList.add(new OptionModel(id, questionId, content, correct));
        }
        res.close();
        return optionModelArrayList;
    }
    public ArrayList<ResultModel> getResult(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ResultModel> resultModelArrayList = new ArrayList<>();
        Cursor res = db.rawQuery("select question.id,question.content,option.content as answer,option.correct from question inner join option on option.question_id = question.id  where question.id in(select  level_question.question_id  from level_question where level_question.level_id = "+ id+ " ) and option.correct=1;",null);
        while (res.moveToNext()){
            id = res.getInt(res.getColumnIndex("id"));
            String content = res.getString(res.getColumnIndex("content"));
            String correctAnswer = res.getString(res.getColumnIndex("answer"));
            boolean correct = Boolean.parseBoolean(res.getString(res.getColumnIndex("correct")));
            resultModelArrayList.add(new ResultModel(id,content,correctAnswer,correct));

        }
        res.close();
        return  resultModelArrayList;
    }
    public void insertToDB(int lId, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("level_id", lId);
            values.put("score", score);
        db.insert("level_score", null, values);

    }
    public void updateLevelScore(int id ,int levelid,int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("score",score);
        db.update("level_score",values,"level_id = "+ levelid,null);
    }

//    public int getBestScorebyLevelID(int levelID) {
//
//        Cursor res = myDataBase.rawQuery("select* from level_score where level_id = "+levelID,null);
//
//        if (res.moveToFirst())
//            return res.getInt(2);
//        res.close();
//        return 0;
//    }





}
