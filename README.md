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

<properties>标签

```xml
<properties resource="org/mybatis/example/config.properties">
  <property name="username" value="dev_user"/>
  <property name="password" value="F2Fa3!33TYyg"/>
</properties>
```

从resources中引入的配置文件，之后的所有值都可以使用${中间的内容是配置文件中的key}对应到相应的值

在标签中还可以自定义一些属性值，这些中的name就相当于配置文件中的key，之后的value就相当于对应的值

```xml
<property name="driver" value="${jdbc.driver}"></property>
<property name="url" value="${jdbc.url}"></property>
<property name="username" value="${username}"></property>
<property name="password" value="${password}"></property>
```

我在配置文件中配置了前两个值，key的值为jdbc.，之后的实在property中定义的，都可以找到

配置读取的过程

首先是读取标签内定义的属性，之后读取resource中定义的路径中的值，最后读取作为方法参数传递的属性(⚠️当前还没有遇到，之后补充)，每一次读取都会覆盖前一次同key的值

<setting>标签

```xml
<settings>
    <setting name="logImpl" value="LOG4J"/>
</settings>
```

logImpl属性配置指定LOG4J日志，这个属性可有可无，当没有指定的时候自动查找

一个配置完整的 settings 元素的示例如下：

具体的配置含义查看文档http://www.mybatis.org/mybatis-3/zh/configuration.html#settings

```xml
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="defaultFetchSize" value="100"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```

<typeAliases>标签

```xml
<typeAliases>
    <package name="cn.edu.dlnu.simple.model"></package>
</typeAliases>
```

配置一个包的权限定名，一个类频繁在Mybatis中使用，我们在这个地方进行配置了之后，相当于引入了包名，写类名的时候，直接写类名，他自动在这个包地下查找

存在的意义仅在于用来减少类完全限定名的冗余

在上面的这个实例中，Mybatis直接在这个包下面进行Java Bean的搜索

```xml
<typeAliases>
  <typeAlias alias="Author" type="domain.blog.Author"/>
  <typeAlias alias="Blog" type="domain.blog.Blog"/>
  <typeAlias alias="Comment" type="domain.blog.Comment"/>
  <typeAlias alias="Post" type="domain.blog.Post"/>
  <typeAlias alias="Section" type="domain.blog.Section"/>
  <typeAlias alias="Tag" type="domain.blog.Tag"/>
</typeAliases>
```

通过这样定义来对每一个类进行类型别名的定义，当这样配置时，`Blog`可以用在任何使用`domain.blog.Blog`的地方。如果我们把Blog定义为Bl，同理，我们只要在使用Blog的地方使用Bl也同样可以

如果我们直接引入了包名，同时想要给包中的类起别名的时候，我们就可以在实体类中定义

```java
@Alias("cou")
public class Country
```

的形式进行别名的使用

⚠️:文档只验证到http://www.mybatis.org/mybatis-3/zh/configuration.html#typeHandlers,明天继续进行配置文件的学习

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