package com.shorka.telegramclone_ui.recycle_views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Message;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class ViewSentMessage  extends RecyclerView.ViewHolder  {

    private static final String TAG = "ViewSentMessage";
    private TextView mTxtMessageBody, mTxtTime;

    public ViewSentMessage(View itemView) {
        super(itemView);

//        Log.d(TAG, "ViewSentMessage: constructor init");

        mTxtMessageBody = itemView.findViewById(R.id.text_sent_message_body);
        mTxtTime = itemView.findViewById(R.id.text_sent_message_time);
    }
    public void bind(String messageText, String date){

        Log.d(TAG, "bind: BIND");
        mTxtMessageBody.setText(messageText);
        mTxtTime.setText(date);
    }

}
