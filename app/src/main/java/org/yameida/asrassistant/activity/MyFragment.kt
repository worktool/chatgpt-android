package org.yameida.asrassistant.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_layout.*
import org.yameida.asrassistant.R
import org.yameida.asrassistant.adapter.ChatAdapter
import org.yameida.asrassistant.model.ChatMessageBean
import java.util.ArrayList

class MyFragment : Fragment() {
    lateinit var mChatAdapter: ChatAdapter
    var messageBeen: ArrayList<ChatMessageBean> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity ?: return
        mChatAdapter = ChatAdapter(activity, messageBeen)
        mChatAdapter.setHasStableIds(true)
        rv_chat.itemAnimator?.changeDuration = 0
        rv_chat.layoutManager = LinearLayoutManager(context)
        rv_chat.adapter = mChatAdapter
    }
}