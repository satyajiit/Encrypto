package satyajitnets.encrypto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import dmax.dialog.SpotsDialog;


public class dec extends AppCompatActivity {
    TextView tv1, tv2, tv5, tv6, text;
    AlertDialog dialog;
    String ver = "1";
    Context con;
    LayoutInflater inflater;
    View layout;
    Toast toast;
    int flag = 1;

    Typeface fn2;
    String msg = "";
    InputStream inputStream;
    String filename = "";
    public static final int PICK_IMAGE = 1;
    CardView cd, cd2;
    String enP, pass,pass2;

    MediaPlayer player;
    Animation animV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decr);

con=this;
        overridePendingTransition(R.anim.anim_translate_in, R.anim.anim_translate_out);

        animV = AnimationUtils.loadAnimation(this, R.anim.anim_translate_vertical);

        Typeface fn3 = Typeface.createFromAsset(getAssets(), "fonts/shop.ttf");
        fn2 = Typeface.createFromAsset(getAssets(), "fonts/cnd.ttf");

        ActionBar ab = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        tv.setLayoutParams(lp);


        tv.setText("Decrypt Message"); // ActionBar title text

        tv.setTextColor(Color.WHITE);
        tv.setTypeface(fn3);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ab.setCustomView(tv);

        cd = findViewById(R.id.crd);
        cd2 = findViewById(R.id.crd2);
        cd.setAnimation(animV);
        cd2.setAnimation(animV);
        tv1 = findViewById(R.id.en1);
        tv2 = findViewById(R.id.en2);
        tv1.setTypeface(fn3);
        tv2.setTypeface(fn3);
        tv5 = findViewById(R.id.t1);
        tv6 = findViewById(R.id.t2);
        tv5.setTypeface(fn2);
        tv6.setTypeface(fn2);


        gets();


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

    public void msg(String message) {

        text.setText(message);


        toast.show();
    }

    void clickPlay() {


        //plays click sounds
        if (player.isPlaying())
            player.stop();

        player.start();
    }

    void gets() {
        SharedPreferences prefs = getSharedPreferences("first_run", MODE_PRIVATE);
        String restoredText = prefs.getString("pass", null);
        if (restoredText != null) {
            enP = prefs.getString("pass", "No name defined");//"No name defined" is the default value.
            pass = dec(dec(enP));
        }
    }

    String dec(String s) {
        String text = "";
        byte[] data = Base64.decode(s, Base64.DEFAULT);
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void step1(View v) {
        clickPlay();
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null)

        {

            if (requestCode == PICK_IMAGE) {
                Uri uri = data.getData();
                String path = uri.getPath();
                filename = path.substring(path.lastIndexOf("/") + 1);
                flag=1;
                filename = filename.substring(6);
                try {
                    inputStream = this.getContentResolver().openInputStream(data.getData());
                    msg = inputStream.toString();

                } catch (IOException e) {
                    msg("IO Error");
                }


            }
        } else msg("Please Select Image File.");
    }

    public void step2(View v) {
        clickPlay();
        if (filename == "") msg("Please Complete Step one first!!");
        else {

new MyTask().execute("");
            //ad();
        }
    }


    private class MyTask extends AsyncTask<String, Integer, String> {


        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {


            // Do something like display a progress bar

            dialog = new SpotsDialog.Builder()
                    .setContext(con)
                    .setTheme(R.style.fetch)
                    .build();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {


            String res = "";
            try {

                ByteSource byteSource = new ByteSource() {
                    @Override
                    public InputStream openStream() throws IOException {
                        return inputStream;
                    }
                };

                msg = byteSource.asCharSource(Charsets.UTF_8).read();
                if(msg.indexOf("FUCK_YOU")==-1) flag=3;
                else {
                    msg = msg.substring(msg.indexOf("FUCK_YOU") + 8);

                    enP = msg.substring(0, msg.indexOf("GATES"));

                    pass2 = dec(dec(enP));
                }

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

                chk();

            // Do things like hide the progress bar or change a TextView
        }


    }
    void chk(){
if(flag!=3) {
    if (pass.equals(pass2)) {

        msg("Password Accepted");
        if (flag != 0)
            msg = dec(dec(dec(dec(msg.substring(msg.indexOf("GATES") + 5)))));
        ad();
        flag = 0;
    } else {
        msg("Invalid Authentication");
    }
}
else msg("This image does not contain an encrypted message!");

    }
    public void ad(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("MESSAGE DECRYPTED");
        alertDialog.setMessage("Here is the Decrypted Message : ");
        alertDialog.setCancelable(false);
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lpp);
        input.setTypeface(fn2);
        input.setTextSize(30);
        input.setText(msg);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.user);

        alertDialog.setPositiveButton("OKaY",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        msg=String.valueOf(input.getText());
                    }
                });



        alertDialog.show();



    }

}
