package satyajitnets.encrypto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import java.io.IOException;

import dmax.dialog.SpotsDialog;

public class main extends AppCompatActivity {
    TextView tv2,tv3,tv4,tv5,tv6,tv7,tv8,text;
    AlertDialog dialog;
    String ver="1";
   Context con;
    LayoutInflater inflater;
    View layout;
    Toast toast;

    CardView cd,cd2,cd3,cd4;

    MediaPlayer player;
    Animation animV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

con=this;
        overridePendingTransition(R.anim.anim_translate_in, R.anim.anim_translate_out);

        animV= AnimationUtils.loadAnimation(this, R.anim.anim_translate_in);


        Typeface fn3 = Typeface.createFromAsset(getAssets(), "fonts/shop.ttf");
        Typeface fn2 = Typeface.createFromAsset(getAssets(), "fonts/cnd.ttf");



         ActionBar ab = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());

        LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, // Width of TextView
                LayoutParams.WRAP_CONTENT);

        tv.setLayoutParams(lp);


        tv.setText(ab.getTitle()); // ActionBar title text

        tv.setTextColor(Color.WHITE);
tv.setTypeface(fn3);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ab.setCustomView(tv);

        cd=findViewById(R.id.crd);
        cd2=findViewById(R.id.crd2);
        cd3=findViewById(R.id.crd3);
        cd4=findViewById(R.id.crd4);


        tv=findViewById(R.id.en1);
        tv2=findViewById(R.id.en2);
        tv3=findViewById(R.id.en3);
        tv4=findViewById(R.id.en4);
        tv5=findViewById(R.id.t1);
        tv6=findViewById(R.id.t2);
        tv7=findViewById(R.id.t3);
        tv8=findViewById(R.id.t4);

        tv.setTypeface(fn3);
        tv2.setTypeface(fn3);
        tv3.setTypeface(fn3);
        tv4.setTypeface(fn3);
        tv5.setTypeface(fn2);
        tv6.setTypeface(fn2);
        tv7.setTypeface(fn2);
        tv8.setTypeface(fn2);

cd.setAnimation(animV);
        cd2.setAnimation(animV);
        cd3.setAnimation(animV);
        cd4.setAnimation(animV);
//cd.startAnimation(animV);



        //Toast Design
        inflater = getLayoutInflater();

        layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        text = (TextView) layout.findViewById(R.id.TMsg);


        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);


        text.setTypeface(fn3);


//Click Sounds
        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("sounds/click.mp3");
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
        } catch (IOException e) {
            Toast.makeText(this, "Background music file failed to load", Toast.LENGTH_LONG).show();

        }



    }

public void prof(View v){
        clickPlay();
    Intent i = new Intent(this, about.class);

     startActivity(i);
}
    public void update(View v) {
        clickPlay();
new MyTask().execute("");

    }

    private class MyTask extends AsyncTask<String, Integer, String> {




        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {


            // Do something like display a progress bar

            dialog = new SpotsDialog.Builder()
                    .setContext(con)
                    .setTheme(R.style.upd)
                    .build();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();



        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {



            String res="";
            try {


                res=new HttpRequest("http://satyajiit.xyz/enc/upd.php?ver="+ver).prepare(HttpRequest.Method.GET).sendAndReadString();






            } catch (Exception e) {

                Log.d("CSE", String.valueOf(e));
            }

            return res;

        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);



            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (result.contains("NO")) {
                msg("NO UPDATES AVAILABLE!");
            } else if (result.contains("yes")) {

                String web = result.substring(result.indexOf("yes")+3);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(web));
                startActivity(browserIntent);




            } else
                msg("Network Error or Server Down..please Try Again");


            // Do things like hide the progress bar or change a TextView
        }
    }




    void msg(String message) {

        text.setText(message);


        toast.show();
    }

    void clickPlay() {


        //plays click sounds
        if (player.isPlaying())
            player.stop();

        player.start();
    }
public void encr(View v){
    clickPlay();
    Intent i = new Intent(this, encrypt.class);

    startActivity(i);
}
    public void decr(View v){
        clickPlay();
        Intent i = new Intent(this, dec.class);

        startActivity(i);
    }
}
