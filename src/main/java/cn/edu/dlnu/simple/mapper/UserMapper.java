package cn.edu.dlnu.simple.mapper;

import cn.edu.dlnu.simple.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * UserMapper.xml 对应的接口
 *
 * @author     ：xzp.
 * @date       ：Created in 1:58 PM 29/08/2018
 */
public interface UserMapper {

    /**
     * create by: xzp
     * description: 通过id查询用户
     * create time: 2:18 PM 29/08/2018
     *
     * @param id 查找的用户id
     * @return 用户的全部信息
     */
    SysUser selectById(Long id);

    /**
     * create by: xzp
     * description: 查询全部用户
     * create time: 4:36 PM 29/08/2018
     *
     * @return List 全部用户的全部信息
     */
    List<SysUser> selectAll();

    /**
     * create by: xzp
     * description: 通过Id或者用户名查找用户
     * create time: 4:46 PM 30/08/2018
     *
     * @param sysUser 用户信息
     * @return SysUser
     */
    SysUser selectByIdOrUserName(SysUser sysUser);

    /**
     * create by: xzp
     * description:根据id集合查询
     * create time: 5:08 PM 30/08/2018
     *
     * @param idList
     * @return java.util.List<cn.edu.dlnu.simple.model.SysUser>
     */
    List<SysUser> selectByidList(@Param("idList") List<Long> idList);

    /**
     * create by: xzp
     * description:通过id查找用户的名称和角色
     * create time: 9:24 AM 31/08/2018
     *
     * @param id
     * @return java.util.List<cn.edu.dlnu.simple.model.SysUser>
     */
    List<SysUser> selectUserAndRoleById(Long id);

    /**
     * create by: xzp
     * description: 得到全部的用户以及角色
     * create time: 1:44 PM 31/08/2018
     *
     * @return java.util.List<cn.edu.dlnu.simple.model.SysUser>
     */
    List<SysUser> selectAllUserAndRoles();
}
