package com.example.jozzee.moivespoils;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SpoilActivity extends MainActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //เปิดปุ่ม back บน Action bar
        String NamePageSpoil =DEcodeData_Space(DecodeData(MainActivity.NameMoive_forpageSpoil));//ใช้สำหรับ set ชื่อ title ที่จะแสดงบน Action bar ให้ตรงตามชื่อหนังที่คลิกเข้ามา
        getSupportActionBar().setTitle(NamePageSpoil);
        setContentView(R.layout.activity_spoil);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final ListView listview_Spoil =(ListView)findViewById(R.id.listView_Spoil);
        ArrayList<String> listdata_Spoil = new ArrayList<String>(); // สร้าง ArrayList เพื่อดึงข้องมูลจาก JSON มาเก็บไว้(โปรเจ็คนี้ JSON ส่งข้อมูลมาเป็นอาเรย์)

        String URL_Spoil = "http://www.fbcredibility.com/cloudobject/usg03/find/"+MainActivity.NID;


        getDataFromJSON(URL_Spoil, "spoil", listdata_Spoil); //funtion ดึงข้อมู๔ลจาก JSON มาเก็บไว้ใน ArrayList
        setLisView(listdata_Spoil,listview_Spoil); //Funtion setListView ให้แสดงผล

        Button Button_Spoil =(Button)findViewById(R.id.Button_Spoil);
        Button_Spoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EditText_AddSpoil = (EditText)findViewById(R.id.EditText_AddSpoil);
                if(EditText_AddSpoil.getText().toString().trim().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill out", Toast.LENGTH_SHORT).show();
                }
                else{

                    String forAddSpoil = EncodeData(ENcodeData_Space(EditText_AddSpoil.getText().toString().trim()));
                    String ObjectSpol =EncodeData(ENcodeData_Space(MainActivity.NameMoive_forpageSpoil));


                    String URL_forAddSpoil ="http://www.fbcredibility.com/cloudobject/usg03/insert/"+MainActivity.NID+"?spoil=" +forAddSpoil;
                    UpDataToJSON(URL_forAddSpoil);
                    Toast.makeText(getApplicationContext(), "Add successfully", Toast.LENGTH_SHORT).show();
                    refresh();

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spoil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ////noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        if(id == R.id.action_About){ //เมื่อกดปุ่ม about จะโชว์หน้า รายชื่อสมาชิกกลุ่ม
            Intent about = new Intent(this,AboutActivity.class);
            //Start Product Activity
            startActivity(about);
            return true;
        }
        switch (item.getItemId()){//เมื่อกดปุ่ม Back บน Action bar
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
