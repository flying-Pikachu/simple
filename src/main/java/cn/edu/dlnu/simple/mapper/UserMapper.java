package cn.edu.dlnu.simple.mapper;

import cn.edu.dlnu.simple.model.SysUser;

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

}
