package com.core.distributed.transaction;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;
import com.core.distributed.CatInvocation;
import com.core.distributed.CatParticipant;
import com.core.distributed.context.TransactionContextBean;
import com.core.distributed.context.TransactionContextLocal;
import com.bobo.annotation.JTATransaction;
import com.bobo.utils.ComUtils;
import com.bobo.utils.SpringContextUtil;

import java.lang.reflect.Method;
import java.util.Objects;

public class CatInvocationHandler extends InvokerInvocationHandler {
    private Object target;

    public CatInvocationHandler(final Invoker<?> handler) {
        super(handler);
    }

    public <T> CatInvocationHandler(final T target, final Invoker<T> invoker) {
        super(invoker);
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final JTATransaction jta = method.getAnnotation(JTATransaction.class);
        final Class<?>[] arguments = method.getParameterTypes();
        final Class clazz = method.getDeclaringClass();
        if (Objects.nonNull(jta)){
            //获取程序上下文的事物
            final TransactionContextBean bean = TransactionContextLocal.getInstance().get();
            try{
                if (Objects.nonNull(bean))//如果没有事务直接执行
                    return super.invoke(target, method, args);
                //获取执行器
                final CatInvocation invocation = new CatInvocation(clazz,method.getName(),arguments,args);
                StringBuilder builder = new StringBuilder();
                if (jta.tags().length() > 0) {
                    builder.append(jta.destination());
                    builder.append(",");
                    builder.append(jta.tags());
                }else{
                    builder.append(jta.destination());
                }
                final Integer pattern = jta.pattern().getCode();
                //创建参与者
                final CatParticipant participant = new CatParticipant(bean.getTransId(), builder.toString(), pattern,invocation);
                //获取事务引擎
                final CatTransactionEngine transactionEngine = SpringContextUtil.getBean(CatTransactionEngine.class);
                //将任务参与者 注入到事务引擎
                transactionEngine.registerParticipant(participant);
                //执行当前方法
                return super.invoke(target, method, args);
            }catch (Throwable throwable){
                throwable.printStackTrace();
                return ComUtils.getDefaultValue(method.getReturnType());
            }
        }else{
            return super.invoke(target,method,args);
        }
    }


}
