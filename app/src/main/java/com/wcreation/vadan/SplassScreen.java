package com.wcreation.vadan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplassScreen extends AppCompatActivity {

    private ImageView imageView,imgOwner;
    private TextView textView;
    private CardView crdImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splass_screen);

        imageView = (ImageView) findViewById(R.id.img);
        textView = (TextView)findViewById(R.id.txtSplass);
        imgOwner = (ImageView)findViewById(R.id.imgOwnerimage);
        crdImage = (CardView)findViewById(R.id.imgSplass);


        Animation animationimg = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        Animation animationtxt = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transtition);

        crdImage.startAnimation(animationimg);
        textView.startAnimation(animationtxt);
        imgOwner.startAnimation(animation);
        final Intent i = new Intent(this,HomePage.class);
        Thread timer = new Thread()
        {
            public void run()
            {
                try {
                    sleep(4000);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {

                    startActivity(i);
                    finish();

                }
            }
        };
        timer.start();
    }
}
