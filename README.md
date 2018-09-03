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

## 注解方式

把SQL语句直接写在接口上面，对于需求简单的系统效率高，**但当SQL有变化的时候需要重新编译代码**

- @Select

  ```xml
  <settings>
  	<setting name=”mapUnderscoreToCamelCase” value=”true”/>  
  <settings>
  ```

  这样配置了之后，他就自动下划线转驼峰操作了

  ```java
  @Select({
      "SELECT ID, role_name" +
          ", enable" +
          ", created_by, " +
          "created_time" +
          "FROM SYS_ROLE" +
          "WHERE ID = #{id}"
  })
  SysRole selectById(Long id);
  ```

- @Results 使用这个来做属性的映射（XML中的resultMap）

  ```java
  @Results({
      @Result(property = "id", column = "ID", id = true),
      @Result(property = "roleName", column = "ROLE_NAME"),
      @Result(property = "createBy", column = "CREATED_BY"),
      @Result(property = "createTime", column = "CREATED_TIME")
  })
  @Select({
      "SELECT ID, role_name" +
          ", enable" +
          ", created_by, " +
          "created_time" +
          "FROM SYS_ROLE" +
          "WHERE ID = #{id}"
  })
  SysRole selectById(Long id);
  ```

  3.3.0之前的版本，Results不能共用，现在在Results上设置一个id就可以共用了

  ```java
  @Results(id = "roleResultMap", value = {
      @Result(property = "id", column = "ID", id = true),
      @Result(property = "roleName", column = "ROLE_NAME"),
      @Result(property = "createBy", column = "CREATED_BY"),
      @Result(property = "createTime", column = "CREATED_TIME")
  })
  
  @ResultMap("roleResultMap")
  @Select({
      "SELECT * FROM SYS_ROLE"
  })
  List<SysRole> selectAll();
  ```

- @Insert

  ```java
  @Insert({
      "INSERT INTO SYS_ROLE(ROLE_NAME, ENABLED, CREATED_BY, CREATED_TIME)" +
          "VALUES(#{roleName}, #{enable}, #{createBy}, #{createTime, jdbcType=DATE})"
  })
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(SysRole sysRole);
  ```

- @Delete

  ```java
  @Delete({
      "delete from sys role where id = #{id }"
  })
  int deleteById(Long id);
  ```

- @Update

  ```java
  @Update({
  "UPDATE SYS_ROLE set " +
  "role name = #{roleName}, " +
  "enabled = #{enabled}, " +
  "create by = #{createBy}, " +
  "create time = #{createTime, jdbcType=TIMESTAMP} " +
  "where id = #{id}"
  })
  int updateById(SysRole sysRole);
  ```

- @Provider

  略

## 动态SQL

### if

```xml
<select id="selectByUser" resultType="cn.edu.dlnu.simple.model.SysUser">
    select ID,
    USER_NAME userName,
    USER_PASSWORD userPassword,
    USER_EMAIL userEmail,
    USER_INFO userInfo,
    HEAD_IMG headImg,
    CREATE_TIME createTime
    FROM SYS_USER
    WHERE 1 = 1
    <if test="userName != null and userName != ''">
        and USER_NAME like concat('%', #{userName}, '%')
    </if>
    <if test="userEmail != null and userEmail != ''">
        and USER_EMAIL = #{userEmail}
    </if>
</select>
```

if中的test要符合OGNL表达式，结果是true或false，非0值为true，0为false

### choose

```xml
<select id="selectByIdOrUserName" resultType="cn.edu.dlnu.simple.model.SysUser">
    select ID,
    USER_NAME userName,
    USER_PASSWORD userPassword,
    USER_EMAIL userEmail,
    USER_INFO userInfo,
    HEAD_IMG headImg,
    CREATE_TIME createTime
    FROM SYS_USER
    WHERE 1 = 1
    <choose>
        <when test="id != null">
            and id = #{id}
        </when>
        <when test="userName != null and userName != ''">
            and userName = #{userName}
        </when>
        <otherwise>
            and 1 = 2
        </otherwise>
    </choose>
</select>
```

when 相当于一个if，otherwise相当于一个else

### where

```xml
<select id="selectByUser" resultType="cn.edu.dlnu.simple.model.SysUser">
        select ID,
            USER_NAME userName,
            USER_PASSWORD userPassword,
            USER_EMAIL userEmail,
            USER_INFO userInfo,
            HEAD_IMG headImg,
            CREATE_TIME createTime
        FROM SYS_USER
        WHERE 1 = 1
        <if test="userName != null and userName != ''">
            and USER_NAME like concat('%', #{userName}, '%')
        </if>
        <if test="userEmail != null and userEmail != ''">
            and USER_EMAIL = #{userEmail}
        </if>

    </select>

    <select id="selectByUser" resultType="cn.edu.dlnu.simple.model.SysUser">
        select ID,
        USER_NAME userName,
        USER_PASSWORD userPassword,
        USER_EMAIL userEmail,
        USER_INFO userInfo,
        HEAD_IMG headImg,
        CREATE_TIME createTime
        FROM SYS_USER
        <where>
            <if test="userName != null and userName != ''">
                AND USER_NAME LIKE concat('%', #{userName}, '%')
            </if>
            <if test="userEmail != null and userEmail != ''">
                AND USER_EMAIL = #{userEmail}
            </if>
        </where>
    </select>
```

如果不使用where标签，我们就需要加上一个一定成立的等式，因为我们每一个**if中都需要有and**，不加等式语法有问题了。

下面的那个使用了where，Mybatis自动去掉and

### set

### trim

### foreach

```xml
<select id="selectByidList" resultType="cn.edu.dlnu.simple.model.SysUser">
    select ID,
    USER_NAME userName,
    USER_PASSWORD userPassword,
    USER_EMAIL userEmail,
    USER_INFO userInfo,
    HEAD_IMG headImg,
    CREATE_TIME createTime
    FROM SYS_USER
    WHERE ID IN
    <foreach collection="list" open="(" close=")" separator="," item="id" index="i">
        #{id}
    </foreach>
</select>
```

Collection 集合的种类，可以是list，或者是set，或者是array，只要是能进行迭代的都可以

item 从迭代对象中取出的每一个值的引用

index 索引的属性名，数组等数据结构的是下标，map的是key

open 整个循环的内容开头的字符串

close 整个循环的内容结尾的字符串

separator 每次循环的分隔符

最后相当于where id in (1, 2, 3)

:yellow_heart:当我们的参数为一个数组参数或集合参数的时候，默认会转换成Map，为集合的时候，map.put("collection", reference),map.put("list", reference);当为数组的时候，map.put("array", reference) 这样就可以在collection中与参数类型对应起来了

我们使用@Param指定每一个参数的名字，这样我们就可以在collection中直接指定名称了

```xml
<select id="selectByidList" resultType="cn.edu.dlnu.simple.model.SysUser">
    select ID,
    USER_NAME userName,
    USER_PASSWORD userPassword,
    USER_EMAIL userEmail,
    USER_INFO userInfo,
    HEAD_IMG headImg,
    CREATE_TIME createTime
    FROM SYS_USER
    WHERE ID IN
    <foreach collection="idList" open="(" close=")" separator="," item="id" index="i">
        #{id}
    </foreach>
</select>

List<SysUser> selectByidList(@Param("idList") List<Long> idList);
```

如果参数是Map，item作为map的val，index作为map的key，collection指定名字，或者是使用_parameter代替

### bind用法

⚠️：明天继续

## 高级查询

### 一对一映射

#### 自动映射处理一对一映射

```xml
<select id="selectUserAndRoleById" resultType="cn.edu.dlnu.simple.model.SysUser">
    SELECT u.ID id,
    u.USER_NAME userName,
    u.USER_PASSWORD userPassword,
    u.USER_EMAIL userEmail,
    u.CREATE_TIME createTime,
    u.HEAD_IMG headImg,
    u.USER_INFO userInfo,
    r.ID "sysRole.Id",
    r.ROLE_NAME "sysRole.roleName",
    r.ENABLED "sysRole.enable",
    r.CREATED_BY "sysRole.createBy",
    r.CREATED_TIME "sysRole.createTime"
    FROM SYS_USER u
    INNER JOIN SYS_USER_ROLE ur on u.ID = ur.USER_ID
    INNER JOIN SYS_ROLE r on r.ID = ur.ROLE_ID
    WHERE u.ID = #{id}
</select>
```

直接在列名的位置上添加实体类中的别名，如果里面有其他的对象，我们需要使用对象引用.属性的方式

`r.ID "sysRole.Id"`

#### 使用resultMap进行映射关系的配置

```xml
<resultMap id="userMap" type="cn.edu.dlnu.simple.model.SysUser">
    <id property="id" column="id"/>
    <result property="userName" column="USER_NAME"/>
    <result property="userPassword" column="USER_PASSWORD"/>
    <result property="userEmail" column="USER_EMAIL"/>
    <result property="userInfo" column="USER_INFO"/>
    <result property="headImg" column="HEAD_IMG" jdbcType="BLOB"/>
    <result property="createTime" column="CREATE_TIME" jdbcType="DATE"/>
</resultMap>
<resultMap id="userRoleMap" type="cn.edu.dlnu.simple.model.SysUser" extends="userMap">
        <result property="sysRole.Id" column="ROLE_ID"/>
        <result property="sysRole.roleName" column="ROLE_NAME"/>
        <result property="sysRole.enable" column="ENABLED"/>
        <result property="sysRole.createBy" column="CREATED_BY"/>
        <result property="sysRole.createTime" column="ROLE_CREATED_TIME"/>
    </resultMap>
<select id="selectUserAndRoleById" resultMap="userRoleMap">
        SELECT u.ID,
               u.USER_NAME,
               u.USER_PASSWORD,
               u.USER_EMAIL,
               u.CREATE_TIME,
               u.HEAD_IMG,
               u.USER_INFO,
               r.ID ROLE_ID,
               r.ROLE_NAME,
               r.ENABLED,
               r.CREATED_BY,
               r.CREATED_TIME ROLE_CREATED_TIME
        FROM SYS_USER u
            INNER JOIN SYS_USER_ROLE ur on u.ID = ur.USER_ID
            INNER JOIN SYS_ROLE r on r.ID = ur.ROLE_ID
        WHERE u.ID = #{id}
    </select>
```

:yellow_heart: 在resultMap中的**column属性里不要指出表名**,如果有与别的表重名的情况，需要给列名起别名，然后在这个属性里添加别名引用

⚠️：下面这个代码有问题，property属性没有明白

使用<association>标签进行匹配

```xml
<resultMap id="userRoleMap" type="cn.edu.dlnu.simple.model.SysUser" extends="userMap">
    <association property="sysRole" columnPrefix="role_" 			                                                                      		       javaType="cn.edu.dlnu.simple.model.SysRole">
        <result property="sysRole.Id" column="ROLE_ID"/>
        <result property="sysRole.roleName" column="ROLE_NAME"/>
        <result property="sysRole.enable" column="ENABLED"/>
        <result property="sysRole.createBy" column="CREATED_BY"/>
        <result property="sysRole.createTime" column="ROLE_CREATED_TIME"/>
    </association>
</resultMap>
```

property 属性对应实体类的属性名

javaType 实体类的类型

resultMap 可以使用已经设计好了的map

columnPrefix 设置前缀，子标签result中的column我们就可以不用写前缀了,⚠️：当前没有使用明白，暂时不要用这个标签

### 一对多

#### collection集合的嵌套结果映射

```xml
<resultMap id="userRoleMap" type="cn.edu.dlnu.simple.model.SysUser" extends="userMap">
    <collection property="sysRoleList" javaType="List" ofType="cn.edu.dlnu.simple.model.SysRole">
        <id property="Id" column="ROLE_ID"/>
        <result property="roleName" column="ROLE_NAME"/>
        <result property="enable" column="ENABLED"/>
        <result property="createBy" column="CREATE_BY"/>
        <result property="createTime" column="ROLE_CREATED_TIME"/>
    </collection>
</resultMap>
```

对于一对多关系的时候，我们在把多个关系封装到对象中时，需要使用上面collection标签

property 定义的是多个关系的实体类中的引用

javaType 定义的是集合类型

ofType 定义的是集合中存储的类型

resultMap 我们可以应用其他地方定义好了的

:yellow_heart:如果我们想要引用其他mapper中定义的resultMap，我们需要把包名进行引用

```xml
<resultMap id="userRoleMap" type="cn.edu.dlnu.simple.model.SysUser" extends="userMap">
        <collection property="sysRoleList" javaType="List" 	ofType="cn.edu.dlnu.simple.model.SysRole" 	resultMap="cn.edu.dlnu.simple.mapper.RoleMapper.roleMap">
        </collection>
    </resultMap>
```

跟原来一样，通过包名来找到xml，然后在找到对应的resultMap

:yellow_heart:Mybatis合并重点(在处理一对多关系的时候进行的合并，说白了就是什么情况下要合并collection属性找到的值)

```
TRACE [main] - <==    Columns: ID, USER_NAME, USER_PASSWORD, USER_EMAIL, CREATE_TIME, HEAD_IMG, USER_INFO, ROLE_ID, ROLE_NAME, ENABLED, CREATE_BY, ROLE_CREATED_TIME

TRACE [main] - <==    Columns: ID, USER_NAME, USER_PASSWORD, USER_EMAIL, CREATE_TIME, HEAD_IMG, USER_INFO, ROLE_ID, ROLE_NAME, ENABLED, CREATE_BY, ROLE_CREATED_TIME

TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 1, 管理员, 1, 1, 2016-04-01 17:00:00.0

TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 2, 普通用户, 1, 1, 2016-04-01 17:00:00.0

DEBUG [main] - <==      Total: 2

SysUser{id=1, userName='admin', userPassword='123456', userEmail='admin@mybatis.com', userInfo='管理员', headImg=null, createTime=Sat Apr 02 00:00:00 CST 2016, sysRoleList=[SysRole{Id=1, roleName='管理员', enable='1', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016}, SysRole{Id=2, roleName='普通用户', enable='1', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016}]}
```

我们可以看出在日志中查询到了两条数据，但是最后合并成了一条

在处理结果的时候，首先先判断结果是否相等，如果结果相同，就保留第一个结果

判断结果相同的方法在于我们配置的<id>属性，我们在resultMap标签下面定义的id属性，如果相同则合并，如果我们没有定义<id>标签，那么Mybatis就会匹配所有的result属性，全都相同了就合并，**尽量使用id属性定义**，效率高

在比较的时候，会对嵌套查询的每一级对象进行查询。在这一级中，如果定义了id标签，就按照id标签中的属性进行查询，没有定义id，就按照全部的result标签的值进行匹配，当匹配相同的时候，在下一级进行信息的合并

e.g.我们现在有三条数据

```
TRACE [main] - <==    Columns: ID, USER_NAME, USER_PASSWORD, USER_EMAIL, CREATE_TIME, HEAD_IMG, USER_INFO, ROLE_ID, ROLE_NAME, ENABLED, CREATE_BY, ROLE_CREATED_TIME

TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 1, 管理员, 1, 1, 2016-04-01 17:00:00.0

TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 2, 普通用户, 1, 1, 2016-04-01 17:00:00.0

TRACE [main] - <==        Row: 2, test, 123456, test@mybatis.com, 2016-04-02 17:00:00.0, 
<<BLOB>>, <<BLOB>>, 2, 普通用户, 1, 1, 2016-04-01 17:00:00.0
```

```xml
<resultMap id="userMap" type="cn.edu.dlnu.simple.model.SysUser">
    <id property="userPassword" column="USER_PASSWORD"/>
    <result property="userName" column="USER_NAME"/>
    <result property="id" column="ID"/>
    <result property="userEmail" column="USER_EMAIL"/>
    <result property="userInfo" column="USER_INFO"/>
    <result property="headImg" column="HEAD_IMG" jdbcType="BLOB"/>
    <result property="createTime" column="CREATE_TIME" jdbcType="DATE"/>
</resultMap>
<resultMap id="roleMap" type="cn.edu.dlnu.simple.model.SysRole">
        <id property="Id" column="ROLE_ID"/>
        <result property="roleName" column="ROLE_NAME"/>
        <result property="enable" column="ENABLED"/>
        <result property="createBy" column="CREATE_BY"/>
        <result property="createTime" column="ROLE_CREATED_TIME"/>
    </resultMap>
```

**在上面的例子中，我们定义的resultMap的id为userPassword，按照我们的匹配要求，在userMap这一级中由于我们的数据的password都是123456，所以我们 会把这三条数据合并成一条数据，继续下一级的查询，在下一级中前两条数据的根据id匹配不同，分两个进行存储，第三条的id跟第二条相同，则保留第二条，不保存第三条**

```
DEBUG [main] - ==>  Preparing: SELECT u.ID, u.USER_NAME, u.USER_PASSWORD, u.USER_EMAIL, u.CREATE_TIME, u.HEAD_IMG, u.USER_INFO, r.ID ROLE_ID, r.ROLE_NAME, r.ENABLED ENABLED, r.CREATED_BY CREATE_BY, r.CREATED_TIME ROLE_CREATED_TIME, sr.PRIVILEGE_NAME PRIVILEGE_NAME, sr.PRIVILEGE_URL PRIVILEGE_URL FROM SYS_USER u INNER JOIN SYS_USER_ROLE ur on u.ID = ur.USER_ID INNER JOIN SYS_ROLE r on r.ID = ur.ROLE_ID INNER JOIN SYS_ROLE_PRIVILEGE srp on r.ID = srp.ROLE_ID INNER JOIN SYS_PRIVILEGE sr on sr.ID = srp.PRIVILEGE_ID 

DEBUG [main] - ==> Parameters: 

TRACE [main] - <==    Columns: ID, USER_NAME, USER_PASSWORD, USER_EMAIL, CREATE_TIME, HEAD_IMG, USER_INFO, ROLE_ID, ROLE_NAME, ENABLED, CREATE_BY, ROLE_CREATED_TIME, PRIVILEGE_NAME, PRIVILEGE_URL

TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 1, 管理员, 1, 1, 2016-04-01 17:00:00.0, 用户管理, /users
TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 1, 管理员, 1, 1, 2016-04-01 17:00:00.0, 角色管理, /roles
TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 1, 管理员, 1, 1, 2016-04-01 17:00:00.0, 系统日志, /logs
TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 2, 普通用户, 1, 1, 2016-04-01 17:00:00.0, 人员维护, /persons
TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 2, 普通用户, 1, 1, 2016-04-01 17:00:00.0, 单位维护, /companies
TRACE [main] - <==        Row: 1001, test, 123456, test@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 2, 普通用户, 1, 1, 2016-04-01 17:00:00.0, 人员维护, /persons
TRACE [main] - <==        Row: 1001, test, 123456, test@mybatis.com, 2016-04-02 17:00:00.0, <<BLOB>>, <<BLOB>>, 2, 普通用户, 1, 1, 2016-04-01 17:00:00.0, 单位维护, /companies
DEBUG [main] - <==      Total: 7

SysUser{id=1, userName='admin', userPassword='123456', userEmail='admin@mybatis.com', userInfo='管理员', headImg=null, createTime=Sat Apr 02 00:00:00 CST 2016, 
sysRoleList=[SysRole{Id=1, roleName='管理员', enable='1', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016, 

sysPrivileges=
[SysPrivilege{Id=1, privilegeName='用户管理', privilegeURL='/users'}, 
SysPrivilege{Id=2, privilegeName='角色管理', privilegeURL='/roles'}, 
SysPrivilege{Id=3, privilegeName='系统日志', privilegeURL='/logs'}]},

SysRole{Id=2, roleName='普通用户', enable='1', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016, 
sysPrivileges=[SysPrivilege{Id=4, privilegeName='人员维护', privilegeURL='/persons'}, SysPrivilege{Id=5, privilegeName='单位维护', privilegeURL='/companies'}]}]}



SysUser{id=1001, userName='test', userPassword='123456', userEmail='test@mybatis.com', userInfo='测试用户', headImg=null, createTime=Sat Apr 02 00:00:00 CST 2016, 
sysRoleList=
[SysRole{Id=2, roleName='普通用户', enable='1', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016, 
sysPrivileges=[
SysPrivilege{Id=4, privilegeName='人员维护', privilegeURL='/persons'}, SysPrivilege{Id=5, privilegeName='单位维护', privilegeURL='/companies'}]}]}


```

查询用户-角色-权限一样

:yellow_heart:在某一级定义resultMap的时候，设置collection的时候只要设置这一级的属性以及collection，不要在这一级上把剩下的全部collection都设置出来，会造成null(⚠️:这里面的原因还没有想)

```xml
<resultMap id="userRolePrivilege" type="cn.edu.dlnu.simple.model.SysUser" extends="userRoleMap">
        <collection property="sysRoleList" javaType="List" ofType="cn.edu.dlnu.simple.model.SysRole" resultMap="cn.edu.dlnu.simple.mapper.RoleMapper.rolePrivilegesMap"/>
    </resultMap>

<resultMap id="rolePrivilegesMap" type="cn.edu.dlnu.simple.model.SysRole" extends="roleMap">
        <collection property="sysPrivileges" javaType="List" ofType="cn.edu.dlnu.simple.model.SysPrivilege" resultMap="cn.edu.dlnu.simple.mapper.PrivilegeMapper.privilegeMap"/>
    </resultMap>

<resultMap id="privilegeMap" type="cn.edu.dlnu.simple.model.SysPrivilege">
        <id property="Id" column="SYS_PRIVILEGE_ID"/>
        <result property="privilegeName" column="PRIVILEGE_NAME"/>
        <result property="privilegeURL" column="PRIVILEGE_URL"/>
    </resultMap>

<select id="selectAllUserAndRoles" resultMap="userRolePrivilege">
        SELECT u.ID,
            u.USER_NAME,
            u.USER_PASSWORD,
            u.USER_EMAIL,
            u.CREATE_TIME,
            u.HEAD_IMG,
            u.USER_INFO,
            r.ID ROLE_ID,
            r.ROLE_NAME,
            r.ENABLED ENABLED,
            r.CREATED_BY CREATE_BY,
            r.CREATED_TIME ROLE_CREATED_TIME,
            sr.ID SYS_PRIVILEGE_ID,
            sr.PRIVILEGE_NAME PRIVILEGE_NAME,
            sr.PRIVILEGE_URL PRIVILEGE_URL
        FROM SYS_USER u
            INNER JOIN SYS_USER_ROLE ur on u.ID = ur.USER_ID
            INNER JOIN SYS_ROLE r on r.ID = ur.ROLE_ID
            INNER JOIN SYS_ROLE_PRIVILEGE srp on r.ID = srp.ROLE_ID
            INNER JOIN SYS_PRIVILEGE sr on sr.ID = srp.PRIVILEGE_ID
    </select>
```

#### collection集合的嵌套查询

⚠️:下次再看

### 鉴别器

类似于java的switch

<discriminator>标签

| column     | 设置鉴别比较级的列，就是对表中的哪一列进行操作 相当于switch() |
| :--------- | ------------------------------------------------------------ |
| javaType   | 指定进行 switch操作的列的类型                                |
| value      | 指定匹配值，相当于case()                                     |
| resultMap  | 匹配后进行的映射，优先于**resultType**                       |
| resultType | 匹配后的映射                                                 |

```
DEBUG [main] - ==>  Preparing: SELECT ID ROLE_ID, ROLE_NAME, ENABLED, CREATED_BY CREATE_BY, CREATED_TIME ROLE_CREATED_TIME FROM SYS_ROLE INNER JOIN SYS_USER_ROLE sur ON sur.ROLE_ID = SYS_ROLE.ID WHERE sur.USER_ID = ? 

DEBUG [main] - ==> Parameters: 1(Long)

TRACE [main] - <==    Columns: ROLE_ID, ROLE_NAME, ENABLED, CREATE_BY, ROLE_CREATED_TIME
TRACE [main] - <==        Row: 1, 管理员, 0, 1, 2016-04-01 17:00:00.0
TRACE [main] - <==        Row: 2, 普通用户, 1, 1, 2016-04-01 17:00:00.0
DEBUG [main] - <==      Total: 2

SysRole{Id=1, roleName='管理员', enable='0', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016, sysPrivileges=[]}// 由于我的语句的问题，没有获得结果，之后修改

SysRole{Id=2, roleName='普通用户', enable='1', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016, sysPrivileges=null}
```

```xml
<resultMap id="rolePrivilegeListMapChoose" type="cn.edu.dlnu.simple.model.SysRole">
        <discriminator javaType="int" column="ENABLED">
            <case value="0" resultMap="rolePrivilegesMap"/>
            <case value="1" resultMap="roleMap"/>
        </discriminator>
    </resultMap>

<select id="selectRoleByUserIdChoose" resultMap="rolePrivilegeListMapChoose">
        SELECT
            ID ROLE_ID,
            ROLE_NAME,
            ENABLED,
            CREATED_BY CREATE_BY,
            CREATED_TIME ROLE_CREATED_TIME
        FROM SYS_ROLE
        INNER JOIN SYS_USER_ROLE sur ON sur.ROLE_ID = SYS_ROLE.ID
        WHERE sur.USER_ID = #{userId}
    </select>
```

查询结果通过case发送给不同的resultMap

## 存储过程

略

## MyBatis缓存

### 一级缓存

```java
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

DEBUG [main] - ==>  Preparing: SELECT * FROM SYS_USER WHERE ID = ? 
DEBUG [main] - ==> Parameters: 1(Long)
TRACE [main] - <==    Columns: ID, USER_NAME, USER_PASSWORD, USER_EMAIL, USER_INFO, HEAD_IMG, CREATE_TIME
TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, <<BLOB>>, <<BLOB>>, 2016-04-02 17:00:00.0
DEBUG [main] - <==      Total: 1
SysUser{id=1, userName=重点在这里'admin'
    , userPassword='123456', userEmail='admin@mybatis.com', userInfo='管理员', headImg=null, createTime=Sat Apr 02 00:00:00 CST 2016, sysRoleList=null}
SysUser{id=1, userName=重点在这里'New Name'
    , userPassword='123456', userEmail='admin@mybatis.com', userInfo='管理员', headImg=null, createTime=Sat Apr 02 00:00:00 CST 2016, sysRoleList=null}
SysUser{id=1, userName=重点在这里'New Name'
    , userPassword='123456', userEmail='admin@mybatis.com', userInfo='管理员', headImg=null, createTime=Sat Apr 02 00:00:00 CST 2016, sysRoleList=null}

SysUser{id=1, userName=重点在这里'New Name1'
    , userPassword='123456', userEmail='admin@mybatis.com', userInfo='管理员', headImg=null, createTime=Sat Apr 02 00:00:00 CST 2016, sysRoleList=null}
SysUser{id=1, userName=重点在这里'New Name1'
    , userPassword='123456', userEmail='admin@mybatis.com', userInfo='管理员', headImg=null, createTime=Sat Apr 02 00:00:00 CST 2016, sysRoleList=null}


DEBUG [main] - ==>  Preparing: SELECT * FROM SYS_USER WHERE ID = ? 
DEBUG [main] - ==> Parameters: 1(Long)
TRACE [main] - <==    Columns: ID, USER_NAME, USER_PASSWORD, USER_EMAIL, USER_INFO, HEAD_IMG, CREATE_TIME
TRACE [main] - <==        Row: 1, admin, 123456, admin@mybatis.com, <<BLOB>>, <<BLOB>>, 2016-04-02 17:00:00.0
DEBUG [main] - <==      Total: 1
SysUser{id=1, userName=重点在这里'admin', 
        userPassword='123456', userEmail='admin@mybatis.com', userInfo='管理员', headImg=null, createTime=Sat Apr 02 00:00:00 CST 2016, sysRoleList=null}

```

当前没有开启二级缓存，在同一个缓存使用同一个session进行查询的时候，我们发现前两个共用了同一个对象，并没有重新进行查询，第二个我们用了一个新的session，这个时候没有设置二级缓存，我们的查询就重新开始

:yellow_heart:在这里我们看出如果启动一级缓存，如果我们改了一个对象，其他的使用同一个session创建的对象中的值都会改变，这时会产生问题，想要得到数据库中的值，但可能得到的是被修改过的值。

我们需要在<select>标签中设置flushCache为true这样就消除了一级缓存

### 二级缓存

存在于sqlSessionFactory生命周期中，如果存在多个Factroy，每一个Factroy中的缓存相互独立

#### XML中设置

在全局开启了之后，我们需要在某一个Mapper.xml中开启

我们先要让UserMapper启动，需要在命名空间中添加<cache />标签

可使用的属性

eviction 回收算法 默认会使用Least Recently Used算法进行数据的回收，每60s刷新一次

		可选的其他算法"FIFO", "SOFT"(移除处于垃圾回收状态的和软引用的对象), "WEAK"(更积极的移除垃圾回		  收状态和弱引用状态的对象)

flushinterval  刷新间隔，毫秒字段，默认不进行刷新，仅仅在调用语句的时候刷新

Size    缓存数目

readOnly true或false，只读时不能进行修改

```xml
<cache eviction="FIFO" flushlnterval="60000" size="512" readOnly="true" />
```

#### 接口中设置

```java
@CacheNamespace(eviction = FifoCache.class, flushInterval = 60000, size = 512, readWrite = true)
public interface RoleMapper
```

:yellow_heart:这两个位置上不能同时进行设置，如果都配置了就会报错误，当配置xml的时候，接口中定义`@CacheNarnespaceRef(RoleMapper . class)`进行替代，当配置接口的时候，在xml文件中配置`<cache-ref narnespace=” tk.rnybatis.sirnple .rnapper.RoleMapper”/>` 

⚠️如果我们配置的是可读可写的缓存，需要使用序列化，没有明白，但确实需要把实体类进行序列化以后才能使用

:yellow_heart:我们在一个sqlSession中进行查找的时候，查找结果只会单纯的放在一级缓存中，如果此时其他的sqlSession查找同样的数据将会从数据库中查找，只有把sqlSession关闭的时候，才会把数据放在sqlSessionFectory的缓存中，也就是二级缓存中

```java
@Test
public void testSelectById() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
    SysRole sysRole = roleMapper.selectById(1l);
    sysRole.setRoleName("xzp");
    System.out.println(sysRole);
    没有进行关闭操作
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
    RoleMapper roleMapper1 = sqlSession1.getMapper(RoleMapper.class);
    SysRole sysRole1 = roleMapper1.selectById(1l);
    System.out.println(sysRole1);
    sqlSession1.close();
}

DEBUG [main] - ==>  Preparing: SELECT ID, ROLE_NAME, ENABLED, CREATED_BY, CREATED_TIME FROM SYS_ROLE WHERE ID = ? 
DEBUG [main] - ==> Parameters: 1(Long)
TRACE [main] - <==    Columns: ID, ROLE_NAME, ENABLED, CREATED_BY, CREATED_TIME
TRACE [main] - <==        Row: 1, 管理员, 0, 1, 2016-04-01 17:00:00.0
DEBUG [main] - <==      Total: 1
SysRole{Id=1, roleName='xzp', enable='0', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016, sysPrivileges=null}

DEBUG [main] - Cache Hit Ratio [cn.edu.dlnu.simple.mapper.RoleMapper]: 0.0
DEBUG [main] - ==>  Preparing: SELECT ID, ROLE_NAME, ENABLED, CREATED_BY, CREATED_TIME FROM SYS_ROLE WHERE ID = ? 
DEBUG [main] - ==> Parameters: 1(Long)
TRACE [main] - <==    Columns: ID, ROLE_NAME, ENABLED, CREATED_BY, CREATED_TIME
TRACE [main] - <==        Row: 1, 管理员, 0, 1, 2016-04-01 17:00:00.0
DEBUG [main] - <==      Total: 1
SysRole{Id=1, roleName='管理员', enable='0', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016, sysPrivileges=null}

进行关闭操作
DEBUG [main] - ==>  Preparing: SELECT ID, ROLE_NAME, ENABLED, CREATED_BY, CREATED_TIME FROM SYS_ROLE WHERE ID = ? 
DEBUG [main] - ==> Parameters: 1(Long)
TRACE [main] - <==    Columns: ID, ROLE_NAME, ENABLED, CREATED_BY, CREATED_TIME
TRACE [main] - <==        Row: 1, 管理员, 0, 1, 2016-04-01 17:00:00.0
DEBUG [main] - <==      Total: 1
SysRole{Id=1, roleName='xzp', enable='0', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016, sysPrivileges=null}
DEBUG [main] - Cache Hit Ratio [cn.edu.dlnu.simple.mapper.RoleMapper]: 0.5
SysRole{Id=1, roleName='xzp', enable='0', createBy='1', createTime=Fri Apr 01 17:00:00 CST 2016, sysPrivileges=null}

```

### EhCache

### Redis

### 脏数据的产生

我们在某一个mapper中定义了一个多表查询的select，并缓存在了这个sqlSession中，若果多表中的其他的某一个表出现了插入或修改的操作，并不会影响到这个sqlSession中的缓存，因此出现了脏数据

解决的方案就是让这几个session使用同一个factory产生，这样就会自动更新了

## 插件开发

### 拦截器接口

```java
<plugins>
    <plugin interceptor="cn.edu.dlnu.simple.plugin.testInterceptor">
       <property name="pro1" value="val1"/>
       <property name="pro2" value="val2"/>
    </plugin>
</plugins>

public interface Interceptor {

	要执行的拦截方法
	invocation.getTarget() 	获取当前被拦截的对象
	invocation.getMethod()	获取当前被拦截的方法
	invocation.getArgs()	获取被拦截方法的参数哦
	invocation.proceed()	执行被拦截对象的方法
	Object intercept(Invocation invocation) throws Throwable; 
	
	targer就是拦截器要拦截的对象，会在创建被拦截的接口实现类的时候被调用，一般内部的方法
	return Plain.wrap(target, this);
	自动判断拦截器的签名和被拦截器的对象接口是否匹配
	Object plugin(Object target);
	
	拦截器的参数通过这个方法把<property>中的参数传递给拦截器，properties能得到配置的参数哦
	void setProperties(Properties);         
}
```

实现接口的时候，需要配置注解

```java
@Intercepts({ 
    @Signature (
  	设置拦截接口，可选值有Executor，ResultSetHandler，ParameterHandler，StatementHandler
    type = ResultSetHandler.class,
    设置拦截器接口的方法，四个接口对应的方法
    method = "handleResultSets",
    拦截方法的参数类型数组，方法名和参数类型确定唯一的方法
    args = {Statement.class})
})
public class ResultSetinterceptor implements Interceptor
```

#### Executor接口

```java
int update (MappedStatement ms, Object parameter) throws SQLException
```

会在所有Insert, update, delete执行时被调用，如果想要拦截者三类操作，可以拦截这个方法

```java
<E> List<E> query (MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException
```

会在所有的select执行时被调用

```java
<E> Cursor<E> queryCursor (MappedStatement ms, Object parameter,RowBounds rowBounds) throws SQLException
```

查询的返回值为Cursor时被调用

```java
List<BatchResult> flushStatements() throws SQLException
```

SqlSession调用flushStatements或者执行@Flush的时候被调用

```java
void commit (boolean required) throws SQLException
void rollback(boolean required) throws SQLException
```

SqlSession调用commit和rollback

```java
Transaction getTransaction()
```

通过SqlSession获取数据库连接的时候被调用

#### ParameterHandler接口

```java
Object getParameterObject ()
```

存储过程处理出参的时候调用

```java
void setParameters(PreparedStatement ps) throws SQLException
```

所有数据库方法设置SQL参数时被调用

#### ResultSetHandler接口

```java
<E> List<E> handleResultSets (Statement stmt) throws SQLException;
```

除了存储过程及返回值为Cursor以外的查询方法中被调用

```java
<E> Cursor<E> handleCursorResultSets (Statement stmt) throws SQLException;
```

只会在返回值为Cursor时调用

```java
void handleOutputParameters (CallableStatement cs) throws SQLException;
```

只想存储过程出参时调用

#### StatementHandler接口

```java
Statement prepare (Connection connection, Integer transactionTimeout) throws SQLException;
```

数据库执行前被调用，优先于当前接口中的其他方法被执行

```java
void parameterize (Statement statement) throws SQLException;
```

在prepare执行之后被执行，用于处理参数信息

```java
int batch (Statement statement) throws SQLException;
```

全剧配置中defalutExecutorType="BATCH"时被调用

```java
<E> List<E> query (Statement statement,ResultHandler resultHandler) throws SQLException ;
```

select方法被执行时调用

```java
<E> Cursor<E> queryCursor (Statement statement) throws SQLException;
```

返回值为Cursor的查询中被调用

⚠️：具体的代码并没有实现，理解不是完善

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