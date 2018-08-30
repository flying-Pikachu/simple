package cn.edu.dlnu.simple.mapper;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.edu.dlnu.simple.model.Country;

/**
 * @ Author     ：xzp.
 * @ Date       ：Created in 9:54 AM 29/08/2018
 * @ Description：CountryMapper 的测试类
 * @ Modified By：
 */
public class CountryMapperTest {

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
    public void testSelectAll(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<Country> countryList = sqlSession.selectList("selectAll");
            printCountryList(countryList);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertCountry() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Country> countryList = new ArrayList<Country>(){
            {
                add(new Country("印度", "IN"));
                add(new Country("日本", "JP"));
            }
        };
        sqlSession.insert("insertCountry", countryList);
        sqlSession.commit();
    }

    private void printCountryList(List<Country> countryList){
        for(Country country : countryList){
            System.out.printf("%-4d%4s%4s\n",country.getId(), country.getCountryName(), country.getCountryCode());
        }
    }
}

