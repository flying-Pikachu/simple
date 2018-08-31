package cn.edu.dlnu.simple.mapper;

import cn.edu.dlnu.simple.model.Country;
import cn.edu.dlnu.simple.model.SysRole;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
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
        System.out.println(sysRole.toString());
        sqlSession.close();
    }

    @Test
    public void testSelectRoleByUserIdChoose() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        List<SysRole> sysRoles = roleMapper.selectRoleByUserIdChoose(1l);
        for (SysRole sysRole : sysRoles) {
            System.out.println(sysRole);
        }
        sqlSession.close();
    }
}
