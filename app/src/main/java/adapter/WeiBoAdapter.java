package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a.toolbardemo.MainActivity;
import a.toolbardemo.R;

/**
 * Created by 我 on 2016/8/12.
 */
public class WeiBoAdapter extends BaseAdapter {
    private Context activity;
    private List<WeiBoNews> lists;
    private  int id;
   private  TextView textView;


    public WeiBoAdapter(Context context, List<WeiBoNews> listdata) {
        this.activity=context;
        this.lists=listdata;
    }

    public  void    NewsAdapter(Activity activity, List<WeiBoNews> newContents){

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
        WeiBoNews newContent= (WeiBoNews) getItem(position);
        VollHolder vollHolder=new VollHolder();
        if (view==null){
         if (newContent.getId()==0)
         {
             System.out.println("adapter   22222222222222222222222222");
             view= LayoutInflater.from(activity).inflate(R.layout.weibo,null);
             vollHolder.textView= (TextView) view.findViewById(R.id.weibo_text);
             vollHolder.textView1= (TextView) view.findViewById(R.id.weibo_text1);
//             vollHolder.textView1.setText(newContent.getWeibo());
             vollHolder.textView.setText(newContent.getTitle());

         }else {
             System.out.println("adapter   11111111111111111111111111");
             view = LayoutInflater.from(activity).inflate(R.layout.weibo_img, null);
             vollHolder.textView= (TextView) view.findViewById(R.id.weibo_text2);
             vollHolder.imageView= (ImageView) view.findViewById(R.id.weibo_img);
              vollHolder.imageView.setImageResource(newContent.getId());
             vollHolder.textView.setText(newContent.getWeibo());
         }


            view.setTag(vollHolder);
        }else {
            vollHolder=(VollHolder) view.getTag();
        }

        return view;
    }



    class VollHolder{
        TextView textView;
        TextView textView1;
        ImageView imageView;

    }
}
