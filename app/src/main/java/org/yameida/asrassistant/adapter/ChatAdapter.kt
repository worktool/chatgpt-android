package org.yameida.asrassistant.adapter

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.yameida.asrassistant.R
import org.yameida.asrassistant.adapter.base.RvMultiAdapter
import org.yameida.asrassistant.adapter.base.RvViewHolder
import org.yameida.asrassistant.model.ChatMessageBean

class ChatAdapter(val context: Context, val data: List<ChatMessageBean>,
                  val avatarClickListener: AvatarClickListener): RvMultiAdapter<ChatMessageBean>(
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

                val robotAvatarUri = SPUtils.getInstance().getString("robot_avatar_uri", "")
                if (robotAvatarUri.isNotEmpty()) {
                    Glide.with(context)
                        .load(Uri.parse(robotAvatarUri))
                        .placeholder(R.drawable.icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .into(iv_receive_picture)
                } else {
                    iv_receive_picture.setImageResource(R.drawable.icon)
                }
                iv_receive_picture.setOnClickListener {
                    avatarClickListener.onAvatarClick(isRobot = true)
                }
            }
            ChatMessageBean.TYPE_SEND -> {
                val tv_send_content = holder.getView<TextView>(R.id.tv_send_content)
                val iv_send_picture = holder.getView<ImageView>(R.id.iv_send_picture)
                tv_send_content.text = bean.content

                val userAvatarUri = SPUtils.getInstance().getString("user_avatar_uri", "")
                if (userAvatarUri.isNotEmpty()) {
                    Glide.with(context)
                        .load(Uri.parse(userAvatarUri))
                        .placeholder(R.drawable.icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .into(iv_send_picture)
                } else {
                    iv_send_picture.setImageResource(R.drawable.icon)
                }
                iv_send_picture.setOnClickListener {
                    avatarClickListener.onAvatarClick(isRobot = false)
                }
            }
            ChatMessageBean.TYPE_SYSTEM -> {
                val tv_system_content = holder.getView<TextView>(R.id.tv_system_content)
                tv_system_content.text = bean.content
            }
        }
    }

    interface AvatarClickListener {
        fun onAvatarClick(isRobot: Boolean)
    }
}