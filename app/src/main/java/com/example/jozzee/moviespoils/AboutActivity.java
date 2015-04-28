package com.example.jozzee.moviespoils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class AboutActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //เปิดปุ่ม back บน Action bar
        setContentView(R.layout.activity_about);
        ArrayList<String> listdata_About = new ArrayList<String>();
       String[] info = {"this application","for learning Application development on Android"
               ,"Group members","Anan Panomtreekait\n" +
               "ECP3R 563333013010-8",
                "Passakon Nuanjan\n" +
               "ECP3R 563333013012-4",
                "Sitthiphong Muntrima\n" +
               "ECP3R 563333013016-5",
               "Nitithon Meeying\n" +
               "ECP3R 563333013018-1",
               "Pukanit thuibuengchim\n" +
               "ECP3R 563333013028-0",
               "computer Engineering Faculty of Engineering",
               "Rajamangala University of Technology ISAN Khonkaen Campus",
               "Semester 2/2014"
                };
        ListView setAbout = (ListView)findViewById(R.id.listView1);
        for(int i=0;i<info.length;i++){
            listdata_About.add(info[i]);
        }
        setLisView(listdata_About,setAbout);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        switch (item.getItemId()){//เมื่อกดปุ่ม Back บน Action bar
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
