package a.toolbardemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 我 on 2016/8/8.
 */
public class FragmentCont extends Fragment{
private ListView listView;
    private ArrayAdapter arrayAdapter1;
    private DrawerLayout mdrawer;
    private String [] data1;
    private  View view;
    private TextView textView;
    private FrameLayout frameLayout;
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.hander, null);
//          textView=(TextView) view.findViewById(R.id.sliding_text);

        return view;


    }



}
