package com.example.oderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.Model.Message;
import com.example.oderapp.R;
import com.example.oderapp.SessionManage.SessionManagement;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static SessionManagement sessionManagement;
    private static int TYPE_LEFT = 1;
    private static int TYPE_RIGHT = 2;
    Context context;

    public MessageAdapter(Context context,  List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }


    List<Message> messageList;


    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (message.isFrom_id_user()){
            return TYPE_LEFT;
        }else{
            return TYPE_RIGHT;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(TYPE_LEFT == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_left,parent,false);
            return  new Message_Left_Holder(view);
        }else if(TYPE_RIGHT == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_right,parent,false);
            return  new Message_right_Holder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message mesage = messageList.get(position);
        if (mesage == null) {
            return;
        }
        if (TYPE_LEFT == holder.getItemViewType()) {
            Message_Left_Holder message_left = (Message_Left_Holder) holder;
            message_left.tv_message_left.setText(mesage.getMessage());
        } else if (TYPE_RIGHT == holder.getItemViewType()) {
            Message_right_Holder message_right = (Message_right_Holder) holder;
            message_right.tv_message_right.setText(mesage.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (messageList != null) {
            return   messageList.size();
        }
        return 0;
    }

    public class Message_right_Holder extends RecyclerView.ViewHolder{
        TextView tv_message_right;

        public Message_right_Holder(@NonNull View itemView) {
            super(itemView);
            tv_message_right = itemView.findViewById(R.id.tv_message_right);

        }
    }
    public class Message_Left_Holder extends RecyclerView.ViewHolder{
        TextView tv_message_left;
        public Message_Left_Holder(@NonNull View itemView) {
            super(itemView);
            tv_message_left = itemView.findViewById(R.id.tv_message_left);
        }
    }
}
