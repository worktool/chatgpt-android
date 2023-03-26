/**
 *
 */
package com.iflytek.ise.result.entity;

import java.util.ArrayList;

/**
 * <p>Title: Syll</p>
 * <p>Description: 音节，对应于结果xml中的Syll标签</p>
 * <p>Company: www.iflytek.com</p>
 * @author iflytek
 * @date 2015年1月12日 下午3:49:51
 */
public class Syll {
    /**
     * 开始帧位置，每帧相当于10ms
     */
    public int beg_pos;
    /**
     * 结束帧位置
     */
    public int end_pos;
    /**
     * 音节内容
     */
    public String content;
    /**
     * 拼音（cn），数字代表声调，5表示轻声，如fen1
     */
    public String symbol;
    /**
     * 增漏读信息：0（正确），16（漏读），32（增读），64（回读），128（替换）
     */
    public int dp_message;
    /**
     * 时长（单位：帧，每帧相当于10ms）（cn）
     */
    public int time_len;
    /**
     * Syll包含的音节
     */
    public ArrayList<Phone> phones;

    /**
     * 获取音节的标准音标（en）
     *
     * @return 标准音标
     */
    public String getStdSymbol() {
        String stdSymbol = "";
        String[] symbols = content.split(" ");

        for (int i = 0; i < symbols.length; i++) {
            stdSymbol += Phone.getStdSymbol(symbols[i]);
        }

        return stdSymbol;
    }
}
