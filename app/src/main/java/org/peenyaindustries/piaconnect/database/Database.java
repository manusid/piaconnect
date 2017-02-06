package org.peenyaindustries.piaconnect.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import org.peenyaindustries.piaconnect.models.Category;
import org.peenyaindustries.piaconnect.models.Post;
import org.peenyaindustries.piaconnect.utilities.L;

import java.util.ArrayList;

public class Database {

    private DatabaseHelper helper;
    private SQLiteDatabase database;

    public Database(Context context) {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }

    /**
     * Insert Data into SQLite Database
     */
    public void insertCategory(ArrayList<Category> categoryArrayList, Boolean clearPrevious) {

        if (clearPrevious) {
            deleteCategory();
        }

        //create a sql prepared statement
        String sql = "INSERT INTO " + DatabaseHelper.CATEGORY_TABLE + " VALUES (?, ?, ?, ?, ?);";

        //compile the statement and start transaction
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();

        for (int i = 0; i < categoryArrayList.size(); i++) {
            Category category = categoryArrayList.get(i);
            statement.clearBindings();

            statement.bindString(2, category.getCategoryId());
            statement.bindString(3, category.getTitle());
            statement.bindString(4, category.getDescription());
            statement.bindString(5, category.getParent());

            statement.execute();
        }

        //set transaction as successful and end transaction
        //TODO - DELETE
        L.Log("inserted categories size - " + categoryArrayList.size());
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void insertPost(ArrayList<Post> postArrayList, Boolean clearPrevious) {

        if (clearPrevious) {
            deletePost();
        }

        //create a sql prepared statement
        String sql = "INSERT INTO " + DatabaseHelper.POST_TABLE + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        //compile the statement and start transaction
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();

        for (int i = 0; i < postArrayList.size(); i++) {
            Post postModel = postArrayList.get(i);
            statement.clearBindings();

            statement.bindString(2, postModel.getPostId());
            statement.bindString(3, postModel.getCategoryId());
            statement.bindString(4, postModel.getType());
            statement.bindString(5, postModel.getUrl());
            statement.bindString(6, postModel.getStatus());
            statement.bindString(7, postModel.getTitle());
            statement.bindString(8, postModel.getContent());
            statement.bindString(9, postModel.getExcerpt());
            statement.bindString(10, postModel.getDate());
            statement.bindString(11, postModel.getCommentCount());
            statement.bindString(12, postModel.getThumbnailUrl());
            statement.bindString(13, postModel.getImageUrl());

            statement.execute();
        }

        //set transaction as successful and end transaction
        //TODO - DELETE
        L.Log("inserted post size - " + postArrayList.size());
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    /**
     * Read data from SQLite Database
     */
    public ArrayList<Category> readCategory() {

        ArrayList<Category> categoryArrayList = new ArrayList<>();

        String[] column = {DatabaseHelper.C_ID,
                DatabaseHelper.C_TITLE,
                DatabaseHelper.C_DESCRIPTION,
                DatabaseHelper.C_PARENT};

        Cursor cursor = database.query(DatabaseHelper.CATEGORY_TABLE, column, null, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Category categoryModel = new Category();

                    categoryModel.setCategoryId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_ID)));
                    categoryModel.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_TITLE)));
                    categoryModel.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_DESCRIPTION)));
                    categoryModel.setParent(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_PARENT)));

                    categoryArrayList.add(categoryModel);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return categoryArrayList;
    }

    public ArrayList<Post> readPosts() {

        ArrayList<Post> postArrayList = new ArrayList<>();

        String[] column = {DatabaseHelper.P_ID,
                DatabaseHelper.P_CATEGORY_ID,
                DatabaseHelper.P_TYPE,
                DatabaseHelper.P_URL,
                DatabaseHelper.P_STATUS,
                DatabaseHelper.P_TITLE,
                DatabaseHelper.P_CONTENT,
                DatabaseHelper.P_EXCERPT,
                DatabaseHelper.P_DATE,
                DatabaseHelper.P_COMMENT_COUNT,
                DatabaseHelper.P_THUMBNAIL_URL,
                DatabaseHelper.P_IMAGE_URL,};

        Cursor cursor = database.query(DatabaseHelper.POST_TABLE, column, null, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Post postModel = new Post();

                    postModel.setPostId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_ID)));
                    postModel.setCategoryId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_CATEGORY_ID)));
                    postModel.setType(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_TYPE)));
                    postModel.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_URL)));
                    postModel.setStatus(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_STATUS)));
                    postModel.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_TITLE)));
                    postModel.setContent(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_CONTENT)));
                    postModel.setExcerpt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_EXCERPT)));
                    postModel.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_DATE)));
                    postModel.setCommentCount(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_COMMENT_COUNT)));
                    postModel.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_THUMBNAIL_URL)));
                    postModel.setImageUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_IMAGE_URL)));


                    postArrayList.add(postModel);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return postArrayList;
    }

    public ArrayList<Post> readPostsByCategoryId(String categoryId) {

        ArrayList<Post> postArrayList = new ArrayList<>();

        String[] column = {DatabaseHelper.P_ID,
                DatabaseHelper.P_CATEGORY_ID,
                DatabaseHelper.P_TYPE,
                DatabaseHelper.P_URL,
                DatabaseHelper.P_STATUS,
                DatabaseHelper.P_TITLE,
                DatabaseHelper.P_CONTENT,
                DatabaseHelper.P_EXCERPT,
                DatabaseHelper.P_DATE,
                DatabaseHelper.P_COMMENT_COUNT,
                DatabaseHelper.P_THUMBNAIL_URL,
                DatabaseHelper.P_IMAGE_URL,};

        String WHERE = DatabaseHelper.P_CATEGORY_ID + "LIKE ?";
        String args[] = {"%" + categoryId + "%"};

        Cursor cursor = database.query(DatabaseHelper.POST_TABLE, column, args[0], null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Post postModel = new Post();

                    postModel.setPostId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_ID)));
                    postModel.setCategoryId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_CATEGORY_ID)));
                    postModel.setType(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_TYPE)));
                    postModel.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_URL)));
                    postModel.setStatus(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_STATUS)));
                    postModel.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_TITLE)));
                    postModel.setContent(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_CONTENT)));
                    postModel.setExcerpt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_EXCERPT)));
                    postModel.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_DATE)));
                    postModel.setCommentCount(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_COMMENT_COUNT)));
                    postModel.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_THUMBNAIL_URL)));
                    postModel.setImageUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.P_IMAGE_URL)));


                    postArrayList.add(postModel);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return postArrayList;
    }

    /**
     * Delete all data from SQLite Database
     */
    private void deleteCategory() {
        database.delete(DatabaseHelper.CATEGORY_TABLE, null, null);
    }

    public void deletePost() {
        database.delete(DatabaseHelper.POST_TABLE, null, null);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        public static final String CATEGORY_TABLE = "category";
        public static final String POST_TABLE = "post";

        public static final String UID = "_id";
        public static final String C_ID = "categoryId";
        public static final String C_TITLE = "title";
        public static final String C_DESCRIPTION = "description";
        public static final String C_PARENT = "parent";
        public static final String P_ID = "postId";
        public static final String P_CATEGORY_ID = "categoryId";
        public static final String P_TYPE = "type";
        public static final String P_URL = "url";
        public static final String P_STATUS = "status";
        public static final String P_TITLE = "title";
        public static final String P_CONTENT = "content";
        public static final String P_EXCERPT = "excerpt";
        public static final String P_DATE = "date";
        public static final String P_COMMENT_COUNT = "commentCount";
        public static final String P_THUMBNAIL_URL = "thumbnailUrl";
        public static final String P_IMAGE_URL = "imageUrl";

        public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE " + CATEGORY_TABLE + " (" +
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_ID + " TEXT, " +
                C_TITLE + " TEXT, " +
                C_DESCRIPTION + " LONGTEXT, " +
                C_PARENT + " TEXT );";

        public static final String CREATE_POST_TABLE = " CREATE TABLE " + POST_TABLE + " (" +
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                P_ID + " TEXT, " +
                P_CATEGORY_ID + " TEXT, " +
                P_TYPE + " TEXT, " +
                P_URL + " VARCHAR(1000), " +
                P_STATUS + " TEXT, " +
                P_TITLE + " LONGTEXT, " +
                P_CONTENT + " VARCHAR(100000), " +
                P_EXCERPT + " VARCHAR(5000), " +
                P_DATE + " TEXT, " +
                P_COMMENT_COUNT + " TEXT, " +
                P_THUMBNAIL_URL + " VARCHAR(1000), " +
                P_IMAGE_URL + " VARCHAR(1000) );";

        public static final String DROP_CATEGORY_TABLE = "DROP TABLE " + CATEGORY_TABLE + " IF EXISTS";
        public static final String DROP_POST_TABLE = "DROP TABLE " + POST_TABLE + " IF EXISTS";

        private Context context;
        private static String DATABASE_NAME = "piaconnect";
        private static int DATABASE_VERSION = 1;


        /**
         * Constructor of Database Helper Class
         *
         * @param context
         */
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_CATEGORY_TABLE);
                db.execSQL(CREATE_POST_TABLE);
                L.Log("Tables created successfully");
            } catch (SQLException e) {
                //TODO - DELETE
                L.Log("Tables Updated Successfully");
                L.Log("" + e);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_CATEGORY_TABLE);
                db.execSQL(DROP_POST_TABLE);
                //TODO - DELETE
                L.Log("Tables Updated Successfully");
                onCreate(db);
            } catch (SQLException e) {
                //TODO - DELETE
                L.Log("Tables Updated Successfully");
                L.Log("" + e);
            }

        }
    }
}
