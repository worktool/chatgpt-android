/**
 *
 */
package com.iflytek.ise.result.entity;

import java.util.ArrayList;

/**
 * <p>Title: Word</p>
 * <p>Description: 单词，对应于结果xml中的word标签</p>
 * <p>Company: www.iflytek.com</p>
 * @author iflytek
 * @date 2015年1月12日 下午3:29:30
 */
public class Word {
    /**
     * 开始帧位置，每帧相当于10ms
     */
    public int beg_pos;
    /**
     * 结束帧位置
     */
    public int end_pos;
    /**
     * 单词内容
     */
    public String content;
    /**
     * 增漏读信息：0（正确），16（漏读），32（增读），64（回读），128（替换）
     */
    public int dp_message;
    /**
     * 单词在全篇索引（en）
     */
    public int global_index;
    /**
     * 单词在句子中的索引（en）
     */
    public int index;
    /**
     * 拼音（cn），数字代表声调，5表示轻声，如fen1
     */
    public String symbol;
    /**
     * 时长（单位：帧，每帧相当于10ms）（cn）
     */
    public int time_len;
    /**
     * 单词得分（en）
     */
    public float total_score;
    /**
     * Word包含的Syll
     */
    public ArrayList<Syll> sylls;

}
