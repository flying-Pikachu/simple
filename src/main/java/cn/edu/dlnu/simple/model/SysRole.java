package cn.edu.dlnu.simple.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * SYS_ROLE表的实体类
 *
 * @author     ：xzp.
 * @date       ：Created in 1:43 PM 29/08/2018
 */
public class SysRole {

    private Long Id;

    private String roleName;

    private String enable;

    private String createBy;

    private Date createTime;

    public SysRole() {

    }

    public SysRole(Long id, String roleName, String enable, String createBy, Date createTime) {
        Id = id;
        this.roleName = roleName;
        this.enable = enable;
        this.createBy = createBy;
        this.createTime = createTime;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRole sysRole = (SysRole) o;
        return Objects.equals(Id, sysRole.Id) &&
                Objects.equals(roleName, sysRole.roleName) &&
                Objects.equals(enable, sysRole.enable) &&
                Objects.equals(createBy, sysRole.createBy) &&
                Objects.equals(createTime, sysRole.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(Id, roleName, enable, createBy, createTime);
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "Id=" + Id +
                ", roleName='" + roleName + '\'' +
                ", enable='" + enable + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
