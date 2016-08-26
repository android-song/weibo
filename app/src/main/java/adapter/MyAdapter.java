package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Http.News;
import a.toolbardemo.R;

/**
 * Created by 我 on 2016/8/14.
 */
public class MyAdapter extends ArrayAdapter {
    private  int rouce;
    public MyAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.rouce=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news= news= (News) getItem(position);
        View view;
        if (convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(rouce, null);
        }else{
            view=convertView;
        }
        ImageView imageView= (ImageView) view.findViewById(R.id.sliding_imgtype);
        TextView textView= (TextView) view.findViewById(R.id.sliding_texttype);
        imageView.setImageResource(news.getId());
           textView.setText(news.getName());
        return view;
    }
}
