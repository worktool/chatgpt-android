package org.yameida.asrassistant.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.CompoundButton
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.android.synthetic.main.activity_config.*
import org.yameida.asrassistant.R
import org.yameida.asrassistant.config.Config
import org.yameida.asrassistant.utils.DonateUtil
import org.yameida.asrassistant.utils.ShareUtil


class ConfigActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        initView()
    }

    private fun initView() {
        rl_proxy_address.setOnClickListener { showProxyAddressSelectionDialog() }
        rl_api_key.setOnClickListener { showAPIKeyDialog() }
        rl_assistant_name.setOnClickListener { showRenameDialog() }
        rl_gpt_model.setOnClickListener { showModelSelectionDialog() }
        rl_donate.setOnClickListener { showDonateDialog() }
        rl_share.setOnClickListener { showShareDialog() }
        sw_use_context.isChecked = Config.useContext
        sw_use_context.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            LogUtils.i("sw_use_context onCheckedChanged: $isChecked")
            Config.useContext = isChecked
        })
        tv_model_proxy.text = "当前代理：${Config.proxyAddress}"
        tv_model_name.text = "模型（当前模型：${Config.gptModel}）"
    }

    private fun showProxyAddressSelectionDialog() {
        val items = arrayOf("https://api.openai.com", "http://proxy.chat.carlife.host", "https://api.gptsapi.net", "手动输入")
        val builder = QMUIDialog.MenuDialogBuilder(this)

        builder.setTitle("选择模型代理地址")
            .addItems(items) { dialog, which ->
                dialog.dismiss()
                if (which == items.size - 1) { // 判断是否选择了“手动输入”选项
                    showProxyAddressDialog(null)
                } else {
                    val selectedProxyAddress = items[which]
                    Config.proxyAddress = selectedProxyAddress
                    tv_model_proxy.text = "当前代理：${Config.proxyAddress}"
                    ToastUtils.showLong("已选择代理地址: $selectedProxyAddress")
                }
            }
            .create(R.style.QMUI_Dialog).show()
    }

    private fun showProxyAddressDialog(selectedProxyAddress: String?) {
        val builder = QMUIDialog.EditTextDialogBuilder(this)
        builder.setTitle("模型代理地址")
            .setDefaultText(selectedProxyAddress ?: "http://proxy.com (可选)")
            .setPlaceholder("请输入代理地址")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction(getString(R.string.cancel)) { dialog, index -> dialog.dismiss() }
            .addAction(getString(R.string.confirm)) { dialog, index ->
                val text = builder.editText.text
                if (text != null) {
                    dialog.dismiss()
                    Config.proxyAddress = text.toString().trim()
                    tv_model_proxy.text = "当前代理：${Config.proxyAddress}"
                    ToastUtils.showLong("已选择代理地址: ${Config.proxyAddress}")
                } else {
                    ToastUtils.showLong("请勿为空！")
                }
            }
            .create(R.style.QMUI_Dialog).show()
    }

    private fun showAPIKeyDialog() {
        val builder = QMUIDialog.EditTextDialogBuilder(this)
        builder.setTitle("API_KEY")
            .setDefaultText(Config.apiKey)
            .setPlaceholder("sk-xxxxxx")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction(getString(R.string.cancel)) { dialog, index -> dialog.dismiss() }
            .addAction(getString(R.string.confirm)) { dialog, index ->
                val text = builder.editText.text
                if (text != null) {
                    dialog.dismiss()
                    Config.apiKey = text.toString().trim()
                } else {
                    ToastUtils.showLong("请勿为空！")
                }
            }
            .create(R.style.QMUI_Dialog).show()
    }

    private fun showRenameDialog() {
        val builder = QMUIDialog.EditTextDialogBuilder(this)
        builder.setTitle("昵称")
            .setDefaultText(Config.assistantName)
            .setPlaceholder("请输入昵称")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction(getString(R.string.cancel)) { dialog, index -> dialog.dismiss() }
            .addAction(getString(R.string.confirm)) { dialog, index ->
                val text = builder.editText.text
                if (text != null) {
                    dialog.dismiss()
                    Config.assistantName = text.toString().trim()
                } else {
                    ToastUtils.showLong("请勿为空！")
                }
            }
            .create(R.style.QMUI_Dialog).show()
    }

    private fun showModelSelectionDialog() {
        val items = arrayOf("gpt-4o", "chatgpt-4o-latest（推荐）", "gpt-4o-mini（推荐）", "gpt-4-turbo", "gpt-3.5-turbo-16k", "claude-3-opus-20240229", "claude-3-5-sonnet-20240620（推荐）", "claude-3-haiku-20240307", "手动输入") // 预设的模型选项和手动输入选项
        val builder = QMUIDialog.MenuDialogBuilder(this)

        builder.setTitle("选择模型")
            .addItems(items) { dialog, which ->
                dialog.dismiss()
                if (which == items.size - 1) { // 判断是否选择了“手动输入”选项
                    showModelDialog(null)
                } else {
                    val selectedModel = items[which]
                    Config.gptModel = selectedModel.replace("（推荐）", "")
                    tv_model_name.text = "模型（当前模型：${Config.gptModel}）"
                    ToastUtils.showLong("已选择模型: ${Config.gptModel}")

                }
            }
            .create(R.style.QMUI_Dialog).show()
    }

    private fun showModelDialog(selectedModel: String?) {
        val builder = QMUIDialog.EditTextDialogBuilder(this)
        builder.setTitle("选择模型")
            .setDefaultText(selectedModel ?: "")
            .setPlaceholder("请输入模型名称")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction(getString(R.string.cancel)) { dialog, index -> dialog.dismiss() }
            .addAction(getString(R.string.confirm)) { dialog, index ->
                val text = builder.editText.text
                if (text != null) {
                    dialog.dismiss()
                    Config.gptModel = text.toString().trim()
                    tv_model_name.text = "模型（当前模型：${Config.gptModel}）"
                    ToastUtils.showLong("已选择模型: ${Config.gptModel}")
                } else {
                    ToastUtils.showLong("请勿为空！")
                }
            }
            .create(R.style.QMUI_Dialog).show()
    }

    private fun showDonateDialog() {
        DonateUtil.zfbDonate(this)
    }

    private fun showShareDialog() {
        startActivity(Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = ShareUtil.TEXT
            putExtra(Intent.EXTRA_TEXT, "我发现一个非常好用的聊天程序，文档地址: https://github.com/worktool/chatgpt-android")
        }, "分享"))
    }

}

