package com.TT.SincereAgree.amei.entity;

public class ImgInfo {
    private String imgid;

    private String albumid;

    private String filename;

    private String imgurl;

    private String uploadTime;

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid == null ? null : imgid.trim();
    }

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid == null ? null : albumid.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime == null ? null : uploadTime.trim();
    }

    @Override
    public String toString() {
        return "ImgInfo{" +
                "imgid='" + imgid + '\'' +
                ", albumid='" + albumid + '\'' +
                ", filename='" + filename + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                '}';
    }
}