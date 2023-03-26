package org.yameida.asrassistant.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import org.yameida.asrassistant.R
import org.yameida.asrassistant.adapter.base.RvMultiAdapter
import org.yameida.asrassistant.adapter.base.RvViewHolder
import org.yameida.asrassistant.model.ChatMessageBean

class ChatAdapter(val context: Context, val data: List<ChatMessageBean>): RvMultiAdapter<ChatMessageBean>(
    context,
    data
) {

    private val TAG = "ChatAdapter"

    init {
        addItemType(ChatMessageBean.TYPE_RECEIVED, R.layout.chat_receive)
        addItemType(ChatMessageBean.TYPE_SEND, R.layout.chat_send)
        addItemType(ChatMessageBean.TYPE_SYSTEM, R.layout.chat_system)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun convert(holder: RvViewHolder, bean: ChatMessageBean, position: Int) {
        val itemViewType = getItemViewType(position)
        when (itemViewType) {
            ChatMessageBean.TYPE_RECEIVED -> {
                val tv_receive_content = holder.getView<TextView>(R.id.tv_receive_content)
                val iv_receive_picture = holder.getView<ImageView>(R.id.iv_receive_picture)
                val tv_receive_nick = holder.getView<TextView>(R.id.tv_receive_nick)
                tv_receive_content.text = bean.content
                tv_receive_nick.text = bean.nick
//                Glide.with(context) //使得glide更容易使用，因为能接收context，activity，fragment对象
//                    .load(bean.pic_url)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .fitCenter() //缩放图像，整个显示在控件，尽可能的填满
//                    .into(iv_receive_picture)
            }
            ChatMessageBean.TYPE_SEND -> {
                val tv_send_content = holder.getView<TextView>(R.id.tv_send_content)
                val iv_send_picture = holder.getView<ImageView>(R.id.iv_send_picture)
                tv_send_content.text = bean.content
//                Glide.with(context) //使得glide更容易使用，因为能接收context，activity，fragment对象
//                    .load(bean.pic_url)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .fitCenter() //缩放图像，整个显示在控件，尽可能的填满
//                    .into(iv_send_picture)
            }
            ChatMessageBean.TYPE_SYSTEM -> {
                val tv_system_content = holder.getView<TextView>(R.id.tv_system_content)
                tv_system_content.text = bean.content
            }
        }
    }
}