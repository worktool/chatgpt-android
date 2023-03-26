package org.yameida.asrassistant.model;


import org.yameida.asrassistant.adapter.base.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by gallon on 2017/5/19.
 */

public class ChatMessageBean implements Serializable, MultiItemEntity {

    /**
     * 发送的消息 msg
     */

    public static final int TYPE_SEND = 1;
    /**
     * 接收到的消息 只有答案
     */
    public static final int TYPE_RECEIVED = 2;

    /**
     * 系统消息
     */
    public static final int TYPE_SYSTEM = 3;


    /**
     * 是否已经展示了更多
     */
    public boolean isMore = false;

    public boolean isGreyMode = false;// 主要用于在咨询界面下，双工时，产生的救场语以及提问的内容置为灰色

    //昵称
    public String nick = "小甲";
    //头像
    public String pic_url = "";
    //内容
    public String content = "";

    public ChatMessageBean(int type, String nick, String pic_url, String content) {
        this.type = type;
        this.content = content;
        this.nick = nick;
        this.pic_url = pic_url;
    }

    public int type = 0;

    @Override
    public int getType() {
        return type;
    }

    public boolean simpleQa = false;
    public boolean save = false;
    public boolean continuesListen = false;

    public static class ChoiceMsg implements Serializable {

        public String content;//标签或案由的中文名
        public boolean isChecked = false;//标签是否被选中
        public int type;//* 叶子标签为1，* 父标签为0
        public String enName;  //标签的英文名
        public String caseCauseId; //案由的id

        @Override
        public String toString() {
            return "{" +
                    "content='" + content + '\'' +
                    ", isChecked=" + isChecked +
                    ", type=" + type +
                    ", enName='" + enName + '\'' +
                    ", caseCauseId=" + caseCauseId +

                    '}';

        }
    }

}
