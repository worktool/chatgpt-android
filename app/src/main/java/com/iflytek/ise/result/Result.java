/**
 *
 */
package com.iflytek.ise.result;

import com.iflytek.ise.result.entity.Sentence;

import java.util.ArrayList;

/**
 * <p>Title: Result</p>
 * <p>Description: 评测结果</p>
 * <p>Company: www.iflytek.com</p>
 * @author iflytek
 * @date 2015年1月12日 下午4:58:38
 */
public class Result {
    /**
     * 评测语种：en（英文）、cn（中文）
     */
    public String language;
    /**
     * 评测种类：read_syllable（cn单字）、read_word（词语）、read_sentence（句子）
     */
    public String category;
    /**
     * 开始帧位置，每帧相当于10ms
     */
    public int beg_pos;
    /**
     * 结束帧位置
     */
    public int end_pos;
    /**
     * 评测内容
     */
    public String content;
    /**
     * 总得分
     */
    public float total_score;
    /**
     * 时长（cn）
     */
    public int time_len;
    /**
     * 异常信息（en）
     */
    public String except_info;
    /**
     * 是否乱读（cn）
     */
    public boolean is_rejected;
    /**
     * xml结果中的sentence标签
     */
    public ArrayList<Sentence> sentences;
}
