package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a.toolbardemo.MainActivity;
import a.toolbardemo.R;

/**
 * Created by 我 on 2016/8/15.
 */
public class SaveAdapter extends BaseAdapter {
    private List<NewContent> newContents;
    private Activity activity;
    private  NewContent newContent;
    public  SaveAdapter(MainActivity mainActivity,List<NewContent> newContents){
        this.newContents= newContents;
        this.activity=mainActivity;
    }
    @Override
    public int getCount() {
        return newContents.size();
    }

    @Override
    public Object getItem(int position) {
        return newContents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        newContent=newContents.get(position);
//        view=convertView;
//        if (view==null)
//        {
           view=   LayoutInflater.from(activity).inflate(R.layout.save_listitem,null);
            TextView textView= (TextView) view.findViewById(R.id.content_text2);
        TextView textView1= (TextView) view.findViewById(R.id.content_text1);
        textView1.setText(newContent.getTitle());
            textView.setText(newContent.getContent());
//        }

        return view;
    }
}
