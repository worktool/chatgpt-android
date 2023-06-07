# chatgpt-android

## 应用简介

本应用集成 ChatGPT API，使用模型为 gpt-3.5-turbo，项目代码为 Kotlin 语言开发的安卓应用。

本应用旨在为开发者快速集成 ChatGPT 并将响应结果流式呈现在客户端，导入本应用到您的项目中以最快体验语音交互。

人机交互的趋势已经到来，本应用框架也希望能帮助更多开发者快速集成 ChatGPT 体验到人机交互的乐趣！

## 快速使用

项目使用前，您应提前准备：
1. 已申请 ChatGPT ApiKey （必须）
2. 已申请科大讯飞Android SDK （可选，ASR能力）

克隆本项目代码到本地，使用 Android Studio 打开后：
1. 填写ChatGPT ApiKey
```
  org.yameida.asrassistant.config.Config 填写您的ApiKey
  详请参考OpenAI官网申请账号和ApiKey
```

2. 讯飞资源使用
```
  科大讯飞的SDK需要自己重新下载替换 目录为 app/libs
  在AndroidManifest.xml中添加
  <meta-data android:name="TD_APP_ID" android:value="xxxxxx" />
  <meta-data android:name="TD_CHANNEL_ID" android:value="xxx" />
  修改org.yameida.asrassistant.MyApplication中的appid
  val createUtility = SpeechUtility.createUtility(this, "appid=" + "xxxxxx")
  详请参考科大讯飞文档中心
```

如果已经有 ChatGPT ApiKey，直接下载应用可在应用内配置。
APP 下载地址：https://github.com/worktool/chatgpt-android/releases/download/1.1/chatgpt_1.1.apk

## 功能展示
使用 OkHttp 进行流式请求 ChatGTP API 并即时更新客户端UI。


<p align="center">
<img src="https://github.com/worktool/chatgpt-android/blob/master/images/testviewo1.gif"  height="500" width="220">
<img src="https://github.com/worktool/chatgpt-android/blob/master/images/asrdemo1.png"  height="500" width="290">
<img src="https://github.com/worktool/chatgpt-android/blob/master/images/asrdemo2.png"  height="500" width="290">
</p>

## 贡献代码

欢迎 PR，或参考我们的开发者团队贡献代码。提交代码请务必有清晰的代码注释或更新描述。

## Copyright

[MIT](http://www.opensource.org/licenses/MIT)

本项目一套全部开源的安卓端快速集成 ChatGPT 平台，毫无保留给个人及企业免费使用。

## 联系方式

可优先使用 issues，但请先了解一定的提问格式并提供尽可能详细的描述信息。

Email: gallonyin@163.com

开发者交流群: 741127157
