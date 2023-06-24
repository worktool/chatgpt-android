package org.yameida.asrassistant.config

import com.blankj.utilcode.util.SPUtils

object Config {

    /**
     * input your ChatGPT ApiKey
     */
    var apiKey: String
        get() = SPUtils.getInstance().getString("apiKey", "")
        set(value) {
            SPUtils.getInstance().put("apiKey", value)
        }

    /**
     * 小助手昵称
     */
    var assistantName: String
        get() = SPUtils.getInstance().getString("assistantName", "小助手")
        set(value) {
            SPUtils.getInstance().put("assistantName", value)
        }

    /**
     * 开启上下文
     */
    var useContext: Boolean
        get() = SPUtils.getInstance().getBoolean("useContext", true)
        set(value) {
            SPUtils.getInstance().put("useContext", value)
        }

    /**
     * GPT模型
     */
    var gptModel: String
        get() = SPUtils.getInstance().getString("gptModel", "gpt-3.5-turbo-16k")
        set(value) {
            SPUtils.getInstance().put("gptModel", value)
        }
}