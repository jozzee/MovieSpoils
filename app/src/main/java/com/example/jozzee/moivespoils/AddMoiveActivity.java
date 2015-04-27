package com.example.jozzee.moivespoils;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddMoiveActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Moive");//ใช้สำหรับ set ชื่อ title ที่จะแสดงบน Action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //เปิดปุ่ม back บน Action bar
        setContentView(R.layout.activity_add_moive);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //ทำให้ใช้งาน Internet  ได้
        StrictMode.setThreadPolicy(policy);

        Button Buttn_CreateMoive =(Button)findViewById(R.id.Button_CreateMoiveSpoil);

        Buttn_CreateMoive.setOnClickListener(new View.OnClickListener() { //เมื่อ button create ถูกกด
            @Override
            public void onClick(View v) {
                EditText NameMoive =  (EditText)findViewById(R.id.editText_AddName_create);
                EditText SpoilMoive =  (EditText)findViewById(R.id.editText_AddSpoil_create);
                if(NameMoive.getText().toString().trim().matches("") || SpoilMoive.getText().toString().trim().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill out", Toast.LENGTH_SHORT).show();
                }
                else{

                    String NameMoive_forUp = EncodeData(ENcodeData_Space(NameMoive.getText().toString().trim()));
                    String SpoilMoive_forUp = EncodeData(ENcodeData_Space(SpoilMoive.getText().toString().trim()));
                    String URL_forlistNameMoive ="http://www.fbcredibility.com/cloudobject/usg03/insert/New_Name_Moive?name="+NameMoive_forUp;
                    String URL_forCreatObjectStoreSpoil ="http://www.fbcredibility.com/cloudobject/usg03/insert/" +NameMoive_forUp +"?spoil=" +SpoilMoive_forUp;
                    UpDataToJSON(URL_forlistNameMoive);
                    UpDataToJSON(URL_forCreatObjectStoreSpoil);
                    Toast.makeText(getApplicationContext(), "Add successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_moive, menu);
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
            //Intent about = new Intent(this,AboutActivity.class);
            ////Start Product Activity
            //startActivity(about);
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
