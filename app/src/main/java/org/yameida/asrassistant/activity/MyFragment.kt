package org.yameida.asrassistant.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_layout.*
import org.yameida.asrassistant.R
import org.yameida.asrassistant.adapter.ChatAdapter
import org.yameida.asrassistant.model.ChatMessageBean
import java.util.ArrayList

class MyFragment : Fragment(), ChatAdapter.AvatarClickListener {

    lateinit var mChatAdapter: ChatAdapter
    var messageBeen: ArrayList<ChatMessageBean> = ArrayList()
    private var isRobotAvatar = false

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val selectedImageUri = result.data?.data
            if (selectedImageUri != null) {
                saveAvatar(selectedImageUri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity ?: return
        mChatAdapter = ChatAdapter(activity, messageBeen, this)
        mChatAdapter.setHasStableIds(true)
        rv_chat.itemAnimator?.changeDuration = 0
        rv_chat.layoutManager = LinearLayoutManager(context)
        rv_chat.adapter = mChatAdapter
    }

    override fun onAvatarClick(isRobot: Boolean) {
        isRobotAvatar = isRobot
        openGallery()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun saveAvatar(imageUri: Uri) {
        val avatarType = if (isRobotAvatar) "机器人头像" else "用户头像"
        SPUtils.getInstance().put(if (isRobotAvatar) "robot_avatar_uri" else "user_avatar_uri", imageUri.toString())
        ToastUtils.showLong("$avatarType 已设置成功")
        mChatAdapter.notifyDataSetChanged()
    }
}