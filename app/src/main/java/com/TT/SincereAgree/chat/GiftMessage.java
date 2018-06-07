package com.TT.SincereAgree.chat;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Created by 冯雪松 on 2018-03-28.
 */

@MessageTag(value = "app:gift", flag = MessageTag.ISPERSISTED)
public class GiftMessage extends MessageContent {

    private String fromid;
    private String userid;
    private String account;
    //是否为诚意分红包
    private boolean sincerity;
    private String url;
    public GiftMessage() {

    }

    public static GiftMessage obtain(String fromid, String userid, String account, boolean sincerity, String url) {
        GiftMessage giftMessage = new GiftMessage();
        giftMessage.fromid = fromid;
        giftMessage.userid = userid;
        giftMessage.account = account;
        giftMessage.sincerity = sincerity;
        giftMessage.url = url;
        return giftMessage;
    }

    // 给消息赋值。
    public GiftMessage(byte[] data) {

        try {
            String jsonStr = new String(data, "UTF-8");
            JSONObject jsonObj = new JSONObject(jsonStr);
            setFromid(jsonObj.getString("fromid"));
            setUserid(jsonObj.getString("userid"));
            setAccount(jsonObj.getString("account"));
            setSincerity(jsonObj.getBoolean("sincerity"));
            setUrl(jsonObj.getString("url"));
            if (jsonObj.has("user")) {
                setUserInfo(parseJsonToUserInfo(jsonObj.getJSONObject("user")));
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        } catch (UnsupportedEncodingException e1) {

        }
    }

    /**
     * 构造函数。
     *
     * @param in 初始化传入的 Parcel。
     */
    public GiftMessage(Parcel in) {
        setFromid(ParcelUtils.readFromParcel(in));
        setUserid(ParcelUtils.readFromParcel(in));
        setAccount(ParcelUtils.readFromParcel(in));
        setSincerity(Boolean.valueOf(ParcelUtils.readFromParcel(in)));
        setUrl(ParcelUtils.readFromParcel(in));
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<GiftMessage> CREATOR = new Creator<GiftMessage>() {

        @Override
        public GiftMessage createFromParcel(Parcel source) {
            return new GiftMessage(source);
        }

        @Override
        public GiftMessage[] newArray(int size) {
            return new GiftMessage[size];
        }
    };

    /**
     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
     *
     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
     */
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param dest  对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 这里可继续增加你消息的属性

        ParcelUtils.writeToParcel(dest, fromid);
        ParcelUtils.writeToParcel(dest, userid);
        ParcelUtils.writeToParcel(dest, account);
        ParcelUtils.writeToParcel(dest,String.valueOf(sincerity));
        ParcelUtils.writeToParcel(dest,url);
        ParcelUtils.writeToParcel(dest, getUserInfo());

    }

    /**
     * 将消息属性封装成 json 串，再将 json 串转成 byte 数组，该方法会在发消息时调用
     */
    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("fromid", fromid);
            jsonObj.put("userid", userid);
            jsonObj.put("account", account);
            jsonObj.put("sincerity",sincerity);
            jsonObj.put("url", url);
            if (getJSONUserInfo() != null)
                jsonObj.putOpt("user", getJSONUserInfo());

        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isSincerity() {
        return sincerity;
    }

    public void setSincerity(boolean sincerity) {
        this.sincerity = sincerity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

