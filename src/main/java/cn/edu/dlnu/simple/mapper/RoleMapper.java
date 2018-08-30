package cn.edu.dlnu.simple.mapper;

import cn.edu.dlnu.simple.model.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author     ：xzp.
 * @Date       ：Created in 1:58 PM 29/08/2018
 * @ Description：
 */
public interface RoleMapper {

    /**
     * create by: xzp
     * description: 通过id查找角色
     * create time: 1:35 PM 30/08/2018
     *
     * @param id 查找的id
     * @return cn.edu.dlnu.simple.model.SysRole
     */;
    @Results(id = "roleResultMap", value = {
            @Result(property = "id", column = "ID", id = true),
            @Result(property = "roleName", column = "ROLE_NAME"),
            @Result(property = "createBy", column = "CREATED_BY"),
            @Result(property = "createTime", column = "CREATED_TIME"),
            @Result(property = "enable", column = "ENABLED")
    })
    @Select({
            "SELECT ID, ROLE_NAME" +
                    ", ENABLED" +
                    ", CREATED_BY, " +
                    "CREATED_TIME " +
                    "FROM SYS_ROLE " +
                    "WHERE ID = #{id}"
    })
    SysRole selectById(Long id);

    /**
     * create by: xzp
     * description: 查找全部的角色
     * create time: 1:35 PM 30/08/2018
     *
     * @return java.util.List<cn.edu.dlnu.simple.model.SysRole>
     */
    @ResultMap("roleResultMap")
    List<SysRole> selectAll();

    /**
     * create by: xzp
     * description: 插入一个角色
     * create time: 1:55 PM 30/08/2018
     *
     * @param sysRole
     * @return void
     */
    @Insert({
            "INSERT INTO SYS_ROLE(ROLE_NAME, ENABLED, CREATED_BY, CREATED_TIME)" +
                    "VALUES(#{roleName}, #{enable}, #{createBy}, #{createTime, jdbcType=DATE})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(SysRole sysRole);

    /**
     * create by: xzp
     * description: 根据id更新数据
     * create time: 2:02 PM 30/08/2018
     *
     * @param sysRole
     * @return int
     * @throws
     */
    @Update({
            "UPDATE SYS_ROLE set " +
                    "role name = #{roleName}, " +
                    "enabled = #{enabled}, " +
                    "create by = #{createBy}, " +
                    "create time = #{createTime, jdbcType=TIMESTAMP} " +
                    "where id = #{id}"
    })
    int updateById(SysRole sysRole);

    /**
     * create by: xzp
     * description: 根据id删除数据
     * create time: 2:03 PM 30/08/2018
     *
     * @param id
     * @return int
     * @throws
     */
    @Delete({
            "delete from sys role where id = #{id }"
    })
    int deleteById(Long id);
}
