
package com.core.distributed.springcloud.feign;


import com.core.distributed.CatInvocation;
import com.core.distributed.CatParticipant;
import com.core.distributed.context.TransactionContextBean;
import com.core.distributed.context.TransactionContextLocal;
import com.core.distributed.transaction.CatTransactionEngine;
import com.core.utils.SpringContextUtil;
import com.bobo.annotation.JTATransaction;
import feign.InvocationHandlerFactory.MethodHandler;
import feign.Target;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;


public class MythFeignHandler implements InvocationHandler {

    private Target<?> target;

    private Map<Method, MethodHandler> handlers;

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            final JTATransaction transaction = method.getAnnotation(JTATransaction.class);
            if (Objects.isNull(transaction)) {
                return this.handlers.get(method).invoke(args);
            }
            try {
                final CatTransactionEngine transactionEngine = SpringContextUtil.getBean(CatTransactionEngine.class);
                final CatParticipant participant = buildParticipant(transaction, method, args);
                if (Objects.nonNull(participant)) {
                    transactionEngine.registerParticipant(participant);
                }
                return this.handlers.get(method).invoke(args);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
        }
    }

    private CatParticipant buildParticipant(final JTATransaction transaction, final Method method,
                                             final Object[] args) {
        final TransactionContextBean mythTransactionContext = TransactionContextLocal.getInstance().get();

        CatParticipant participant;
        if (Objects.nonNull(mythTransactionContext)) {
            final Class declaringClass = transaction.target();
            CatInvocation mythInvocation =
                    new CatInvocation(declaringClass, method.getName(), method.getParameterTypes(), args);
            final Integer pattern = transaction.pattern().getCode();
            //封装调用点
            participant = new CatParticipant(mythTransactionContext.getTransId(),
                    transaction.destination(),
                    pattern,
                    mythInvocation);
            return participant;
        }
        return null;
    }

    public void setTarget(final Target<?> target) {
        this.target = target;
    }

    public void setHandlers(final Map<Method, MethodHandler> handlers) {
        this.handlers = handlers;
    }

}
