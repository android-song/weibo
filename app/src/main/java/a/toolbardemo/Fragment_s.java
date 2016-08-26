package a.toolbardemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 我 on 2016/8/15.
 */
public class Fragment_s  extends Fragment implements View.OnClickListener{
    private String st;
    public Fragment_s(String da){
        this.st=da;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.listcontent,null);
        TextView textView= (TextView) view.findViewById(R.id.svae_text);
        textView.setText("已收藏");
        TextView textView1= (TextView) view.findViewById(R.id.ssclear);
        textView1.setText(st);
        ImageButton button= (ImageButton) view.findViewById(R.id.img_save);
        button.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(),"已收藏,请勿重复操作",Toast.LENGTH_SHORT).show();
    }
}
