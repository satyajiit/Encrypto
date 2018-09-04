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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


import java.io.IOException;

import java.io.UnsupportedEncodingException;





import dmax.dialog.SpotsDialog;

public class reg extends AppCompatActivity {

        Animation animIN,animV;
        EditText ed,ed2,ed3;
        TextView t,t2;
        AlertDialog dialog;
        Button b1,b2;
        ImageView img;
        Context con;
        String link;
        Toast toast;
        TextView text;
        LayoutInflater inflater;
        View layout;
        MediaPlayer player;
        String email,pass2,pass;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_layout);


                getSupportActionBar().hide();

                overridePendingTransition(R.anim.anim_translate_in, R.anim.anim_translate_out);
        Typeface fn = Typeface.createFromAsset(getAssets(), "fonts/bp.ttf");
        Typeface fn2 = Typeface.createFromAsset(getAssets(), "fonts/cnd.ttf");
        Typeface fn3 = Typeface.createFromAsset(getAssets(), "fonts/shop.ttf");

                animIN= AnimationUtils.loadAnimation(this, R.anim.anim_translate_in);

                animV= AnimationUtils.loadAnimation(this, R.anim.anim_translate_vertical);
con=this;


                img=findViewById(R.id.imageView);



        ed = findViewById(R.id.editText12);
        ed2 = findViewById(R.id.editText22);

                ed3=findViewById(R.id.editText32);






                ed3.setTypeface(fn2);






        t = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView4);

        ed.setTypeface(fn2);
        ed2.setTypeface(fn2);
        t.setTypeface(fn);
        t2.setTypeface(fn);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);

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



        }
        @Override
        public void onBackPressed()
        {       clickPlay();
                Intent i = new Intent(this, MainActivity.class);
                finish();
                startActivity(i);
        }

public void regis(View v){
                clickPlay();
        email=String.valueOf(ed.getText());
        pass=String.valueOf(ed2.getText());
        pass2=String.valueOf(ed3.getText());

        if(email.contains("@")==false) msg("Invalid Email ID");
               else if(pass.equals(pass2)==false) msg("Please enter same password in both the fields.");
               else
                      new MyTask().execute("");
}

        public void login(View v){
                Intent i = new Intent(this, MainActivity.class);
                clickPlay();
                finish();

                startActivity(i);
        }

        void clickPlay() {

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
                                .setTheme(R.style.regis)
                                .build();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();



                }

                // This is run in a background thread
                @Override
                protected String doInBackground(String... params) {


                        String res="";
                        try {




                                res=new HttpRequest("http://satyajiit.xyz/enc/fuck.php?email="+email+"&pass="+pass).prepare(HttpRequest.Method.GET).sendAndReadString();







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

                        if (result.contains("Exist")) {
                                msg("UserID Already Exist!!");
                        } else if (result.contains("Success")) {


                                msg("Registered Successfully");
                                        firstRun();
                                finish();
                                Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);

                        } else {


                             msg("Network Error or Server Down..please Try Again");

                        }
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