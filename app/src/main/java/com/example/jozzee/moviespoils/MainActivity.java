package com.example.jozzee.moviespoils;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity{

    static String NameMoive_forpageSpoil;
    static String NID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("All Movies"); //ใช้สำหรับ set ชื่อ title ที่จะแสดงบน Action bar
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //ทำให้ใช้งาน Internet  ได้
        StrictMode.setThreadPolicy(policy);

        final ListView listview_Main =(ListView)findViewById(R.id.ListView_Main);
        ArrayList<String> listdata_Main = new ArrayList<String>(); // สร้าง ArrayList เพื่อดึงข้องมูลจาก JSON มาเก็บไว้(โปรเจ็คนี้ JSON ส่งข้อมูลมาเป็นอาเรย์)

        String URL_Main = "http://www.fbcredibility.com/cloudobject/usg03/find/Nmovie";
        //URL ที่จะดีงข้อมูล รายชื่อหนัง มาแสดง

        getDataFromJSON(URL_Main,"name",listdata_Main); //funtion ดึงข้อมู๔ลจาก JSON มาเก็บไว้ใน ArrayList
        setLisView(listdata_Main,listview_Main); //Funtion setListView ให้แสดงผล


        listview_Main.setOnItemClickListener(new AdapterView.OnItemClickListener() { //เมื่อมีการคลิก ไอเทม (ข้อมูล) บน ListView
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NameMoive_forpageSpoil = EncodeData(ENcodeData_Space((listview_Main.getItemAtPosition(position).toString().trim())));
                NID = String.valueOf(position+1);
                //เป็นการ กำหนดค่าตัวแปร ตาม ไอเท็มที่คลิกบน ListView ในขนะนั้น เพื่อนำไปใช้ใน Spoilclass
                Intent Spoil = new Intent(view.getContext(),SpoilActivity.class);
                startActivity(Spoil);  //ไปที่ classSpoil
            }
        });

        ImageButton AddMoive = (ImageButton)findViewById(R.id.Button_AddMoive); //เมื่อมีการกด button Add_Moive
        AddMoive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddMoive = new Intent(v.getContext(), AddMovieActivity.class);
                startActivity(AddMoive);//ไปที่ classAddMoive
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ////noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) { //ลบออกเพราะว่าในเมนู มันไม่มีแล้ว
        //    return true;
        // }
        if(id == R.id.action_About){ //เมื่อกดปุ่ม about จะโชว์หน้า รายชื่อสมาชิกกลุ่ม
            Intent about = new Intent(this,AboutActivity.class);
            //Start Product Activity
            startActivity(about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //---------------------------------------------------------------------------------------------------------------
    protected String EncodeData(String Encode_data){ //เข้ารหัส UTF-8 เช่น & ก็จะเป็น %26
        String resData = null;
        try {
            resData = URLEncoder.encode(Encode_data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resData;
    }
    protected String DecodeData(String Decode_Data){ //ถอดรหัส UTF-8 เช่น %26 ก็จะเป็น &
        String resData = null;
        try {
            resData = URLDecoder.decode(Decode_Data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resData;
    }
    protected void getDataFromJSON(String Ram_URL, String key,ArrayList Ram_ArrayList){ //เมททอร์อดเพื่อเรียกใช้งานการดึงข้อมูลจาก JSON
        try{
            URL url = new URL(Ram_URL);
            Scanner scan = new Scanner(url.openStream());
            StringBuffer Buf = new StringBuffer();
            while (scan.hasNext()){
                Buf.append(scan.next());
            }
            JSONObject json = new JSONObject(Buf.toString()); //สร้าง JSONObject
            JSONArray ArrayData = json.getJSONArray("data"); //ใช้ JOSONArray ดึงข้อลูล อาเรย์มา

            for(int i=0;i<ArrayData.length();i++){
                JSONObject getData = ArrayData.getJSONObject(i);//สร้างออปเจ็ค เพื่อดึงข้อมูลจากอาเรย์ช่องที่ i
                String Ram_NameMoive = DEcodeData_Space(DecodeData(getData.getString(key))); //getString มาจาก Object ที่ Array ช่องที่ i แล้วนำไปถอดรหัส เพื่อให้ได้ข้อความที่สมบูณออกมา
                Ram_ArrayList.add(Ram_NameMoive); //เพิ่มข้อมูลที่ดึงมาไปเก็บไว้ใน ArrayList
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    protected String getID(String Ram_URL){
        String id = null;
        try{
            URL url = new URL(Ram_URL);
            Scanner scan = new Scanner(url.openStream());
            StringBuffer Buf = new StringBuffer();
            while (scan.hasNext()){
                Buf.append(scan.next());
            }
            JSONObject json = new JSONObject(Buf.toString()); //สร้าง JSONObject
            JSONArray ArrayData = json.getJSONArray("data"); //ใช้ JOSONArray ดึงข้อลูล อาเรย์มา
            id = String.valueOf(ArrayData.length()+1);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }
    protected void UpDataToJSON(String UP_URL){ //function สำหรับ ส่งข้อมูลขึ้น sever
        try{
            HttpGet getHTTP = new HttpGet(UP_URL);
            HttpClient clientHTTP = new DefaultHttpClient();
            clientHTTP.execute(getHTTP);
            Toast.makeText(getApplicationContext(), "Add successfully", Toast.LENGTH_SHORT).show();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void setLisView(ArrayList Ram_ArrayList,ListView Ram_ListView){ //function set ListView เอา ArrayList มาใส่ ListView
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Ram_ArrayList);
        Ram_ListView.setAdapter(itemsAdapter);

    }
    //จะอัพข้อมูลขึ้นไป ต้องเข้ารหัส เปลี่ยนเว้นวรรคเป็น _ ซะก่อน แล้วจึงไปเข้ารหัส UTF-8 แล้วค่อยอัพขึ้น
    //จะดึงข้อมมูล ให้ถอดรัส UTF-8 ก่อน แล้วค่อยถอดรหัส แล้วค่อยถอดเว้นวรรค แล้วค่อยเอาไปใส่ใน String
    protected String ENcodeData_Space(String ENcode_Data_Space) {
        String resData = ENcode_Data_Space;
        if(ceck(ENcode_Data_Space," ")){ //เช็กว่า String ที่รับมา มีเว้นวรรคหรือไม่ ก่อนจะเข้ารหัสเว้นวรรค
            int a = 0;
            String s[] = ENcode_Data_Space.split(" ");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < s.length - 1; i++) {
                sb.append(s[i]);
                sb.append("_");
                a = i;
            }
            sb.append(s[a + 1]);
            resData = sb.toString();
        }
        return resData;
    }
    protected String DEcodeData_Space(String Decode_Data_Space){

        String resData = Decode_Data_Space;
        if(ceck(Decode_Data_Space,"_")) { //เช็กว่า String ที่รับมา ม _ หรือไม่ ก่อนจะถอดรหัสเว้นวรรค
            int a = 0;
            String s[] = Decode_Data_Space.split("_");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < s.length - 1; i++) {
                sb.append(s[i]);
                sb.append(" ");
                a = i;
            }
            sb.append(s[a + 1]);
            resData = sb.toString();
        }
        return resData;
    }
    protected boolean ceck(String inputCeckString,String Space_or_Underscore){
        char ceck = Space_or_Underscore.charAt(0);
        boolean sdf = false;
        for(int i=0;i<inputCeckString.length();i++){
            char ceck2 = inputCeckString.charAt(i);
            if(ceck == ceck2){
                sdf = true;
            }
        }

        return sdf;
    }
    protected void refresh(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
