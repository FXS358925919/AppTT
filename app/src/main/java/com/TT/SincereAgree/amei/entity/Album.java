package com.TT.SincereAgree.amei.entity;

import java.util.List;

public class Album {
    private String albumid;

    private String name;

    private String accountId;

    private String description;

    private Integer level;

    private Double price;

    private String createTime;

    private List<ImgInfo> imgs;
    
    
    public List<ImgInfo> getImgs() {
		return imgs;
	}

	public void setImgs(List<ImgInfo> imgs) {
		this.imgs = imgs;
	}

	public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid == null ? null : albumid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumid='" + albumid + '\'' +
                ", name='" + name + '\'' +
                ", accountId='" + accountId + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                ", price=" + price +
                ", createTime='" + createTime + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}