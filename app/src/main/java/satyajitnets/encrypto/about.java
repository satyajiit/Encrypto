package satyajitnets.encrypto;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        getSupportActionBar().hide();

        Typeface fn2 = Typeface.createFromAsset(getAssets(), "fonts/cnd.ttf");


        TextView name=findViewById(R.id.name);
        TextView opn=findViewById(R.id.opn);
        TextView email=findViewById(R.id.email);
        TextView adr=findViewById(R.id.adr);
        TextView web=findViewById(R.id.web);


        name.setTypeface(fn2);
        opn.setTypeface(fn2);
        email.setTypeface(fn2);
        adr.setTypeface(fn2);
        web.setTypeface(fn2);



        web.setMovementMethod(LinkMovementMethod.getInstance());
        web.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://satyajiit.xyz"));
                startActivity(browserIntent);

            }
        });


        email.setMovementMethod(LinkMovementMethod.getInstance());
        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("mailto:satyajiit0@gmail.com"));
                startActivity(browserIntent);

            }
        });


        ImageView fb=findViewById(R.id.fb);
        ImageView git=findViewById(R.id.git);
        ImageView link=findViewById(R.id.link);



        fb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://www.facebook.com/satyajiit"));
                startActivity(browserIntent);

            }
        });


        git.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://github.com/satyajiit"));
                startActivity(browserIntent);

            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://www.linkedin.com/in/satyajiit"));
                startActivity(browserIntent);

            }
        });


    }
}
