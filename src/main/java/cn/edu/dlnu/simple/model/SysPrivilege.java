package cn.edu.dlnu.simple.model;

/**
 *
 * SYS_PEIVILEGE表的实体类
 *
 * @author     ：xzp.
 * @date       ：Created in 1:46 PM 29/08/2018
 */
public class SysPrivilege {

    private Long Id;

    private String privilegeName;

    private String privilegeURL;

    public SysPrivilege() {

    }

    public SysPrivilege(Long id, String privilegeName, String privilegeURL) {
        Id = id;
        this.privilegeName = privilegeName;
        this.privilegeURL = privilegeURL;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeURL() {
        return privilegeURL;
    }

    public void setPrivilegeURL(String privilegeURL) {
        this.privilegeURL = privilegeURL;
    }

    @Override
    public String toString() {
        return "SysPrivilege{" +
                "Id=" + Id +
                ", privilegeName='" + privilegeName + '\'' +
                ", privilegeURL='" + privilegeURL + '\'' +
                '}';
    }
}
