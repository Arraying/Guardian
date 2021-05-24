package de.arraying.guardian;

import java.lang.reflect.Proxy;

public class GuardianWrap {

    private final GuardianContext context;
    private final Object object;

    public GuardianWrap(GuardianContext context, Object object) {
        this.context = context;
        this.object = object;
    }

    public Object get() {
        Class<? extends Wrappable> iface = context.getAssociations().get(object.getClass());
        if (iface == null) {
            throw new GuardianWrapException("Cannot wrap " + object.getClass() + "; no association found");
        }
        return Proxy.newProxyInstance(iface.getClassLoader(),
            new Class[]{ iface },
            new GuardianProxy(context, object)
        );
    }
}
