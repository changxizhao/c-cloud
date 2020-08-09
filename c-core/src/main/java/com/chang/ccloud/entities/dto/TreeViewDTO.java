package com.chang.ccloud.entities.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/11 21:08
 * @Description
 */
public class TreeViewDTO {

    private String text;

    private String href;

    private List<String> tags = Lists.newArrayList();

    private List<TreeViewDTO> nodes = Lists.newArrayList();

    private boolean checked;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<TreeViewDTO> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeViewDTO> nodes) {
        this.nodes = nodes;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
