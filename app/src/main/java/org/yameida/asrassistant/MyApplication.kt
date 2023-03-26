package org.yameida.asrassistant

import android.app.Application
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.Gson
import com.iflytek.cloud.SpeechUtility
import com.umeng.commonsdk.UMConfigure
import org.yameida.asrassistant.config.GlobalException

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化工具类
        Utils.init(this)
        GsonUtils.setGsonDelegate(Gson())
        //初始化友盟统计
        val key = "6284a3a3d024421570f97c3c"
        val channel = "main_channel"
        UMConfigure.preInit(this, key, channel)
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        if (SPUtils.getInstance().getString("uminit", "1") == "1") {
            UMConfigure.init(this, key, channel, UMConfigure.DEVICE_TYPE_PHONE, "")
        }
        //设置全局异常捕获重启
        Thread.setDefaultUncaughtExceptionHandler(GlobalException.getInstance())

        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符
        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
        LogUtils.e("初始化讯飞")
        val createUtility = SpeechUtility.createUtility(this, "appid=" + "")
        LogUtils.e("初始化讯飞 " + createUtility)
    }

}