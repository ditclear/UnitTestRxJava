package com.ditclear.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import rx.Subscriber;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(R.id.text);

        Contributor.loadContributor("square", "retrofit")
                .subscribe(new Subscriber<List<Contributor>>() {

            String name = "";

            @Override
            public void onCompleted() {
                text.setText(name);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Contributor> contributors) {
                StringBuilder builder = new StringBuilder();
                for (Contributor contributor : contributors) {
                    Log.d("c", contributor.login);
                    builder.append(contributor.login + "\n");
                }
                name = builder.toString();
            }
        });

    }

}
