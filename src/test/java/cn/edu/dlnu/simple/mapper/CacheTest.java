package cn.edu.dlnu.simple.mapper;

import cn.edu.dlnu.simple.model.SysUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

/**
 * @Author     ：xzp.
 * @Date       ：Created in 10:02 AM 03/09/2018
 * @Description：测试缓存机制
 */
public class CacheTest {

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
    public void testL1Cache() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        SysUser sysUser = userMapper.selectById(1l);
        System.out.println(sysUser);
        sysUser.setUserName("New Name");
        System.out.println(sysUser);
        SysUser sysUser1 = userMapper.selectById(1l);
        System.out.println(sysUser1);
        sysUser.setUserName("New Name1");
        System.out.println(sysUser);
        System.out.println(sysUser1);
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        SysUser sysUser2 = userMapper1.selectById(1l);
        System.out.println(sysUser2);
    }
}
