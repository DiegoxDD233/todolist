package com.example.no2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderA>{
    private Context mContext;
//    private LinkedList<String> mList = new LinkedList<>();
    private List<String> mList;
    private final String mFileName = "test.txt";
    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(Context context,List<String> list){
        mContext = context;
        mList = list;
    }

    public void addItems(List<String> items) {
        mList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(String item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderA onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,parent,false);
        ViewHolderA holderA = new ViewHolderA(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, final int position){
        holder.mTextView.setText(read(String.valueOf(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), EditActivity.class);

                intent.putExtra("no",position);
                ((Activity)v.getContext()).startActivityForResult(intent,1);
//                notifyDataSetChanged();


            }
        });


    }


    class ViewHolderA extends RecyclerView.ViewHolder{
        TextView mTextView;


        public ViewHolderA(View itemView){
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.recycler_textview);


        }
    }
    class ViewHolderB extends RecyclerView.ViewHolder{
        TextView mTextView;


        public ViewHolderB(View itemView){
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.recycler_textview_1);
//            mTextView.setText(read(String.valueOf(getAdapterPosition())));


        }
    }

    @Override
    public int getItemCount(){
        return mList.size();
    }

    public void addData(int position){
        mList.add(position, "Todolist" + position);
        notifyItemInserted(position);
    }
    public void refresh(){
        notifyDataSetChanged();
    }
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
    public int getItemViewType(int position){
        if (read(String.valueOf(position)) == null){
            return  0;
        }else{
            return 1;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;

    }


}
