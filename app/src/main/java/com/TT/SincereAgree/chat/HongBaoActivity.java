package com.TT.SincereAgree.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class HongBaoActivity extends AppCompatActivity {
    TextView message;
    TextView amount;
    Button button_send_hongbao;
    RadioButton sincerity_button;
    RadioButton integral_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hong_bao);
        message = (TextView) findViewById(R.id.chat_hongbao_text);
        amount = (TextView) findViewById(R.id.chat_hongbao_num);
        button_send_hongbao = (Button) findViewById(R.id.chat_send_hongbao);
        sincerity_button = (RadioButton) findViewById(R.id.sincerity_redpacket);
        button_send_hongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();
                String amt = amount.getText().toString();
                boolean sincerity = sincerity_button.isChecked();
                /*
                Intent intent = new Intent();
                intent.putExtra("num",num);
                setResult(RESULT_OK,intent);
                */
                RongRedPacketMessage rongRedPacketMessage = RongRedPacketMessage.obtain(Configure.accountId, Configure.chatToId, msg,amt,sincerity);


                RongIM.getInstance().getRongIMClient().sendMessage(Conversation.ConversationType.PRIVATE, Configure.chatToId, rongRedPacketMessage, null, null, new RongIMClient.SendMessageCallback() {
                    @Override
                    public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                        Log.e("RongRedPacketProvider", "-----onError--" + errorCode);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.e("RongRedPacketProvider", "-----onSuccess--" + integer);
                    }
                });

                finish();
            }
        });

    }
}
