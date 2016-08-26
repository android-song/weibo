package a.toolbardemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.app.SearchManager;
import android.app.backup.BackupAgentHelper;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.SearchAutoComplete;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
//import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Http.GetJson;
import Http.News;
import SQL.SqListedatebase;
import adapter.MyAdapter;
import adapter.NewContent;
import adapter.NewsAdapter;

import static a.toolbardemo.R.color.colorAccent;
import static a.toolbardemo.R.color.colorPrimaryDark;
import static a.toolbardemo.R.color.foreground_material_dark;
import static a.toolbardemo.R.id.cancel_action;
import static a.toolbardemo.R.id.search;
import static a.toolbardemo.R.id.search_src_text;
import static a.toolbardemo.R.id.ss;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{
private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mdrawer;
    private ListView listView;
    private ListView listView1;
    private NewsAdapter arrayAdapter;
    private MyAdapter  arrayAdapter1;
    private List<String> listdata;
    private List<String> listdata1;
    private List<String>  data;
    private String [] data1;
  private   Button  but;
    private    View view;
    private TextView text;
 private FrameLayout frameLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button button;
    private String word;
    private SearchView searchView;
    private List<News> newses;
    private ImageButton imgbutton;
   private  ArrayList<NewContent> newstitle;
    private List<String>  listurls;
    private List<String>  listerl1;
    private IntentFilter intentFilter;
    private MyRecever myRecever;
    private SqListedatebase sqListedatebase =sqListedatebase=new SqListedatebase(this,"weibo.db",null,1);;
    private MainActivity mainActivity;
    private   View layoutInflater;
    private  Button but_load;
private   GetJson  getJson;
    private  Handler handler;


    private int visiblelastindex=0;
    private int visibleitemCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.i("Mianacivity","*****************************************oncrerate");
//        if (savedInstanceState==null){
//            FragmentContent fragmentContent=new FragmentContent();
//            getFragmentManager().beginTransaction().add(R.id.frament,fragmentContent).addToBackStack(null).commit();
//        }

        text=(TextView) findViewById(R.id.text_word);
        Toolbar toolbar=(Toolbar) findViewById(R.id.ss);
        sqListedatebase=new SqListedatebase(this,"weibo.db",null,1);
        mainActivity=this;
//       searchView.setQueryHint("查找");


        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mdrawer=(DrawerLayout) findViewById(R.id.drawerlayout);
           frameLayout=(FrameLayout) findViewById(R.id.frament_slid);
         newstitle = new ArrayList<NewContent>();
        listView1=(ListView) findViewById(R.id.listview);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary,R. color.colorccent);
        arrayAdapter=new NewsAdapter(this,newstitle);
        listView1.setAdapter(arrayAdapter);
        listView1.setTextFilterEnabled(true);//listview需要设置为可过滤的
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                NewContent newContent=listdata.get(position);
//                    String da=newContent.getContent();
                String da=listurls.get(position);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                ListFragment fragment1 =new ListFragment(newstitle,position,getApplicationContext(),da,sqListedatebase);
                transaction.replace(R.id.frament, fragment1);
                transaction.commit();
            }
        });
         layoutInflater=getLayoutInflater().inflate(R.layout.load_more,null);
        but_load= (Button) layoutInflater.findViewById(R.id.but_load);
   ;
        listView1.addFooterView(layoutInflater);



        newses=new ArrayList<News>();
        newses=getnews();
        listView = (ListView)findViewById(R.id.slidng_lsit);

        arrayAdapter1 = new MyAdapter(this,R.layout.sliding_type, newses);
//        View vie= inflater.inflate(R.layout.activity_main,null);



        listView.setAdapter(arrayAdapter1);
        listView.setOnItemClickListener(this);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        getJson.click(but_load);
                        swipeRefreshLayout.setRefreshing(false);
                        arrayAdapter.notifyDataSetChanged();
                        break;
                    case 2:


                        break;
                    default:
                        break;
                }
            }
        };

       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           Thread.sleep(3000);
                           Message message=new Message();
                           message.what=1;
                           message.obj="加载";
                           handler.sendMessage(message);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }).start();

           }
       });


         actionBarDrawerToggle=new ActionBarDrawerToggle(this,mdrawer,toolbar,0,0){
         @Override
         public void onDrawerClosed(View drawerView) {

             super.onDrawerClosed(drawerView);
         }

         @Override
         public void onDrawerOpened(View drawerView) {

             super.onDrawerOpened(drawerView);


         }
     };
        actionBarDrawerToggle.syncState();
        mdrawer.setDrawerListener(actionBarDrawerToggle);
//        jj();



        //网络请求
        getJson= new GetJson(this,text,newstitle,swipeRefreshLayout,arrayAdapter);
        getJson.jj();
        listdata= getJson.getListdata1();
         listurls=getJson.getUrls();
        but_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJson.click(but_load);
            }
        });
//        listerl1=new ArrayList<>();
//        listdata1=new ArrayList<>();
//        for (int i=0;i<5;i++){
//            String url=listurls.get(i);
//          String a=  listdata.get(i);
//            listdata1.add(a);
//            listerl1.add(url);
//        }


//        word=getJson.getdata();
//        text.setText(word);
//        Log.e("444444444444444444444",word);

//      jsoup(word);
    }

    private List<News> getnews() {
        List<News> d=new ArrayList<News>();
        data1 = new String[]{"设置", "收藏", "连接", "个人", "模式", "扣扣"};
        int id=R.mipmap.ic_toggle_star;
        for (int i=0;i<data1.length;i++){
            News n=new News();
            n.setId(id);
            n.setName(data1[i]);
            d.add(n);
        }
        return d;
    }


//    public  void     jj(){
//        String URL="http://daily.zhihu.com/story/";
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        StringRequest stringRequest=new StringRequest(URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Log.e("22222222222222222",s);
//                System.out.println("连接成功");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                System.out.println("连接失败");
//            }
//        });
//        requestQueue.add(stringRequest);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mmm,menu);
//        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem=menu.findItem(R.id.search_menu);
        searchView= (SearchView) MenuItemCompat.getActionView(menuItem);


//        int id=searchView.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
// widget.searchview设置searchview的字体颜色和隐藏的颜色
//        TextView textview=  (TextView) searchView.findViewById(id);

       //v7.widget.searchview中设置字体颜色
        SearchView.SearchAutoComplete searchAutoComplete= (SearchAutoComplete) searchView.findViewById(R.id.search_src_text);

        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//             NewContent newContent= (NewContent) listView1.getAdapter().getItem(1);
//                if (newContent.getContent()!=newText){
//                    listView1.getId().clearTextFilter();
//                }
//                 if (listView1.getId(newText)){
//
//                      listView1.clearTextFilter();
//                 }else {
//                     listView1.setFilterText(newText);
//                 }
                return true;
            }
        });//设置监听事件
        searchView.setSubmitButtonEnabled(true);//设置
        searchView.setQueryHint("查找");//设置提示信息
//        searchView.setIconified(true);//
        searchView.setIconifiedByDefault(true);

//   searchView= (SearchView) menu.findItem(R.id.searchview).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return  true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case R.id.lll:Toast.makeText(this,"lll",Toast.LENGTH_SHORT).show();
                 Intent intent=new Intent(Intent.ACTION_SEND);
                 intent.setType("text/plain");//设置为纯文字
//                 intent.setType("image/");
//               File file=  new File(Environment.getExternalStorageDirectory(),"1.png");//这个是本地的SD卡的图片
//                 Uri uri=Uri.fromFile(file);
//                 intent.putExtra(Intent.EXTRA_SUBJECT,"Share");//分享
//                 intent.putExtra(Intent.EXTRA_STREAM,uri);
//               intent.
                 intent.putExtra(Intent.EXTRA_TEXT,"http://123.206.87.189(分享自andorid_songjp)");//分享的内容
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(Intent.createChooser(intent,getTitle()));
                 break;
             case R.id.action:Toast.makeText(this,"收藏",Toast.LENGTH_SHORT).show();
                 break;

         }
        return super.onOptionsItemSelected(item);
    }
//重点：popBackStackImmediate  自带退栈，而且popBackStack也是退栈，如果两个同时用的话，就会退出两个栈
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {



            if (getFragmentManager().popBackStackImmediate()) {
//                    FragmentManager fragmentManager = getFragmentManager();
//                    fragmentManager.popBackStack();
                    System.out.println("退出");
            } else {
//                System.out.println("完成");
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
               alertDialog.setTitle("是否退出");
               alertDialog.setMessage("清除记录");
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setCancelable(true);

                final Activity activity=this;
                alertDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
                alertDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                  alertDialog.show();
            }
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:    FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            System.out.println("" + position);
            Fragment1 fragment1 = new Fragment1();
            transaction.replace(R.id.frament, fragment1);
            transaction.commit();
              break;
            case 1:
                    FragmentManager fragmentManager1 = getFragmentManager();
                    FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
                    transaction1.addToBackStack(null);
                    System.out.println("" + position);
                    Fragment_save save = new Fragment_save(sqListedatebase,mainActivity);
                    transaction1.replace(R.id.frament, save);
                    transaction1.commit();
                break;
         default:
             break;
        }

        mdrawer.closeDrawer(frameLayout);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
