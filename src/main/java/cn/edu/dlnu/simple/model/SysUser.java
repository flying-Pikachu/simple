package cn.edu.dlnu.simple.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * SYS_USER表的实体类
 *
 * @author     ：xzp.
 * @date       ：Created in 1:50 PM 29/08/2018
 */
public class SysUser {

    private Long id;

    private String userName;

    private String userPassword;

    private String userEmail;

    private String userInfo;

    private byte[] headImg;

    private Date createTime;

    private List<SysRole> sysRoleList;

    public SysUser() {

    }

    public SysUser(Long id, String userName, String userPassword, String userEmail, String userInfo, byte[] headImg, Date createTime, List<SysRole> sysRoleList) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userInfo = userInfo;
        this.headImg = headImg;
        this.createTime = createTime;
        this.sysRoleList = sysRoleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public byte[] getHeadImg() {
        return headImg;
    }

    public void setHeadImg(byte[] headImg) {
        this.headImg = headImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<SysRole> getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List<SysRole> sysRoleList) {
        this.sysRoleList = sysRoleList;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", headImg=" + Arrays.toString(headImg) +
                ", createTime=" + createTime +
                ", sysRoleList=" + sysRoleList +
                '}';
    }
}
