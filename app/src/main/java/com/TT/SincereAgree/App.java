package com.TT.SincereAgree;

import android.app.Application;
import android.content.Context;

import com.TT.SincereAgree.chat.GiftMessage;
import com.TT.SincereAgree.chat.GiftMessageProvider;
import com.TT.SincereAgree.chat.MyConversationListBehaviorListener;
import com.TT.SincereAgree.chat.MyExtensionModule;
import com.TT.SincereAgree.chat.MyReceiveMessageListener;
import com.TT.SincereAgree.chat.MySendMessageListener;
import com.TT.SincereAgree.chat.RedeceTimeSchedule;
import com.TT.SincereAgree.chat.RongRedPacketMessage;
import com.TT.SincereAgree.chat.RongRedPacketMessageProvider;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;

/**
 * Created by 冯雪松 on 2018-02-01.
 */

public class App extends Application {
    private static Context mContext;

    private String userID = "00000003";
    private String userName = "黎明";

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        RongIM.registerMessageType(RongRedPacketMessage.class);
        RongIM.registerMessageTemplate(new RongRedPacketMessageProvider());
        RongIM.registerMessageType(GiftMessage.class);
        RongIM.registerMessageTemplate(new GiftMessageProvider());
        RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
        RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
            }
        }
        RedeceTimeSchedule redeceTimeSchedule = new RedeceTimeSchedule();
        redeceTimeSchedule.start();
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
    
}
