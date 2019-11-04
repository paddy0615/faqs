package com.example.demo.entity;

/**
 *2019/10/22
 */
public interface EsHighlight {
    /**
     * 高亮显示 - 开始标签
     */
    String HIGH_LIGHT_START_TAG = "<span style=\"color:red\">";

    /**
     * 高亮显示 - 结束标签
     */
    String HIGH_LIGHT_END_TAG = "</span>";

    /**
     * 索引名称
     */
    class INDEX_NAME {
        /**
         * 游记
         */
        public static final String TRAVEL = "travel";
    }
}
