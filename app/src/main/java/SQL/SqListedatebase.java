package SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by &#x6211; on 2016/8/14.
 */


public class SqListedatebase extends SQLiteOpenHelper {
  private String CREATE_WETBO="create table weibo(" +
          "title text," +
          "content text," +
          "id integer primary key autoincrement" +
          "flag text)";

    private Context context;
    public SqListedatebase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_WETBO);
        Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
