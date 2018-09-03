package cn.edu.dlnu.simple.mapper;

import cn.edu.dlnu.simple.model.SysUserRole;
import org.apache.ibatis.annotations.Insert;

/**
 * @Author     ：xzp.
 * @Date       ：Created in 1:58 PM 29/08/2018
 * @Description：用户角色操作类
 */
public interface UserRoleMapper {

    /**
     * create by: xzp
     * description:插入一个用户角色关系
     * create time: 2:00 PM 03/09/2018
     *
     * @param sysUserRole
     * @return void
     */
    @Insert("INSERT INTO SYS_USER_ROLE(USER_ID, ROLE_ID)" +
            "VALUES(#{userId}, #{roleId})")
    void insert(SysUserRole sysUserRole);
}
