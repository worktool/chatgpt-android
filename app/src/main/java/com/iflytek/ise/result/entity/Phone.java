/**
 *
 */
package com.iflytek.ise.result.entity;

import java.util.HashMap;

/**
 * <p>Title: Phone</p>
 * <p>Description: 音素，对应于xml结果中的Phone标签</p>
 * <p>Company: www.iflytek.com</p>
 * @author iflytek
 * @date 2015年1月12日 下午3:55:56
 */
public class Phone {
    /**
     * 讯飞音标-标准音标映射表（en）
     */
    public static HashMap<String, String> phone_map = new HashMap<String, String>();

    static {
        phone_map.put("aa", "ɑ:");
        phone_map.put("oo", "ɔ");
        phone_map.put("ae", "æ");
        phone_map.put("ah", "ʌ");
        phone_map.put("ao", "ɔ:");
        phone_map.put("aw", "aʊ");
        phone_map.put("ax", "ə");
        phone_map.put("ay", "aɪ");
        phone_map.put("eh", "e");
        phone_map.put("er", "ə:");
        phone_map.put("ey", "eɪ");
        phone_map.put("ih", "ɪ");
        phone_map.put("iy", "i:");
        phone_map.put("ow", "əʊ");
        phone_map.put("oy", "ɔɪ");
        phone_map.put("uh", "ʊ");
        phone_map.put("uw", "ʊ:");
        phone_map.put("ch", "tʃ");
        phone_map.put("dh", "ð");
        phone_map.put("hh", "h");
        phone_map.put("jh", "dʒ");
        phone_map.put("ng", "ŋ");
        phone_map.put("sh", "ʃ");
        phone_map.put("th", "θ");
        phone_map.put("zh", "ʒ");
        phone_map.put("y", "j");
        phone_map.put("d", "d");
        phone_map.put("k", "k");
        phone_map.put("l", "l");
        phone_map.put("m", "m");
        phone_map.put("n", "n");
        phone_map.put("b", "b");
        phone_map.put("f", "f");
        phone_map.put("g", "g");
        phone_map.put("p", "p");
        phone_map.put("r", "r");
        phone_map.put("s", "s");
        phone_map.put("t", "t");
        phone_map.put("v", "v");
        phone_map.put("w", "w");
        phone_map.put("z", "z");
        phone_map.put("ar", "eə");
        phone_map.put("ir", "iə");
        phone_map.put("ur", "ʊə");
        phone_map.put("tr", "tr");
        phone_map.put("dr", "dr");
        phone_map.put("ts", "ts");
        phone_map.put("dz", "dz");
    }

    /**
     * 开始帧位置，每帧相当于10ms
     */
    public int beg_pos;
    /**
     * 结束帧位置
     */
    public int end_pos;
    /**
     * 音素内容
     */
    public String content;
    /**
     * 增漏读信息：0（正确），16（漏读），32（增读），64（回读），128（替换）
     */
    public int dp_message;
    /**
     * 时长（单位：帧，每帧相当于10ms）（cn）
     */
    public int time_len;

    /**
     * 得到content对应的标准音标（en）
     */
    public String getStdSymbol() {
        return getStdSymbol(content);
    }

    public static String getStdSymbol(String content) {
        String std = phone_map.get(content);
        return (null == std) ? content : std;
    }

}
