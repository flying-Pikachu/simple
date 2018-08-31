package cn.edu.dlnu.simple.mapper;

import cn.edu.dlnu.simple.model.SysRole;
import cn.edu.dlnu.simple.model.SysUser;
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
 * @Date       ：Created in 5:36 PM 30/08/2018
 * @Description：UserMapper的测试类
 */
public class UserMapperTest {

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
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<SysUser> sysUser = userMapper.selectByidList(new ArrayList<Long>(){
            {
                add(1l);
                add(2l);
            }
        });
        for (SysUser sysUser1 : sysUser) {
            System.out.println(sysUser1);
        }
        sqlSession.close();
    }

    @Test
    public void testSelectUserAndRoleById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<SysUser> sysUser = userMapper.selectUserAndRoleById(1l);
        for (SysUser sysUser1 : sysUser) {
            System.out.println(sysUser1);
        }
        sqlSession.close();
    }

    @Test
    public void testSelectAllUserAndRoles() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<SysUser> sysUsers = userMapper.selectAllUserAndRoles();
        for (SysUser sysUser : sysUsers) {
            System.out.println(sysUser);
        }
        sqlSession.close();
    }
}
