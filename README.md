# simple

## MyBatis 配置

整体的配置框架

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
</configuration>
```

<setting>标签

```xml
<settings>
    <setting name="logImpl" value="LOG4J"/>
</settings>
```

logImpl属性配置指定LOG4J日志

<typeAliases>标签

```xml
<typeAliases>
    <package name="cn.edu.dlnu.simple.model"></package>
</typeAliases>
```

配置一个包的权限定名，一个类频繁在Mybatis中使用，我们在这个地方进行配置了之后，相当于引入了包名，写类名的时候，直接写类名，他自动在这个包地下查找

<environments>标签

```xml
<environments default="development">
        <environment id="development">
            <transactionManager type="JDBC">
                <property name="" value=""></property>
            </transactionManager>
            <dataSource type="UNPOOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"></property>
                <property name="url" value="jdbc:mysql://localhost:3306/TEST_MYBATIS"></property>
                <property name="username" value="root"></property>
                <property name="password" value="123456"></property>
            </dataSource>
        </environment>
    </environments>
```

配置数据库连接

<mappers>标签

MyBatis的SQL语句和映射配置文件

```xml
<mappers>
    <mapper resource="cn/edu/dlnu/simple/mapper/CountryMapper.xml"></mapper>
</mappers>
```

## Mapper映射配置文件

整体框架

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="cn.edu.dlnu.simple.mapper.CountryMapper">

</mapper>
```

<mapper>标签

namespace 定义了当前的xml的命名空间

\<select>标签

```xml
<select id="selectAll" resultType="Country">
    SELECT ID, COUNTRY_NAME, COUNTRY_CODE FROM COUNTRY;
</select>
```

id 通过id可以找到执行哪一条语句

resultType 当前查询的返回值类型，在之前的配置文件中<typeAliases>配置了我们的包名，所以我们直接在这个位置上进行类的查询

## 小问题总结

1. 数据库中的字段与实体类中的属性不匹配

   - 在mapper的SQL映射文件中的语句查找字段进行名称的重命名

     数据库中的字段名为ID, COUNTRY_NAME, COUNTRY_CODE

     实体类中的字段名为id, countryName, countryCode

     ```xml
     <select id="selectAll" resultType="Country">
         SELECT ID id, COUNTRY_NAME countryName, COUNTRY_CODE countryCode FROM COUNTRY;
     </select>
     ```

   - 创建一个resultMap

     ```xml
     <resultMap id="countryResultMap" type="Country">
         <result property="id" column="ID"/>
         <result property="countryName" column="COUNTRY_NAME"/>
         <result property="countryCode" column="COUNTRY_CODE"/>
     </resultMap>
     
     <select id="selectAll" resultType="Country" resultMap="countryResultMap">
         SELECT ID, COUNTRY_NAME, COUNTRY_CODE FROM COUNTRY;
     </select>
     ```

     resultMap标签中的id为这个resultMap的唯一id，用来确定是哪一个resultMap

     type映射的类名

     之后的就是映射的字段

     ⚠️:当前使用到的智勇result，之后还要进行更新