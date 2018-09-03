package cn.edu.dlnu.simple.mapper;

import cn.edu.dlnu.simple.model.Country;
import cn.edu.dlnu.simple.model.SysRole;
import cn.edu.dlnu.simple.model.SysUserRole;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author     ：xzp.
 * @Date       ：Created in 1:38 PM 30/08/2018
 * @Description：RoleMapper的测试类
 */
public class RoleMapperTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init(){
        try {
            // 读入配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            String environment = "development";
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, environment);
            reader.close();
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }


    @Test
    public void testSelectById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        SysRole sysRole = roleMapper.selectById(1l);
        sysRole.setRoleName("xzp");
        System.out.println(sysRole);
        sqlSession.close();
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        RoleMapper roleMapper1 = sqlSession1.getMapper(RoleMapper.class);
        SysRole sysRole1 = roleMapper1.selectById(1l);
        System.out.println(sysRole1);
        sqlSession1.close();
    }

    @Test
    public void testSelectRoleByUserIdChoose() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        List<SysRole> sysRoles = roleMapper.selectRoleByUserIdChoose(1l);
        for (SysRole sysRole : sysRoles) {
            sysRole.setRoleName("xzp " + sysRole.getRoleName());
            System.out.println(sysRole);
        }
//        roleMapper.insert(new SysRole("测试用户", "1", "1", new Date(2018, 9, 3)));
//        UserRoleMapper sysUserRole = sqlSession.getMapper(UserRoleMapper.class);
//        sysUserRole.insert(new SysUserRole(1l, 3l));
//        for (SysRole sysRole : sysRoles) {
//            System.out.println(sysRole);
//        }
        sqlSession.close();
    }
}
