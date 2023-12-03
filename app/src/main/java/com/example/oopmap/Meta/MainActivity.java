package com.example.oopmap.Meta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oopmap.Crits.Predator;
import com.example.oopmap.Crits.VegCreature;
import com.example.oopmap.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Bitmap newBmp=Bitmap.createBitmap(600,600, Bitmap.Config.ARGB_8888);
        LProvider.x=50;
        LProvider.y=50;
        final Canvas newConv=new Canvas(newBmp);
        Paint black=new Paint();
        black.setARGB(255,0,0,0);
        newConv.drawRect(0,0,600,600,black);
        final TileMap newMap=new TileMap();
        final TextView textView=findViewById(R.id.textView);
        newMap.updateMap();
        final CreatureMap newCrits=new CreatureMap(newMap);
        newConv.drawRect(0,0,600,600,black);
        ((ImageView)findViewById(R.id.imageView)).setImageBitmap(newBmp);
        ((ImageView)findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timer mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int i = 0;
                        while ((i < 1)&&(!LProvider.paused)) {
                            newMap.updateMap();
                            newCrits.update();
                            i++;
                        }
                        int ms=20;

                        if (LProvider.real)
                        {
                            ms=50;
                        }
                        int in;
                       in = -20;
                       if (LProvider.real)
                       {
                           in=-50;
                       }
                        while (in < ms) {
                            int jn = -20;

                            if (LProvider.real)
                            {
                                jn=-50;
                            }
                            while (jn < ms) {
                     /*   in = 0;
                        while (in < 100) {
                            int jn = 0;
                            while (jn < 100){*/
                                i=in+LProvider.x;
                                int j=jn+LProvider.y;
                                newConv.drawRect(LProvider.getProjection(6 * in), LProvider.getProjection(6 * jn), LProvider.getProjection(6 * in + 6),LProvider.getProjection( 6 * jn + 6), newMap.getTile(i, j).getColor());
                                if (newCrits.getCreature(i, j) != null) {
                                    Paint np = newCrits.getCreature(i, j).getColor();
                                    newConv.drawOval(LProvider.getProjection(6 * in+1), LProvider.getProjection(6 * jn+1), LProvider.getProjection(6 * in + 5),LProvider.getProjection( 6 * jn + 5), np);
                                    if ((in==0)&&(jn==0))
                                        LProvider.text=("Class:"+newCrits.getCreature(i, j).getClass().getSimpleName()+" Hunger:"+newCrits.getCreature(i, j).getHunger()
                                                        +" Lifetime:"+newCrits.getCreature(i, j).getLifetime()+" Child amount:"+newCrits.getCreature(i, j).getChildAmount());
                                }
                                else  if ((in==0)&&(jn==0)) LProvider.text=("empty");
                                jn++;
                            }
                            in++;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(newBmp);
                                textView.setText(LProvider.text);
                            }
                        });
                    }


                }, 0, 50);
            }
    });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LProvider.y=(LProvider.y+1)%LProvider.MAX_SIZE;
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LProvider.x=(LProvider.x-1+LProvider.MAX_SIZE)%LProvider.MAX_SIZE;
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LProvider.x=(LProvider.x+1)%LProvider.MAX_SIZE;
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LProvider.y=(LProvider.y-1+LProvider.MAX_SIZE)%LProvider.MAX_SIZE;
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LProvider.real=!LProvider.real;
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LProvider.paused=!LProvider.paused;
            }
        });
    }

    private Activity getActivity() {
        return this;
    }

}
