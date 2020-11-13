package com.example.no2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String > list;

    private RecyclerViewAdapter mAdapter;
    int i = getlength();
//    int i =0;
    private final String mFileName = "test.txt";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData(i);
        mAdapter = new RecyclerViewAdapter(this,list);
        mRecyclerView.setAdapter(mAdapter);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        Button mBtnadd = (Button) findViewById(R.id.btn_add);

        mBtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addData(list.size());
                i++;
                clear();
                save_length(String.valueOf(i));
            }
        });
        Toast.makeText(this,String.valueOf(i), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,String.valueOf(read_length()), Toast.LENGTH_SHORT).show();
    }

    private void initData(int length) {

        list = new ArrayList<>();
        for (i =0 ; i < length; i++) {
            list.add("Todolist" + i);
        }
    }

    private int getlength(){
        if(read_length() == null || Integer.parseInt(read_length()) == 0){
            return 0;

        }else{
            return Integer.parseInt(read_length());
        }
    }

    private void save(List content){
        FileOutputStream fileOutputStream = null;
        try {
//            fileOutputStream = openFileOutput(mFileName,MODE_PRIVATE);
            File dir = new File(Environment.getExternalStorageDirectory(),"list");
            if(!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(dir,mFileName);
            if(!file.exists()){
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream stream = new ObjectOutputStream(fileOutputStream);
            stream.writeObject(content);

            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if(fileOutputStream!= null){
                try {

                    fileOutputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private String read(){
        FileInputStream fileInputStream = null;
        try {
//            fileInputStream = openFileInput(mFileName);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"length",mFileName);
            fileInputStream = new FileInputStream(file);
            byte[] buff = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while((len = fileInputStream.read(buff)) > 0){
                sb.append(new String(buff,0,len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void save_length(String content){
        FileOutputStream fileOutputStream = null;
        try {
//            fileOutputStream = openFileOutput(mFileName,MODE_PRIVATE);
            File dir = new File(Environment.getExternalStorageDirectory(),"length");
            if(!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(dir,mFileName);
            if(!file.exists()){
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream!= null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private void clear(){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"length",mFileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String read_length(){
        FileInputStream fileInputStream = null;
        try {
//            fileInputStream = openFileInput(mFileName);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"length",mFileName);
            fileInputStream = new FileInputStream(file);
            byte[] buff = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while((len = fileInputStream.read(buff)) > 0){
                sb.append(new String(buff,0,len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title = data.getExtras().getString("title");


//        Toast.makeText(this,title, Toast.LENGTH_SHORT).show();
        if (title != null) {
            mAdapter.addItem(title);
        }


    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        adapter.
//    }
}