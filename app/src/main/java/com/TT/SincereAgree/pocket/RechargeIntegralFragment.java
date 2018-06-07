package com.TT.SincereAgree.pocket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.pocket.alipay.OrderInfoUtil2_0;
import com.TT.SincereAgree.pocket.alipay.PayResult;
import com.alipay.sdk.app.PayTask;

import java.math.BigDecimal;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by 冯雪松 on 2018-06-01.
 */

public class RechargeIntegralFragment extends Fragment implements View.OnClickListener{
    private int integeralNum;
    int[] integrals = {2000,5000,10000,15000,30000,-1};
    double[] values = {68,168,268,368,568,-1};
    private double integralMul = 30.0; //一元兑换30积分
    private TextView integeralText;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    int selectTab = -1;
    int selectPay = -1;
    LinearLayout selectLayout;
    ImageView selectImg;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    LinearLayout layout6;
    ImageView alipay;
    ImageView wechatpay;
    Button confirm;
    int rechargeNum = -1;
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "20141225xxxx";
    public static final String PID = "2088501624560335";
    private String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCYsRFIKlftGIviOOQCLAGoypXcH+ZS8jtjn6dKbqRdz0S9apHWbrb2TSukyci2Yn9JPBZ2u/Tb5B3AcSFcXY6tGQPFHQpXkuIikRXmmYC0YHFdcLIHNYSD23D/ODUSYsknT4dKISyW0lG+/72lqK+3tRGILtF2JBxwIOlg03og/26QVcgYUKXpRn5QtixdxDtg7EzTwuV/u1dm87FWwPZ3bZaZGS/enG+gxDJPZRbmHm351AKzxTFBJ8lzx/zT0Dv6hm9eL54q1z5y+r2uVgJnH6VeV8iF4OxeTC0VTjap6AhOSpeZFBDVZUOWjB3KzrXR8RKhDewiTBx/AsANUxOfAgMBAAECggEAe9O9DCUqwSCS8Jbqcch2YAhKmAVWqBXBi/1Bkd9AdMNOH755HskSn5PdvPnXR/GBAEc2gHvVCy5n4lTclSdaOY57vDLc3EBGG3pFpIkAE0fTJ8/o0GfaW4RukstmXs62Cqc4NCnLG1Tc7mQ/zESMnBK/cQ1m2MRYO6a1HMzj0jWLTXTbdL24ujKLI9LApMSGRJo9FMaK8UYNwdlCwL/EpzTVVUVZ/bR3pAofzARlzNsY0ddV4JB9L6yr802CnMdI7RRClnBcS/ByFb9GjdJUO2bgAsUhFym3qhCGvOtULwW2yOVdBU0GLmaua5VSDpwveYgE1ZzXs8HL4maqgDwQwQKBgQD2oJgaLujAl91G1VNkNU2JSym/C5Arh/dF1lJFJpoaJIyDTB4H0Cz1gkWPTJ3O0DEfvrxFdvWk4TozrSDR6/vyGvqUnUtPo/F7PEmPPyBapxz+odU70GgxovcZ7W2tvIptWmkRS2NqTeXgUfTygeTEC1uvG+anQpJe2XrAjZ7O1wKBgQCefpXZo9iyW3jMGImLc0CZQ4Q8hM3dhCjNCGr9lgrlxP191jnV3jyp4SPj5EI3Gv1nmdPc85DOhHMCsTOsGUdD+c0ry+rm8kTHBJdrhk5xI5/zFVgpu65prT9Mn0OyUhCbGB8SxBhKOV2vl7qJCcPmxBm9xydlx2w2WmWo9P0weQKBgH8c0ohcORZar2+8r5hsaKQkm0WePhZRo77Y8do2RSLIR6u8ZZX9U87N7/AluwhHAaqs8fiTkZQDia3sw4euq1JjWVeToqdPhjzQG9G65YATtbv/yRllFu7OYbF5UBhFnssx2AlT4898isiNNURwcmoguIaOSNlYVHySdtQbqghNAoGAfpL1NTRGcFngpI3L+pP2OO/UgUhF5+wNcYCRgSb8WPbywjX9RjEPHVPxh6PYxY1GZiBRNBlHwcXA25uMeppNcZnjImDdncZI6u9dxb8ikfQzXrvjBqEd7sMu1BA0zgn/vPyMhD4ab9xUV1DC5YHo8zgUnpyXeA7E+rtCiKXuNXECgYEAqo2F1nLjXQAyfU/BqAJUBtOMka4+LOemDeQpydgcyiBNfwCuI25ovvebnZBJEGQz1jQBTcuF/BNSIAVkU1rrfEFJZDdoJOTY555W/XTuVsV/87qTCabc4EzBZfcVQaS0FwIPHvWKdodo5fHCHfbg9gp9oS8L9ar1sN5p4wLjcuw=";
    public static final String APPID = "2016021001141619";
    private static final int SDK_PAY_FLAG = 1001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "订单支付成功", Toast.LENGTH_SHORT).show();
                        try{
                            sendRequestWithHttpURLConnectionForToken(rechargeNum,true);
                            integeralText.setText(integeralNum+rechargeNum+"");
                            rechargeNum = -1;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rechargeintegral, container, false);
        integeralText = (TextView) view.findViewById(R.id.recharge_integral_text);
        layout1 = (LinearLayout) view.findViewById(R.id.recharge_integral_layout1);
        layout2 = (LinearLayout) view.findViewById(R.id.recharge_integral_layout2);
        layout3 = (LinearLayout) view.findViewById(R.id.recharge_integral_layout3);
        layout4 = (LinearLayout) view.findViewById(R.id.recharge_integral_layout4);
        layout5 = (LinearLayout) view.findViewById(R.id.recharge_integral_layout5);
        layout6 = (LinearLayout) view.findViewById(R.id.recharge_integral_layout6);
        button1 = (Button) view.findViewById(R.id.recharge_integral_button1);
        button2 = (Button) view.findViewById(R.id.recharge_integral_button2);
        button3 = (Button) view.findViewById(R.id.recharge_integral_button3);
        button4 = (Button) view.findViewById(R.id.recharge_integral_button4);
        button5 = (Button) view.findViewById(R.id.recharge_integral_button5);
        button6 = (Button) view.findViewById(R.id.recharge_integral_button6);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        alipay = (ImageView) view.findViewById(R.id.recharge_integrel_ali);
        wechatpay = (ImageView) view.findViewById(R.id.recharge_integral_wechat);
        alipay.setOnClickListener(this);
        wechatpay.setOnClickListener(this);
        confirm = (Button) view.findViewById(R.id.recharge_integral_confirm);
        confirm.setOnClickListener(this);
        Bundle bundle = getArguments();
        integeralNum = bundle.getInt("integralNum");
        integeralText.setText(integeralNum+"");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge_integral_button1:
                if(selectTab == 1){
                    layout1.setBackgroundColor(Color.WHITE);
                    selectTab = -1;
                }else{
                    if(selectLayout != null)
                        selectLayout.setBackgroundColor(Color.WHITE);
                    layout1.setBackgroundResource(R.drawable.rechargeselect);
                    selectLayout = layout1;
                    selectTab = 1;
                }
                break;
            case R.id.recharge_integral_button2:
                if(selectTab == 2){
                    layout2.setBackgroundColor(Color.WHITE);
                    selectTab = -1;
                }else{
                    if(selectLayout != null)
                        selectLayout.setBackgroundColor(Color.WHITE);
                    layout2.setBackgroundResource(R.drawable.rechargeselect);
                    selectLayout = layout2;
                    selectTab = 2;
                }
                break;
            case R.id.recharge_integral_button3:
                if(selectTab == 3){
                    layout3.setBackgroundColor(Color.WHITE);
                    selectTab = -1;
                }else{
                    if(selectLayout != null)
                        selectLayout.setBackgroundColor(Color.WHITE);
                    layout3.setBackgroundResource(R.drawable.rechargeselect);
                    selectLayout = layout3;
                    selectTab = 3;
                }
                break;
            case R.id.recharge_integral_button4:
                if(selectTab == 4){
                    layout4.setBackgroundColor(Color.WHITE);
                    selectTab = -1;
                }else{
                    if(selectLayout != null)
                        selectLayout.setBackgroundColor(Color.WHITE);
                    layout4.setBackgroundResource(R.drawable.rechargeselect);
                    selectLayout = layout4;
                    selectTab = 4;
                }
                break;
            case R.id.recharge_integral_button5:
                if(selectTab == 5){
                    layout5.setBackgroundColor(Color.WHITE);
                    selectTab = -1;
                }else{
                    if(selectLayout != null)
                        selectLayout.setBackgroundColor(Color.WHITE);
                    layout5.setBackgroundResource(R.drawable.rechargeselect);
                    selectLayout = layout5;
                    selectTab = 5;
                }
                break;
            case R.id.recharge_integral_button6:
                if(selectTab == 6){
                    layout6.setBackgroundColor(Color.WHITE);
                    selectTab = -1;
                }else{
                    if(selectLayout != null)
                        selectLayout.setBackgroundColor(Color.WHITE);
                    layout6.setBackgroundResource(R.drawable.rechargeselect);
                    selectLayout = layout6;
                    selectTab = 6;
                }
                break;
            case R.id.recharge_integral_wechat:
                Toast.makeText(getActivity(),"暂不支持微信支付，请使用支付宝支付",Toast.LENGTH_SHORT).show();
                break;
            case R.id.recharge_integrel_ali:
                if(selectPay == -1){
                    alipay.setImageResource(R.drawable.alipay);
                    selectPay = 2;
                } else if(selectPay == 2){
                    alipay.setImageResource(R.drawable.alipayunclick);
                    selectPay = -1;
                }
                break;
            case R.id.recharge_integral_confirm:
                if(selectPay == -1){
                    Toast.makeText(getActivity(),"请选择支付方式",Toast.LENGTH_SHORT).show();
                }else if(selectPay == 2){
                    //支付宝支付
                    if(selectTab == -1){
                        Toast.makeText(getActivity(),"请选择购买积分的数量",Toast.LENGTH_SHORT).show();
                    }else{
                        int integral = integrals[selectTab-1];
                        double value = values[selectTab-1];
                        if(integral == -1){
                            //自定义积分
                            final View view = LayoutInflater.from(getContext()).inflate(R.layout.rechargeforintegral, null);
                            final android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(getContext()).setTitle("自定义积分").setView(view)
                                    .create();
                            final EditText integralNum = (EditText) view.findViewById(R.id.recharge_integral_ziding_custom);
                            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });
                            //添加确定按钮
                            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    String numStr = integralNum.getText().toString();

                                    try{
                                        int num = Integer.parseInt(numStr);
                                        rechargeNum = num;
                                        //截取小数点后两位
                                        BigDecimal bg = new BigDecimal(num/integralMul);
                                        payV2(view,bg.setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
                                    }catch (Exception e){
                                        Toast.makeText(getActivity(),"积分请输入数字",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            dialog.show();
                        }else{
                            rechargeNum = integral;
                            payV2(v,value);
                        }
                    }
                }
        }


    }

    public void sendRequestWithHttpURLConnectionForToken(final int num,final boolean isintegeral) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("accountid", Configure.accountId)
                            .add("num",num+"").add("isintegeral",String.valueOf(isintegeral)).build();
                    Request request = new Request.Builder().url(rootUrl+"pocket/recharge").post(requestBody).build();
                    client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    public void payV2(View v,double num) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(getActivity()).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            getActivity().finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,num);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}