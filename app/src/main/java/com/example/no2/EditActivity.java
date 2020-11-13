package com.example.no2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    private RecyclerViewAdapter adapter1;
    private TextView mTv1,mTv2;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Switch swh_status;
    private int type = 1;
    public static final String EXTRA_MESSAGE = "com.example.list1.MESSAGE";
    private Button mBtnsave, mBtnshow;
    private EditText mEtTitle,mEtBody;
    private String title;

    private final String mFileName = "test.txt";

    public EditActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mEtTitle = (EditText) findViewById(R.id.eT_1);
        mEtBody = (EditText) findViewById(R.id.eT_2);
        mTv1 = (TextView) findViewById(R.id.tv_1) ;

        mTv2 = (TextView) findViewById(R.id.tv_2);



        swh_status = (Switch) findViewById(R.id.sw_1);
        swh_status.setOnCheckedChangeListener(this);
        mSharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mBtnsave = (Button) findViewById(R.id.btn_save);
        mBtnshow = (Button) findViewById(R.id.btn_show);
        Intent intent = getIntent();
        final int pos = intent.getIntExtra("no",0);
        Toast.makeText(this,String.valueOf(pos), Toast.LENGTH_SHORT).show();



        if(read(String.valueOf(pos)) != null ){
            mEtTitle.setText(read(String.valueOf(pos)));
        }
        if (read1(String.valueOf(pos) )!= null){
            mEtBody.setText(read1(String.valueOf(pos)));
        }
        mBtnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mEditor.putString("name",mEtTitle.getText().toString());
//                mEditor.apply();
                save(mEtTitle.getText().toString(),String.valueOf(pos));
                save1(mEtBody.getText().toString(),String.valueOf(pos));
                mTv2.setText(read(String.valueOf(pos))+read1(String.valueOf(pos)));


            }
        });

        mBtnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("title",mEtTitle.getText().toString());
                EditActivity.this.setResult(RESULT_OK,intent);
                EditActivity.this.finish();


//                Intent intent = new Intent();
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("title","芜湖");
//                intent.putExtras(bundle1);
//                setResult(Activity.RESULT_OK,intent);
//                finish();


            }
        });


    }


    private void save1(String content, String position){
        FileOutputStream fileOutputStream = null;
        try {
//            fileOutputStream = openFileOutput(mFileName,MODE_PRIVATE);
            File dir = new File(Environment.getExternalStorageDirectory(),position+"1");
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
    private String read1(String position){
        FileInputStream fileInputStream = null;
        try {
//            fileInputStream = openFileInput(mFileName);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+position+"1",mFileName);
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



    //存储数据
    private void save(String content, String position){
        FileOutputStream fileOutputStream = null;
        try {
//            fileOutputStream = openFileOutput(mFileName,MODE_PRIVATE);
            File dir = new File(Environment.getExternalStorageDirectory(),position);
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

    //读取数据
    private String read(String position){
        FileInputStream fileInputStream = null;
        try {
//            fileInputStream = openFileInput(mFileName);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+position,mFileName);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.sw_1:
                if(buttonView.isChecked()){
                    mTv1.setText("Completed");
                    type =1;
//                    mEditor.putString(getString(position),1);
//                    mEditor.apply();
                }
                else{
                    mTv1.setText("Uncompleted");
                    type = 0;
                }

        }
    }

    public void sendMessage(View view){
        EditText editText = (EditText) findViewById(R.id.eT_1);
        String message = editText.getText().toString();
//        Intent intent = new Intent(this,RecycleViewAdapter.class);
        getIntent().putExtra(EXTRA_MESSAGE, message);

    }
}