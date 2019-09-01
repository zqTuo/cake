package io.renren.common.utils.poi.model;

import java.io.Serializable;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2018/3/16 16:56.
 */
public class PropertyModel implements Serializable {
    private String name;//列属性名
    private int position; //列开始位置
    private String title; //列标题
    private int numberFormat; //数值格式化  倍数  如：100  表示 字段值/100
    private String dateFormat; //日期格式化
    private boolean uniqueness;//数据是否唯一（在数据库中是否存在，导入时用）
    private boolean required; //数据是否可空
    private String type; //字段类型
    private int limitLenth;//限制长度
    private String defaultValue; //默认值

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean isUniqueness() {
        return uniqueness;
    }

    public void setUniqueness(boolean uniqueness) {
        this.uniqueness = uniqueness;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public int getLimitLenth() {
        return limitLenth;
    }

    public void setLimitLenth(int limitLenth) {
        this.limitLenth = limitLenth;
    }
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(int numberFormat) {
        this.numberFormat = numberFormat;
    }

    @Override
    public String toString() {
        return "PropertyModel{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", title='" + title + '\'' +
                ", numberFormat=" + numberFormat +
                ", dateFormat='" + dateFormat + '\'' +
                ", uniqueness=" + uniqueness +
                ", required=" + required +
                ", type='" + type + '\'' +
                ", limitLenth=" + limitLenth +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}
