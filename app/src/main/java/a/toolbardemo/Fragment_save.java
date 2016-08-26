package a.toolbardemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import SQL.SqListedatebase;
import adapter.NewContent;
import adapter.NewsAdapter;
import adapter.SaveAdapter;

import static android.content.DialogInterface.*;

/**
 * Created by 我 on 2016/8/14.
 */
public class Fragment_save extends Fragment implements View.OnClickListener{
    private ListView listView;
    private SqListedatebase sqListedatebase;
    private  Cursor cursor;
    private ArrayList<NewContent> newContents;
    private MainActivity activity;
  private   SaveAdapter arrayAdapter;
    private  Handler handler;
 private Button button;
    public Fragment_save(SqListedatebase sqListedatebase, MainActivity mainActivity){
          this.sqListedatebase=sqListedatebase;
        this.activity=mainActivity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_save,null);
        button= (Button) view.findViewById(R.id.clear);
         button.setOnClickListener(this);
        newContents=new ArrayList<>();
        listView= (ListView) view.findViewById(R.id.save_list);
         handler=new Handler(){
            @Override
            public void handleMessage(final Message msg) {

                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                    arrayAdapter = new SaveAdapter(activity,newContents);
                    listView.setAdapter(arrayAdapter);
                        break;
                    case 2:
                        final int p= (int) msg.obj;
                        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                        builder.setMessage("是否删除");
                        builder.setIcon(R.mipmap.ic_toggle_star);
                        builder.setCancelable(false);
                        builder.setTitle("删除");
                        builder.setNegativeButton("否", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setPositiveButton("是", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String t=newContents.get(p).getTitle();
                                newContents.remove(p);
                                SQLiteDatabase db= sqListedatebase.getWritableDatabase();
                              db.delete("weibo","title=?",new String[]{t});
                                arrayAdapter.notifyDataSetChanged();
                            }
                        });
                        builder.show();
                        break;

                }
            }
        };
        System.out.println("onCreateView");
        init();

        int i=0;
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Message message=new Message();
                message.what=2;
                message.obj=position;
                handler.sendMessage(message);
                System.out.println("click1111111111111111111111111111111111");
                return false;
            }
        });

listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewContent newContent=newContents.get(position);
        String da=newContent.getTitle();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        Fragment_s fragment1 =new Fragment_s(da);
        transaction.replace(R.id.frament, fragment1);
        transaction.commit();
    }
});

        return view;
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SQLiteDatabase db= sqListedatebase.getWritableDatabase();
                    cursor= db.query("weibo",null,null,null,null,null,null);
                    Thread.sleep(500);
                    while (cursor.moveToNext()){
                        String title=cursor.getString(cursor.getColumnIndex("title"));
                        String content=cursor.getString(cursor.getColumnIndex("content"));

                        NewContent newContent=new NewContent();
                        newContent.setTitle(title);
                        newContent.setContent(content);
                        newContents.add(newContent);
                    }
                    cursor.close();
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }catch (Exception e){
                    System.out.println("读取失败"+e);
                }

            }
        }).start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");


    }

    @Override
    public void onClick(View v) {
      SQLiteDatabase db=  sqListedatebase.getWritableDatabase();
        db.delete("weibo",null,null);
        newContents.clear();
    arrayAdapter.notifyDataSetChanged();
    }
}
