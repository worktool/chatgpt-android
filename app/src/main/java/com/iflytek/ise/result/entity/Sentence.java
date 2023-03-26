/**
 *
 */
package com.iflytek.ise.result.entity;

import java.util.ArrayList;

/**
 * <p>Title: Sentence</p>
 * <p>Description: 句子，对应于xml结果中的sentence标签</p>
 * <p>Company: www.iflytek.com</p>
 * @author iflytek
 * @date 2015年1月12日 下午4:10:09
 */
public class Sentence {
    /**
     * 开始帧位置，每帧相当于10ms
     */
    public int beg_pos;
    /**
     * 结束帧位置
     */
    public int end_pos;
    /**
     * 句子内容
     */
    public String content;
    /**
     * 总得分
     */
    public float total_score;
    /**
     * 时长（单位：帧，每帧相当于10ms）（cn）
     */
    public int time_len;
    /**
     * 句子的索引（en）
     */
    public int index;
    /**
     * 单词数（en）
     */
    public int word_count;
    /**
     * sentence包括的word
     */
    public ArrayList<Word> words;
}
