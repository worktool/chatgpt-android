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
     * 模型代理地址
     */
    var proxyAddress: String
        get() = SPUtils.getInstance().getString("proxyAddress", "https://api.openai.com")
        set(value) {
            SPUtils.getInstance().put("proxyAddress", value)
        }

    /**
     * GPT模型
     */
    var gptModel: String
        get() = SPUtils.getInstance().getString("gptModel", "gpt-4o-mini")
        set(value) {
            SPUtils.getInstance().put("gptModel", value)
        }
}