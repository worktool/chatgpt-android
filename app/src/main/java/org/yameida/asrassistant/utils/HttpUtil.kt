package org.yameida.asrassistant.utils

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.google.gson.JsonObject
import okhttp3.*
import org.yameida.asrassistant.config.Config
import org.yameida.asrassistant.model.Message
import org.yameida.asrassistant.model.StreamAiAnswer
import java.io.BufferedReader
import java.io.IOException


object HttpUtil {

    /**
     * ChatGPT
     */
    fun chat(send: String, callback: CallBack) {
        val url = "http://proxy.chat.carlife.host/v1/chat/completions"
        val apiKey = "Bearer ${Config.apiKey}"
        val jsonObject = JsonObject()
        jsonObject.addProperty("model", "gpt-3.5-turbo")
        val body = RequestBody.create(MediaType.parse("application/json"), "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"stream\": true,\n" +
                "  \"messages\": [{\"role\": \"user\", \"content\": \"$send!\"}]\n" +
                "}")
        val request: Request = Request.Builder().url(url).method("POST", body)
            .addHeader("Authorization", apiKey)
            .build()
        OkHttpUtil.okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val bufferedReader = BufferedReader(responseBody.charStream())
                    var line = bufferedReader.readLine()
                    var index = 0
                    val sb = StringBuilder()
                    while (line != null) {
                        val msg = convert(line, "1", index++)
                        if (msg != null) {
                            sb.append(msg.content)
                            callback.onCallBack(sb.toString(), false)
                        }
                        line = bufferedReader.readLine()
                    }
                    callback.onCallBack(sb.toString(), true)
                }
            }
        })
    }

    fun convert(answer: String, questionId: String, index: Int): Message? {
        val msg = Message()
        msg.content = ""
        msg.messageType = "normal"
        msg.id = questionId
        if ("data: [DONE]" != answer) {
            val beanStr = answer.replaceFirst("data: ", "", false)
            val aiAnswer = GsonUtils.fromJson(beanStr, StreamAiAnswer::class.java) ?: return null
            val choices = aiAnswer.choices
            if (choices.isEmpty()) {
                return null
            }
            val stringBuffer = StringBuffer()
            for (choice in choices) {
                if (choice.finish_reason != "stop") {
                    if (choice.delta.content != null) {
                        stringBuffer.append(choice.delta.content)
                    } else {
                        return null
                    }
                }
            }
            msg.content = stringBuffer.toString()
            if (index == 0) {
                if (msg.content == "\n\n") {
                    LogUtils.e("发现开头有两次换行,移除两次换行")
                    return null
                }
            }
        } else {
            msg.type = "stop"
        }
        msg.index = index
        return msg
    }

    interface CallBack {
        fun onCallBack(result: String, isLast: Boolean)
    }
}
