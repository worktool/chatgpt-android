package org.yameida.asrassistant.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_layout.*
import org.yameida.asrassistant.R
import org.yameida.asrassistant.utils.AsrUtil
import org.yameida.asrassistant.utils.HttpUtil
import org.yameida.asrassistant.adapter.MyPagerAdapter
import org.yameida.asrassistant.model.ChatMessageBean
import java.text.SimpleDateFormat
import java.util.*


class ChatActivity : FragmentActivity() {

    lateinit var asrUtil: AsrUtil
    var lastFragment: MyFragment? = null
    var sdf = SimpleDateFormat("HH:mm")
    var scrollState = 0
    val REQUEST_RECORD_AUDIO_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        asrUtil = AsrUtil(this) { result, isLast ->
            if (isLast) {
                lastFragment?.mChatAdapter?.apply {
                    val time = sdf.format(Date())
                    if (mData.count { it.type == ChatMessageBean.TYPE_SYSTEM && it.content == time } == 0) {
                        addData(ChatMessageBean(ChatMessageBean.TYPE_SYSTEM, null, null, sdf.format(Date())))
                    }
                    addData(ChatMessageBean(ChatMessageBean.TYPE_SEND, "", "", result))
                    val receivedMessage = ChatMessageBean(ChatMessageBean.TYPE_RECEIVED, "小助手", "", "请稍等...")
                    addData(receivedMessage)
                    lastFragment?.rv_chat?.scrollToPosition(mData.size - 1)
                    lastFragment?.rv_chat?.clearOnScrollListeners()
                    lastFragment?.rv_chat?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                            scrollState = newState
                        }
                    })
                    var index = 0
                    HttpUtil.chat(result, object : HttpUtil.CallBack {
                        override fun onCallBack(result: String, isLast: Boolean) {
                            runOnUiThread {
                                receivedMessage.content = result
                                if ((scrollState == 0 && index % 3 == 0) || isLast) {
                                    updateData()
                                }
                                if ((scrollState == 0 && ++index % 20 == 0) || isLast) {
                                    lastFragment?.rv_chat?.scrollToPosition(mData.size - 1)
                                }
                            }
                        }
                    })
                }
            }
        }
        initView()
        requestAudioPermission()
    }

    private fun requestAudioPermission() {
        val permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        val adapter = MyPagerAdapter(supportFragmentManager)
        lastFragment = MyFragment()
        adapter.addFragment(lastFragment, "Fragment 1")
        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                TODO("Not yet implemented")
            }
            override fun onPageSelected(position: Int) {
//                TODO("Not yet implemented")
            }
            override fun onPageScrollStateChanged(state: Int) {
//                TODO("Not yet implemented")
            }
        })
        bt_asr.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    LogUtils.e("按住结束")
                    asrUtil.onClick(1)
                }
                MotionEvent.ACTION_UP -> {
                    LogUtils.e("按住结束")
                    asrUtil.onClick(2)
                }
            }
            true
        }
        bt_switch.setOnClickListener {
            bt_asr.visibility = View.GONE
            chat_bottom.visibility = View.VISIBLE
        }
        chat_voice.setOnClickListener {
            bt_asr.visibility = View.VISIBLE
            chat_bottom.visibility = View.GONE
        }
        chat_send.setOnClickListener {
            val result = chat_content.text.toString()
            if (result.isNotBlank()) {
                lastFragment?.mChatAdapter?.apply {
                    val time = sdf.format(Date())
                    if (mData.count { it.type == ChatMessageBean.TYPE_SYSTEM && it.content == time } == 0) {
                        addData(ChatMessageBean(ChatMessageBean.TYPE_SYSTEM, null, null, sdf.format(Date())))
                    }
                    addData(ChatMessageBean(ChatMessageBean.TYPE_SEND, "", "", result))
                    val receivedMessage = ChatMessageBean(ChatMessageBean.TYPE_RECEIVED, "小助手", "", "请稍等...")
                    addData(receivedMessage)
                    lastFragment?.rv_chat?.scrollToPosition(mData.size - 1)
                    lastFragment?.rv_chat?.clearOnScrollListeners()
                    lastFragment?.rv_chat?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                            scrollState = newState
                        }
                    })
                    var index = 0
                    HttpUtil.chat(result, object : HttpUtil.CallBack {
                        override fun onCallBack(result: String, isLast: Boolean) {
                            runOnUiThread {
                                receivedMessage.content = result
                                if ((scrollState == 0 && index % 3 == 0) || isLast) {
                                    updateData()
                                }
                                if ((scrollState == 0 && ++index % 20 == 0) || isLast) {
                                    lastFragment?.rv_chat?.scrollToPosition(mData.size - 1)
                                }
                            }
                        }
                    })
                }
            }
            chat_content.text.clear()
        }

    }

}

