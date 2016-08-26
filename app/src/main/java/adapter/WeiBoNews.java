package adapter;

import android.widget.Toolbar;

import java.io.ObjectInputStream;

/**
 * Created by 我 on 2016/8/13.
 */
public class WeiBoNews {
  private   int id=0;
    private  String weibo;
    private  String title;
    private String time;
    private String tool;
    private boolean flag=true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
           public boolean     getfFlag(){
                 return  flag;
           }
    public void setTime(String time) {
        this.time = time;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public String getTime() {
        return time;
    }

    public String getTool() {
        return tool;
    }

    public int getId() {
        return id;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
