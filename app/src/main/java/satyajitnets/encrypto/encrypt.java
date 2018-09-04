package satyajitnets.encrypto;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class encrypt extends AppCompatActivity {
    TextView tv1,tv2, tv3, tv4, tv5, tv6, tv7, tv8, text;
    AlertDialog dialog;
    String ver = "1";

    LayoutInflater inflater;
    View layout;
    Toast toast;
    int flag=0;
    Typeface fn2;
    String msg="";
    InputStream inputStream;
    String filename="";
    public static final int PICK_IMAGE = 1;
    CardView cd, cd2, cd3, cd4;
    String enP,pass;

    MediaPlayer player;
    Animation animV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encr);

        overridePendingTransition(R.anim.anim_translate_in, R.anim.anim_translate_out);

        animV= AnimationUtils.loadAnimation(this, R.anim.anim_translate_vertical);

        Typeface fn3 = Typeface.createFromAsset(getAssets(), "fonts/shop.ttf");
        fn2 = Typeface.createFromAsset(getAssets(), "fonts/cnd.ttf");

        ActionBar ab = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        tv.setLayoutParams(lp);


        tv.setText("Encrypt Your Image"); // ActionBar title text

        tv.setTextColor(Color.WHITE);
        tv.setTypeface(fn3);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ab.setCustomView(tv);

        cd=findViewById(R.id.crd);
        cd2=findViewById(R.id.crd2);
        cd3=findViewById(R.id.crd3);
        cd4=findViewById(R.id.crd4);

        cd.setAnimation(animV);
        cd2.setAnimation(animV);
        cd3.setAnimation(animV);
        cd4.setAnimation(animV);




        tv1=findViewById(R.id.en1);
        tv2=findViewById(R.id.en2);
        tv3=findViewById(R.id.en3);
        tv4=findViewById(R.id.en4);
        tv5=findViewById(R.id.t1);
        tv6=findViewById(R.id.t2);
        tv7=findViewById(R.id.t3);
        tv8=findViewById(R.id.t4);

        tv1.setTypeface(fn3);
        tv2.setTypeface(fn3);
        tv3.setTypeface(fn3);
        tv4.setTypeface(fn3);
        tv5.setTypeface(fn2);
        tv6.setTypeface(fn2);
        tv7.setTypeface(fn2);
        tv8.setTypeface(fn2);



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
public void step1(View v){
    clickPlay();
    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
    getIntent.setType("image/*");

    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    pickIntent.setType("image/*");

    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

    startActivityForResult(chooserIntent, PICK_IMAGE);
}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(data!=null)

        {

            if (requestCode == PICK_IMAGE) {
                Uri uri = data.getData();
                String path = uri.getPath();
                filename = path.substring(path.lastIndexOf("/") + 1);

                filename = filename.substring(6);
                try {
                    inputStream = this.getContentResolver().openInputStream(data.getData());

                } catch (IOException e) {
                    msg("IO Error");
                }


            }
        }
        else msg("Please Select Image File.");
    }


    private void copy(InputStream in, String f) {

       File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), f);

        OutputStream out = null;

        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            msg="FUCK_YOU"+enP+"GATES"+enc(enc(enc(enc(msg))));
            out.write(msg.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Ensure that the InputStreams are closed even if there's an exception.
            try {
                if ( out != null ) {
                    out.close();
                }

                in.close();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
public void step2(View v){
    clickPlay();
        if(filename=="") msg("Please Complete Step one first!!");
        else {

            ad();
        }
}

public void ad(){
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    alertDialog.setTitle("MESSAGE ENCRYPT");
    alertDialog.setMessage("Enter Text to Encrypt to in the image.");
    alertDialog.setCancelable(false);
    final EditText input = new EditText(this);
    LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
    input.setLayoutParams(lpp);
    input.setTypeface(fn2);
    input.setText(msg);
    alertDialog.setView(input);
    alertDialog.setIcon(R.drawable.user);

    alertDialog.setPositiveButton("SAVE",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                msg=String.valueOf(input.getText());
                }
            });



    alertDialog.show();



}

void gets(){
    SharedPreferences prefs = getSharedPreferences("first_run", MODE_PRIVATE);
    String restoredText = prefs.getString("pass", null);
    if (restoredText != null) {
        enP = prefs.getString("pass", "No name defined");//"No name defined" is the default value.
        pass=dec(dec(enP));
    }
}
    String dec(String s){
        String text="";
        byte[] data = Base64.decode(s, Base64.DEFAULT);
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
public void step3(View v){
    clickPlay();
        if(msg=="") msg("Please Complete Step two first!!");
        else {
            ad2();

        }
}
    public void ad2(){
        if(flag==0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("INPUT PASSWORD");
            alertDialog.setMessage("Enter your registered password :");
            alertDialog.setCancelable(false);
            final EditText input = new EditText(this);
            LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lpp);
            input.setTypeface(fn2);

            alertDialog.setView(input);
            alertDialog.setIcon(R.drawable.user);

            alertDialog.setPositiveButton("VERIFY",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String in = String.valueOf(input.getText());
                            if (pass.equals(in)) {
                                msg("Password Accepted");
                                flag = 1;

                            } else {

                                msg("Invalid Password!");
                            }
                        }
                    });


            alertDialog.show();
        }

        else msg("You have already authenticated!");


    }

    public void step4(View v){
        clickPlay();

        if(flag==0) msg("Please validate Step four first!!");
        else {
            copy(inputStream, filename + ".jpg");

            msg("Successfully Generated Image..Check Documents Folder!");
            msg="";
            flag=0;
            pass="";
        }
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
