package Http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import a.toolbardemo.R;
import adapter.NewContent;
import adapter.NewsAdapter;
import adapter.WeiBoAdapter;
import adapter.WeiBoNews;

import static com.android.volley.Response.*;

/**
 * Created by 我 on 2016/8/10.
 */
public class GetJson {
    private Context context;
    private     String data="实验数据";
    private     String data1;
    private   String  mirkdown;
    private JSONArray jsonArray;
    private String s;
    private List<String>  listdata1=new ArrayList<>();
    private TextView text;
    private ArrayList<NewContent> listdata;
    private ArrayList<NewContent> listdata_1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsAdapter arrayAdapter;
    private WeiBoAdapter weiBoAdapter;
    private List<String>  listurls=new ArrayList<>();
    private  List<WeiBoNews> weibo_list;
    String URL ="http://daily.zhihu.com/story/";
    String Url1="http://api.kanzhihu.com/getposts/1411261200";
    String URLGETBLOG="http://123.206.87.189";
    private TextView textView;
    private String t;
    private  int k=6;
    private View layoutInflater;

         public    GetJson(Context context, String s, WeiBoAdapter weiBoAdapter, List<WeiBoNews> weiBoNewses,TextView textView){
        this.URLGETBLOG=s;
             this.context=context;
             this.weiBoAdapter=weiBoAdapter;
             this.weibo_list=weiBoNewses;
             this.textView=textView;
    }

    public GetJson(Context applicationContext, TextView t, ArrayList<NewContent> data,
                   SwipeRefreshLayout swipeRefreshLayout, NewsAdapter arrayAdapter) {
        this.context = applicationContext;
         this.text=t;
        this.listdata_1=data;
        this.swipeRefreshLayout=swipeRefreshLayout;
        this.arrayAdapter=arrayAdapter;
    }

    public void jj() {


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest=new StringRequest(URLGETBLOG, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println("122222222"+s);

                jsoup(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("连接失败");
            }
        });
        requestQueue.add(stringRequest);

    }



    public void  jsoup(String word) {
             boolean flag=true;
         listdata=new ArrayList<>();
        System.out.println("11111111111111111111111");
        String html="<p><a href=\"a.html\">a</p><p>wenzi</p>";
        Document document= Jsoup.parse(word);
//        Element element=document.getElementsByTag("title");
//        Elements elements=document.getElementsByTag("title");
//        for (Element element:elements){
//            System.out.println(element.text());
//             s=element.text();
//        }
        Elements elements1=document.select("header.entry-header");
        for (Element element:elements1){

          String  he= element.getElementsByTag("a").first().text();
            Elements time=element.select("time.post-date");
            String time1=time.first().text();
            System.out.println(he);
            NewContent newContent=new NewContent();
                newContent.setTitle(he);
            newContent.setTime(time1);

              listdata.add(newContent);
        }
        Elements elements2=document.select("div.entry-content");
        int i=0;
        int j=0;
        for (Element element:elements2) {

        j++;
            Elements elements = element.select("img");
            for (Element element1:elements){
            StringBuilder stringBuilder1 = new StringBuilder();
            String src = element1.attr("src");
            String srcurl = stringBuilder1.append(URLGETBLOG).append(src).toString();
                System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj"+srcurl+j);
            listdata.get(j).setImg(srcurl);
        }


            String  he1= element.text();
          listdata.get(i++).setContent(he1);
            System.out.println(he1);
            listdata1.add(he1);
        }

        Elements ddd=document.select("div.read-more");
        for (Element d:ddd) {
           Elements element= d.select("a[href]");
            StringBuilder stringBuilder=new StringBuilder();
            String s=element.attr("href");
            stringBuilder.append(URLGETBLOG).append(s);
            String urls=stringBuilder.toString();
         listurls.add(urls);
        }

       for (int r=0;r<6;r++){
           listdata_1.add(listdata.get(r));
           Log.i("dd..","................................"+listdata.get(r).getTitle());
       }

        arrayAdapter.notifyDataSetChanged();
    }

    public List<String> getListdata1() {
        return listdata1;
    }

    public List<String> getUrls() {
        return  listurls;
    }
    public void urlcontent() {


        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest=new StringRequest(URLGETBLOG, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println(s);
                weibo(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("连接失败");
            }
        });
        requestQueue.add(stringRequest);

    }

    private void weibo(String s) {

        Document document=Jsoup.parse(s);
        StringBuilder stringBuilder=new StringBuilder();
        Elements elements1=document.select("p");
        for (Element element:elements1){
//           Elements elements= element.getElementsByTag("br");
                String s1=element.text();
                stringBuilder.append(s1).append("\n");
                System.out.println(s1);
        }
        Elements element3=document.select("header.entry-header");
        int i=0;
        for (Element element:element3){
          t= element.getElementsByTag("a").first().text();
            textView.setText(t);
        }
        WeiBoNews weiBoNews=new WeiBoNews();
        weiBoNews.setTitle(stringBuilder.toString());
        weiBoNews.setWeibo(t);
        weibo_list.add(weiBoNews);

        weiBoAdapter.notifyDataSetChanged();
    }

    public List<WeiBoNews> getweibo_title() {

        return weibo_list;
    }

  public void  click(Button button){

      if (k==listdata.size()){
          Toast.makeText(context,"加载完毕..",Toast.LENGTH_SHORT).show();
          button.setText("全部加载完毕.");

      }else {
      int p=0;
          for ( ; p < 6; p++) {
              if (p + k == listdata.size())
                  break;

              listdata_1.add(listdata.get(p + k));
          }
      k=k+p;
              Log.i("dd..", "................................" + k + listdata_1.get(5).getTitle());


      }
      arrayAdapter.notifyDataSetChanged();
    }
}
