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

<typeHandlers>标签

这个标签用来做jdbc类型和java类型的转换

默认情况下，mybatis已经做好了转换的定义，如果我们想要自定义一个，我们需要继承一个BaseTypeHandler类

```java
@MappedJdbcTypes(JdbcType.VARCHAR)  
//此处如果不用注解指定jdbcType, 那么，就可以在配置文件中通过"jdbcType"属性指定， 同理， javaType 也可通过 @MappedTypes指定
public class ExampleTypeHandler extends BaseTypeHandler<String> {
 
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, parameter);
  }
 
  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return rs.getString(columnName);
  }
 
  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return rs.getString(columnIndex);
  }
 
  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return cs.getString(columnIndex);
  }
}
```

```xml
<!-- mybatis-config.xml -->
<typeHandlers>
  <typeHandler handler="org.mybatis.example.ExampleTypeHandler"/>
</typeHandlers>
```

使用了这个以后覆盖已经存在的处理 Java 的 String 类型属性和 VARCHAR 参数及结果的类型处理器。

如果在java文件中我们没有定义映射关系，我们需要在配置文件中进行配置

```xml

<typeHandlers>
      <!-- 
          当配置package的时候，mybatis会去配置的package扫描TypeHandler
          <package name="com.dy.demo"/>
		  当我们使用这个的时候，需要在java文件中使用注解进行配置
       -->
      
      <!-- handler属性直接配置我们要指定的TypeHandler -->
      <typeHandler handler=""/>
      
      <!-- javaType 配置java类型，例如String, 如果配上javaType, 那么指定的typeHandler就只作用于指定的类型 -->
      <typeHandler javaType="" handler=""/>
      
      <!-- jdbcType 配置数据库基本数据类型，例如varchar, 如果配上jdbcType, 那么指定的typeHandler就只作用于指定的类型  -->
      <typeHandler jdbcType="" handler=""/>
      
      <!-- 也可两者都配置 -->
      <typeHandler javaType="" jdbcType="" handler=""/>
      
  </typeHandlers>
```

这个用到的不多，因为对应的关系在MyBatis中实现的很多了

<objectFactory>标签

在我们得到我们的查询结果的时候，我们需要把我们的数据封装到一个对象中，在生成实例的时候，我们可以自定义这个过程

```java
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
 
import com.majing.learning.mybatis.entity.User;
 
public class ExampleObjectFactory extends DefaultObjectFactory{
 
	private static final long serialVersionUID = 3608715667301891724L;
 
	@Override
	public <T> T create(Class<T> type) {
		T result = super.create(type);
		if(type.equals(User.class)){
			((User)result).setAuthor("马靖");
		}
		return result;
	}

}
```

```xml
<objectFactory type="com.majing.learning.mybatis.reflect.objectfactory.ExampleObjectFactory"></objectFactory>
```

默认情况下，使用的是默认的对象工厂方法 ‘’DefaultObjectFactory‘’ ，在这个方法中使用instantiateClass方法获取实体类的构造函数

<environments>标签

使用这个标签配置数据库的环境，我们在项目中可以映射到多种数据库中，在使用的时候，我们使用**SqlSessionFactory**来对数据库进行操作

```java
public class CountryMapperTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init(){
        try {
            // 读入配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
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

    private void printCountryList(List<Country> countryList){
        for(Country country : countryList){
            System.out.printf("%-4d%4s%4s\n",country.getId(), country.getCountryName(), country.getCountryCode());
        }
    }
}


```

当我们配置这个的时候，我们需要了解到，每一个SqlSessionFactory只能对应一个环境

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

当我们配置多个环境的时候，我们需要定义多个环境

```xml
<environments default="HO">  
        <environment id="HD">  
            <transactionManager type="JDBC" />  
            <dataSource type="POOLED">  
                <property name="driver" value="${hd.jdbc.driverClassName}" />  
                <property name="url" value="${hd.jdbc.url}" />  
                <property name="username" value="${hd.jdbc.username}" />  
                <property name="password" value="${hd.jdbc.password}" />  
            </dataSource>  
        </environment>  
        <environment id="HO">  
            <transactionManager type="JDBC" />  
            <dataSource type="POOLED">  
                <property name="driver" value="${ho.jdbc.driverClassName}" />  
                <property name="url" value="${ho.jdbc.url}" />  
                <property name="username" value="${ho.jdbc.username}" />  
                <property name="password" value="${ho.jdbc.password}" />  
            </dataSource>  
        </environment>  
    </environments>  
```

default 表示的意思是默认的工作环境，下面的环境id对应的就是默认的

id 每一个环境定义一个唯一的id

<transactionManager> 事务处理器 type=”[JDBC|MANAGED]” 

一般使用JDBC直接使用了 JDBC 的提交和回滚设置，它依赖于从数据源得到的连接来管理事务作用域。后面的那个一般不用

<dataSource> 配置连接对象的资源，就是进行连接配置  type=”[UNPOOLED|POOLED|JNDI]”

**UNPOOLED**– 这个数据源的实现只是每次被请求时打开和关闭连接。虽然有点慢，但对于在数据库连接可用性方面没有太高要求的简单应用程序来说，是一个很好的选择。 不同的数据库在性能方面的表现也是不一样的，对于某些数据库来说，使用连接池并不重要，这个配置就很适合这种情形。UNPOOLED 类型的数据源仅仅需要配置以下 5 种属性：

- `driver` – 这是 JDBC 驱动的 Java 类的完全限定名（并不是 JDBC 驱动中可能包含的数据源类）。
- `url` – 这是数据库的 JDBC URL 地址。
- `username` – 登录数据库的用户名。
- `password` – 登录数据库的密码。
- `defaultTransactionIsolationLevel` – 默认的连接事务隔离级别。

作为可选项，你也可以传递属性给数据库驱动。要这样做，属性的前缀为“driver.”，例如：

- `driver.encoding=UTF8`

这将通过 DriverManager.getConnection(url,driverProperties) 方法传递值为 `UTF8` 的 `encoding` 属性给数据库驱动。

**POOLED**– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。 这是一种使得并发 Web 应用快速响应请求的流行处理方式。

除了上述提到 UNPOOLED 下的属性外，还有更多属性用来配置 POOLED 的数据源：

- `poolMaximumActiveConnections` – 在任意时间可以存在的活动（也就是正在使用）连接数量，默认值：10
- `poolMaximumIdleConnections` – 任意时间可能存在的空闲连接数。
- `poolMaximumCheckoutTime` – 在被强制返回之前，池中连接被检出（checked out）时间，默认值：20000 毫秒（即 20 秒）
- `poolTimeToWait` – 这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直安静的失败），默认值：20000 毫秒（即 20 秒）。
- `poolMaximumLocalBadConnectionTolerance` – 这是一个关于坏连接容忍度的底层设置， 作用于每一个尝试从缓存池获取连接的线程. 如果这个线程获取到的是一个坏的连接，那么这个数据源允许这个线程尝试重新获取一个新的连接，但是这个重新尝试的次数不应该超过 `poolMaximumIdleConnections` 与 `poolMaximumLocalBadConnectionTolerance` 之和。 默认值：3 (新增于 3.4.5)
- `poolPingQuery` – 发送到数据库的侦测查询，用来检验连接是否正常工作并准备接受请求。默认是“NO PING QUERY SET”，这会导致多数数据库驱动失败时带有一个恰当的错误消息。
- `poolPingEnabled` – 是否启用侦测查询。若开启，需要设置 `poolPingQuery` 属性为一个可执行的 SQL 语句（最好是一个速度非常快的 SQL 语句），默认值：false。
- `poolPingConnectionsNotUsedFor` – 配置 poolPingQuery 的频率。可以被设置为和数据库连接超时时间一样，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 poolPingEnabled 为 true 时适用）。

**JNDI** – 这个数据源的实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的引用。这种数据源配置只需要两个属性：

- `initial_context` – 这个属性用来在 InitialContext 中寻找上下文（即，initialContext.lookup(initial_context)）。这是个可选属性，如果忽略，那么 data_source 属性将会直接从 InitialContext 中寻找。
- `data_source` – 这是引用数据源实例位置的上下文的路径。提供了 initial_context 配置时会在其返回的上下文中进行查找，没有提供时则直接在 InitialContext 中查找。

和其他数据源配置类似，可以通过添加前缀“env.”直接把属性传递给初始上下文。比如：

- `env.encoding=UTF8`

这就会在初始上下文（InitialContext）实例化时往它的构造方法传递值为 `UTF8` 的 `encoding` 属性。



创建SqlSession的方法有很多，我们一般使用四种方法

```java
public SqlSessionFactory build(Reader reader) {
        return this.build((Reader)reader, (String)null, (Properties)null);
    }

    public SqlSessionFactory build(Reader reader, String environment) {
        return this.build((Reader)reader, environment, (Properties)null);
    }

    public SqlSessionFactory build(Reader reader, Properties properties) {
        return this.build((Reader)reader, (String)null, properties);
    }

    public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
        SqlSessionFactory var5;
        try {
            XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);
            var5 = this.build(parser.parse());
        } catch (Exception var14) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", var14);
        } finally {
            ErrorContext.instance().reset();

            try {
                reader.close();
            } catch (IOException var13) {
                ;
            }

        }

        return var5;
    }
```



<mappers>标签

MyBatis的SQL语句和映射配置文件

```xml
<mappers>
    <mapper resource="cn/edu/dlnu/simple/mapper/CountryMapper.xml"></mapper>
</mappers>
```

告诉Mybatis去哪里找这个映射文件

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
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

- id 通过id可以找到执行哪一条语句

- select中返回值有两种，一种是使用resultType，另一种是使用resultMap

  resultType 需要的是类的**权限定名或者是类的别名**，如果返回值是一个List，那么我们的返回值就需要是集合包含的值的类型，而不是集合类型，**如果返回值是集合类型，但表与实体类不匹配，那么就需要我们使用SQL语句中给字段起别名的方法进行匹配**

  resultMap 能够解决许多困难的映射案例，对于表中字段与实体类不匹配问题，可以使用这个来设计一个映射关系进行映射

  **上述的两个不能同时出现**

  resultType 当前查询的返回值类型，在之前的配置文件中<typeAliases>配置了我们的包名，所以我们直接在这个位置上进行类的查询

  在我们查询全部信息的时候，不要使用*，会影响性能，要把全部需要查询的列名写出来

- flushCache 将其设置为 true，任何时候只要语句被调用，都会导致本地缓存和二级缓存都会被清空，默认值：false。在每一次查询的时候，都会刷新缓存:yellow_heart:对于一级缓存和二级缓存的叙述，参考下方的下问题总结2

- useCache 将其设置为 true，将会导致本条语句的结果被二级缓存，默认值：对 select 元素为 true。禁用这个select的二级缓存

- parameterType 这个值不用设置，MyBatis可以自己识别

- statementType STATEMENT，PREPARED 或 CALLABLE 的一个。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。


<insert><update><delete>标签

| `id`            | 命名空间中的唯一标识符，可被用来代表这条语句。               |
| --------------- | ------------------------------------------------------------ |
| `parameterType` | 将要传入语句的参数的完全限定类名或别名。这个属性是可选的，因为 MyBatis 可以通过 TypeHandler 推断出具体传入语句的参数，默认值为 unset。 |
| `parameterMap`  | 这是引用外部 parameterMap 的已经被废弃的方法。使用内联参数映射和 parameterType 属性。 |
| `flushCache`    | 将其设置为 true，任何时候只要语句被调用，都会导致本地缓存和二级缓存都会被清空，默认值：true（对应插入、更新和删除语句）。 |
| `timeout`       | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为 unset（依赖驱动）。 |
| `statementType` | STATEMENT，PREPARED 或 CALLABLE 的一个。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |

- useGeneratedKeys **(仅对 insert 和 update 有用)**这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系数据库管理系统的自动递增字段），默认值：false。所以，我们在设置为true的时候，不需要设置id就可以了

  ```xml
  <insert id="insertAuthor" useGeneratedKeys="true"
      keyProperty="id">
    insert into Author (username,password,email,bio)
    values (#{username},#{password},#{email},#{bio})
  </insert>
  ```

- keyProperty **（仅对 insert 和 update 有用）**唯一标记一个属性，MyBatis 会通过 getGeneratedKeys 的返回值或者通过 insert 语句的 selectKey 子元素设置它的键值，默认：`unset`。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。一般就是设置自加的属性（⚠️理解的不对，之后改正）

insert的多行插入

```xml
<insert id="insertAuthor" useGeneratedKeys="true"
    keyProperty="id">
  insert into Author (username, password, email, bio) values
  <foreach item="item" collection="list" separator=",">
    (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
  </foreach>
</insert>
```

⚠️：更多的之后慢啊慢看



<sql> 标签

定义可重用的SQL代码段

⚠️：这块挺灵活的，下午看

## XML方式的基本用法

我们首先创建. Mapper.xml. 文件，这个文件中我们进行SQL语句的编写(⚠️这块总结的不好，之后再进行总结)

创建xml文件之后，我们需要对文件进行引用，引用的时候，我们要在mybatis的配置文件<mappers>中进行配置，

```xml
<mappers>

        <mapper resource="cn/edu/dlnu/simple/mapper/CountryMapper.xml"/>
        <mapper resource="cn/edu/dlnu/simple/mapper/UserMapper.xml"/>
        <mapper resource="cn/edu/dlnu/simple/mapper/RoleMapper.xml"/>
        <mapper resource="cn/edu/dlnu/simple/mapper/PrivilegeMapper.xml"/>
        <mapper resource="cn/edu/dlnu/simple/mapper/UserRoleMapper.xml"/>
        <mapper resource="cn/edu/dlnu/simple/mapper/RolePrivilegeMapper.xml"/>

    </mappers>
```

这种方法太麻烦

我们还可以使用

```xml
<mappers>
    <package name="cn.edu.dlnu.simple.mapper"/>
</mappers>
```

因为每一个xml文件都有接口对应，所以可以采用这种方法

Mybatis会首先检查这个包地下的全部的接口，循环进行一下的操作

将接口的权限定名以.xml为后缀搜索资源



之后我们要编写Mapper接口



当我们只使用XML不使用接口的时候，namespace的value随意，标签中id不能用**“.”**，同一个命名空间下面不能有同样的id，接口中的方法可以重载，多个重载方法对应着同一个id的方法

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
         <id property="id" column="ID"/>
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

     <resultMap>标签包含的属性

     id 唯一确认

     type 配置查询类所映射到的Java对象，就是表对应的实体类

     extends 当前的resultMap继承其他的resultMap，通过id

     autoMapping 启动非映射字段的自动映射功能

     ```xml
     <resultMap id="countryResultMap" type="Country">
         <result property="id" column="ID"/>
         <result property="countryName" column="COUNTRY_NAME"/>
     </resultMap>
     
     <resultMap id="countryResultMap1" type="Country" extends="countryResultMap">
         <result property="countryCode" column="COUNTRY_CODE"/>
     </resultMap>
     
     <select id="selectAll" resultType="Country" resultMap="countryResultMap1">
         SELECT ID, COUNTRY_NAME, COUNTRY_CODE FROM COUNTRY;
     </select>
     ```

     内部标签包括

     constructor 

     id,result 这两个标签包含的属性相同 **id代表的是主键字段(通过setter方式注入)**，result代表的是一般字段。

     - javaType   一个java类的完全限定名
     - jdbcType
     - typeHandler

     association

     collection

     case

     ⚠️:当前使用到的智勇result，之后还要进行更新

2. MyBatis的缓存机制

   - 一级缓存

     - 一级缓存的作用域是同一个SqlSession，在同一个sqlSession中两次执行相同的sql语句，**第一次执行完毕会将数据库中查询的数据写到缓存（内存），第二次会从缓存中获取数据将不再从数据库查询，从而提高查询效率。**当一个sqlSession结束后该sqlSession中的一级缓存也就不存在了。Mybatis默认开启一级缓存。
     - 如果**sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓**存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

   - 二级缓存

     - 首先开启二级缓存

       ```xml
       <setting name="cacheEnabled"value="true"/>
       ```

     - 跟一级缓存类似，这个时候，我们的缓存数据是SqlSession共享的，所有的SqlSession都可以访问到