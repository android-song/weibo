package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.select.Evaluator;

import java.util.ArrayList;
import java.util.List;

import a.toolbardemo.MainActivity;
import a.toolbardemo.R;

/**
 * Created by 我 on 2016/8/12.
 */
public class NewsAdapter extends BaseAdapter {
    private Activity activity;
    private List<NewContent> lists;
    private  int id;
    private int dd=5;



    public NewsAdapter(MainActivity mainActivity, List<NewContent> listdata) {
        this.activity=mainActivity;
        this.lists=listdata;

    }

    public  void    NewsAdapter(Activity activity, List<NewContent> newContents){

         this.activity=activity;
        this.lists=newContents;


     }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         View view;
        view=convertView;

        NewContent newContent= (NewContent) getItem(position);
        VollHolder vollHolder=new VollHolder();
//        if (view==null){

         if (newContent.getImg()==null)
         {
             view= LayoutInflater.from(activity).inflate(R.layout.list_content2,null);
             vollHolder.textView= (TextView) view.findViewById(R.id.content_text2);
             vollHolder.textView1= (TextView) view.findViewById(R.id.content_text1);
             vollHolder.time= (TextView) view.findViewById(R.id.time_1);

             final TextView d= (TextView) view.findViewById(R.id.pinglun);
             vollHolder.textView1.setText(newContent.getContent());
             vollHolder.textView.setText(newContent.getTitle());
             vollHolder.time.setText(newContent.getTime());
             ImageButton imageButton= (ImageButton) view.findViewById(R.id.img_but);
             imageButton.setOnClickListener(new View.OnClickListener() {
                 private  int i=0;
                 @Override
                 public void onClick(View v) {
                     i++;
                 d.setText("点赞"+i);

                 }
             });
         }else {
             view = LayoutInflater.from(activity).inflate(R.layout.list_content, null);
             vollHolder.textView= (TextView) view.findViewById(R.id.content_text);
              vollHolder.imageView= (ImageView) view.findViewById(R.id.img);
             String imgid=newContent.getImg();
             RequetImg(imgid,vollHolder.imageView);

             vollHolder.textView.setText(newContent.getTitle());
             final TextView d= (TextView) view.findViewById(R.id.pinglun);
             ImageButton imageButton= (ImageButton) view.findViewById(R.id.img_but);
             imageButton.setOnClickListener(new View.OnClickListener() {
                 private  int i=0;
                 @Override
                 public void onClick(View v) {
                     i++;
                     d.setText("点赞"+ i);

                 }
             });
         }


            view.setTag(vollHolder);
//        }else {
//            vollHolder=(VollHolder) view.getTag();
//        }

        return view;
    }

    private void RequetImg(String imgid, final ImageView imageView) {
        RequestQueue requestQueue= Volley.newRequestQueue(activity);

        ImageRequest imageRequest=new ImageRequest(imgid, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                System.out.println("连接成功");
                  imageView.setImageBitmap(bitmap);
            }
        },0,0, Bitmap.Config.RGB_565,new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("连接失败");
            }
        });
        requestQueue.add(imageRequest);

    }


    class VollHolder{
        TextView time;
        TextView textView;
        TextView textView1;
      ImageView imageView;
    }
    public void addItem(String item){
        NewContent newContent=new NewContent();
        newContent.setTitle(item);
        lists.add(newContent);
    }
}
