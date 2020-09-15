package com.example.expressapp.utils;

import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCount extends CountDownTimer {
    private Button btn;
    public TimeCount(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture,countDownInterval);//传递参数给父类
        this.btn = btn;
    }
    @Override
    public void onTick(long millisUntilFinished) {
        //计时60秒内不允许点击并提供剩余时间提示
        btn.setClickable(false);
        btn.setText(millisUntilFinished / 1000 + "秒后\n重新获取");
    }

    @Override
    public void onFinish() {
        //计时完毕后设置可以点击
        btn.setClickable(true);
        btn.setText("发送验证码");
    }
}
