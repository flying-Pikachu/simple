package cn.edu.dlnu.simple.plugin;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

/**
 * @ Author     ：xzp.
 * @ Date       ：Created in 2:25 PM 03/09/2018
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
public class testInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object o) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
