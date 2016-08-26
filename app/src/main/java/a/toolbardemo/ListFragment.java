package a.toolbardemo;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Http.GetJson;
import SQL.SqListedatebase;
import adapter.NewContent;
import adapter.WeiBoAdapter;
import adapter.WeiBoNews;

/**
 * Created by 我 on 2016/8/13.
 */
@SuppressLint("ValidFragment")
public class ListFragment extends Fragment implements View.OnClickListener{
    private String data;
    private Context context;
    private   ListView listView;
    private WeiBoAdapter weiBoAdapter;
    private List<WeiBoNews> weiBoNewses;
   private TextView textView;
    private ImageButton imageButton;
    private SqListedatebase sqListedatebase;
    private SQLiteDatabase db;
    private  View view;
    private TextView te;
    boolean flag=true;
    private int position;
    private List<NewContent> newContents;
    private List<NewContent> newContents1;
    private Handler handler;
    @SuppressLint("ValidFragment")
    public  ListFragment(ArrayList<NewContent> newstitle, int position, Context context, String s, SqListedatebase sqListedatebase){
        this.data=s;
        this.context=context;
        this.sqListedatebase=sqListedatebase;
        this.position=position;
        this.newContents=newstitle;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.listcontent,null);
        System.out.println("oncreat");
        listView= (ListView) view.findViewById(R.id.urlcontent);
         textView= (TextView) view.findViewById(R.id.toole);
         te= (TextView) view.findViewById(R.id.svae_text);
        imageButton= (ImageButton) view.findViewById(R.id.img_save);
        imageButton.setOnClickListener(this);
        newContents1=new ArrayList<>();
        weiBoNewses=new ArrayList<>();
//        WeiBoNews weiBoNews =new WeiBoNews();
//        weiBoNews.setWeibo("dddddddddddddd");
//        weiBoNewses.add(weiBoNews);
        weiBoAdapter=new WeiBoAdapter(context,weiBoNewses);
        listView.setAdapter(weiBoAdapter);
        GetJson getJson= new GetJson(context,data,weiBoAdapter,weiBoNewses,textView);
        getJson.urlcontent();
      weiBoNewses = getJson.getweibo_title();

  handler=new Handler(){
      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);
          switch (msg.what){
              case 1:
                  break;
                      default:
                          break;
          }
      }
  };
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("ontouch");


    }

    @Override
    public void onClick(View v) {
                 te.setText("已收藏");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                     db = sqListedatebase.getReadableDatabase();
                    Cursor cursor = db.query("weibo", null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        String title = cursor.getString(cursor.getColumnIndex("title"));
                        NewContent newContent = new NewContent();
                        newContent.setTitle(title);
                        newContents1.add(newContent);
                    }
                    cursor.close();
                }catch (Exception e){

                }finally {

                    ContentValues values = new ContentValues();


                    for(WeiBoNews weiBoNews: weiBoNewses) {
                        values.put("title", weiBoNews.getTitle());
                        values.put("content",weiBoNews.getWeibo());

                    }
                    for (NewContent newContent:newContents1) {
                        if (newContent.getTitle().equals( weiBoNewses.get(0).getTitle())) {
//                            Toast.makeText(getActivity(), "已收藏", Toast.LENGTH_SHORT).show();
                            flag=false;
                            System.out.println(flag);
                            break;
                        }
                    }

                    if (flag==true) {
                        System.out.println("333333333333333333"+"insert");
                        db.insert("weibo", null, values);
                    }
                    else {

                    }
                }
            }
        }).start();


    }
}
