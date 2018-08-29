package cn.edu.dlnu.simple.model;

/**
 *
 * SYS_ROLE_PRIVILEGE表的实体类
 *
 * @author     ：xzp.
 * @date       ：Created in 1:48 PM 29/08/2018
 */
public class SysRolePrivilege {

    private Long roleId;

    private Long privilegeId;

    public SysRolePrivilege() {

    }

    public SysRolePrivilege(Long roleId, Long privilegeId) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public String toString() {
        return "SysRolePrivilege{" +
                "roleId=" + roleId +
                ", privilegeId=" + privilegeId +
                '}';
    }
}
