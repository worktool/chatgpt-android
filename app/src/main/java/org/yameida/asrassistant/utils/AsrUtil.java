package org.yameida.asrassistant.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.speech.util.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AsrUtil {
    private static final String TAG = AsrUtil.class.getSimpleName();
    // 语音听写对象
    private final SpeechRecognizer mIat;
    // 语音听写UI
    private final RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private final HashMap<String, String> mIatResults = new LinkedHashMap<>();

    // 引擎类型
    private final String mEngineType = SpeechConstant.TYPE_CLOUD;
    private final String language = "zh_cn";
    private final String resultType = "json";
    private final StringBuffer buffer = new StringBuffer();

    private final CallBack callback;

    public AsrUtil(Context context, AsrUtil.CallBack callback) {
        this.callback = callback;
        // 初始化识别无UI识别对象
        mIat = SpeechRecognizer.createRecognizer(Utils.getApp(), mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(context, mInitListener);
    }

    int ret = 0; // 函数调用返回值

    public void onClick(int type) {
        if (null == mIat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }

        switch (type) {
            // 开始听写
            // 如何判断一次听写结束：OnResult isLast=true 或者 onError
            case 1:
                buffer.setLength(0);
                mIatResults.clear();
                // 设置参数
                setParam();
                boolean isShowDialog = true;
                if (isShowDialog) {
                    // 显示听写对话框
                    mIatDialog.setListener(mRecognizerDialogListener);
                    mIatDialog.show();
                } else {
                    // 不显示听写对话框
                    ret = mIat.startListening(mRecognizerListener);
                    if (ret != ErrorCode.SUCCESS) {
                        showTip("听写失败,错误码：" + ret + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
                    } else {
//                        showTip("开始听写");
                    }
                }
                break;
            // 停止听写
            case 2:
                mIat.stopListening();
//                showTip("停止听写");
                break;
            // 取消听写
            case 3:
                mIat.cancel();
                showTip("取消听写");
                break;
            default:
                break;
        }
    }

    /**
     * 初始化监听器。
     */
    private final InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            }
        }
    };

    /**
     * 听写UI监听器
     */
    private final RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        // 返回结果
        public void onResult(RecognizerResult results, boolean isLast) {
            String result = printResult(results);
            if (callback != null) {
                callback.onCallBack(result, isLast);
            }
        }

        // 识别回调错误
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

    };

    /**
     * 听写监听器。
     */
    private final RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            Log.d(TAG, "onError " + error.getPlainDescription(true));
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            if (resultType.equals("json")) {
                printResult(results);
            }
            if (isLast) {
                StringBuffer resultBuffer = new StringBuffer();
                for (String key : mIatResults.keySet()) {
                    resultBuffer.append(mIatResults.get(key));
                }
                Log.d(TAG, "onResult 结束");
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            showTip("当前正在说话，音量大小 = " + volume + " 返回音频数据 = " + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 显示结果
     * @return
     */
    private String printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
            LogUtils.e("key: " + key, "resultBuffer: " + resultBuffer);
        }
        return resultBuffer.toString();
    }

    private void showTip(final String str) {
        ToastUtils.showLong(str);
    }

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, resultType);

        if (language.equals("zh_cn")) {
            String lag = "mandarin";
            // 设置语言
            Log.e(TAG, "language = " + language);
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        } else {
            mIat.setParameter(SpeechConstant.LANGUAGE, language);
        }
        Log.e(TAG, "last language:" + mIat.getParameter(SpeechConstant.LANGUAGE));

        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
//        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        mIat.setParameter(SpeechConstant.VAD_EOS, "10000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav.
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH,
                Utils.getApp().getExternalFilesDir("msc").getAbsolutePath() + "/iat.wav");
    }

    protected void onDestroy() {
        if (mIat != null) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }

    public interface CallBack {
        void onCallBack(String result, boolean isLast);
    }
}
