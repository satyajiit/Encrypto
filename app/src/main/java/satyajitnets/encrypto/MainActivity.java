package satyajitnets.encrypto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;



import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;


import java.io.IOException;

import java.io.UnsupportedEncodingException;


import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    EditText ed,ed2;

    AlertDialog dialog;

    TextView t,t2;
    String link;
    Context con;
    Button b1,b2;
    Animation animIN,animV;
    MediaPlayer player;
    ImageView img;
    Toast toast;
    TextView text;
    String email,pass;
    LayoutInflater inflater;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        getSupportActionBar().hide();
        overridePendingTransition(R.anim.anim_translate_in, R.anim.anim_translate_out);
con=this;

        Typeface fn = Typeface.createFromAsset(getAssets(), "fonts/bp.ttf");
        Typeface fn2 = Typeface.createFromAsset(getAssets(), "fonts/cnd.ttf");
        Typeface fn3 = Typeface.createFromAsset(getAssets(), "fonts/shop.ttf");

        animIN= AnimationUtils.loadAnimation(this, R.anim.anim_translate_in);

        animV= AnimationUtils.loadAnimation(this, R.anim.anim_translate_vertical);

        ed=findViewById(R.id.editText1);

        ed2=findViewById(R.id.editText2);






img=findViewById(R.id.imageView);

        t=findViewById(R.id.textView);
        t2=findViewById(R.id.textView4);

        ed.setTypeface(fn2);
        ed2.setTypeface(fn2);




        t.setTypeface(fn);
        t2.setTypeface(fn);

        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);

        b1.setTypeface(fn3);
        b2.setTypeface(fn3);

       b1.startAnimation(animIN);
        b2.startAnimation(animIN);
        t.startAnimation(animV);
img.startAnimation(animV);




        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("sounds/click.mp3");
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
        } catch (IOException e) {
            Toast.makeText(this, "Background music file failed to load", Toast.LENGTH_LONG).show();

        }



        //Adding functions to ripple effect buttons


        final RippleView rippleView =  findViewById(R.id.more);

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                clickPlay();

                email=String.valueOf(ed.getText());
                pass=String.valueOf(ed2.getText());

                new MyTask().execute("");

            }

        });






        final RippleView rippleView2 =  findViewById(R.id.more2);

        rippleView2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                clickPlay();
                Intent i = new Intent(con, reg.class);

                startActivity(i);
            }

        });




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









//completed styling the xms files and their components



        //check if new user
        SharedPreferences prefs = getSharedPreferences("first_run", MODE_PRIVATE);
        int run = prefs.getInt("run", 0);

        if (run==1) {

            finish();
            Intent i = new Intent(con, main.class);

            startActivity(i);



        }







    }


    void clickPlay() {


        //plays click sounds
        if (player.isPlaying())
            player.stop();

        player.start();
    }








    private class MyTask extends AsyncTask<String, Integer, String> {




        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {


            // Do something like display a progress bar

           dialog = new SpotsDialog.Builder()
                    .setContext(con)
                    .setTheme(R.style.login)
                    .build();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();



        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {



                    String res="";
            try {


                res=new HttpRequest("http://satyajiit.xyz/enc/login.php?email="+email+"&pass="+pass).prepare(HttpRequest.Method.GET).sendAndReadString();






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
            if (result.contains("FAIL")) {
                msg("Invalid Credentials!");
            } else if (result.contains("Success")) {

                    msg("Logged In");
                firstRun();
                finish();
                Intent i = new Intent(con, main.class);

                startActivity(i);




            } else
                msg("Network Error or Server Down..please Try Again");


            // Do things like hide the progress bar or change a TextView
        }
    }




    void msg(String message) {

        text.setText(message);


        toast.show();
    }


    void firstRun() {

        SharedPreferences.Editor editor = getSharedPreferences("first_run", MODE_PRIVATE).edit();
        editor.putString("pass", enc(enc(pass)));
        editor.putInt("run", 1);
        editor.apply();
    }

    String enc(String s){
        byte[] data = new byte[0];
        try {
            data = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64;
    }

}





















