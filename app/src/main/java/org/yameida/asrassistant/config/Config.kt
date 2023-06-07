package org.yameida.asrassistant.config

import com.blankj.utilcode.util.SPUtils

object Config {

    /**
     * input your ChatGPT ApiKey
     */
    var apiKey: String
        get() = SPUtils.getInstance().getString("apiKey", "sk-")
        set(value) {
            SPUtils.getInstance().put("apiKey", value)
        }
}