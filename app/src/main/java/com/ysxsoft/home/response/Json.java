package com.ysxsoft.home.response;

import java.io.Serializable;

/**
 * create by Sincerly on 2019/6/19 0019
 **/
public class Json implements Serializable {
    private String company;
    private String position;
    private String start_time;
    private String end_time;
    private String content;
    private String industry;//行业id
    private String industryName;//行业名称
    public String getIndustry() {
        return industry == null ? "" : industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryName() {
        return industryName == null ? "" : industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getCompany() {
        return company == null ? "" : company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position == null ? "" : position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStart_time() {
        return start_time == null ? "" : start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time == null ? "" : end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
