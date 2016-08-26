package a.toolbardemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.zdp.aseo.content.AseoZdpAseo;

/**
 * Created by 我 on 2016/8/9.
 */
public class SecondActivity extends Activity implements Animation.AnimationListener{
    private  View view;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
       view = View.inflate(this,R.layout.actvity_main,null);
        AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
           setContentView(view);
//        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
            initdata();
    }

    private void initdata() {
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.1f,0.3f);
        alphaAnimation.setDuration(2000);
         view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
