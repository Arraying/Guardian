/* Copyright 2021 Arraying
 *
 * This file is part of Guardian.
 *
 * Guardian is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Guardian is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Guardian. If not, see http://www.gnu.org/licenses/.
 */

package de.arraying.guardian;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GuardianProxy implements InvocationHandler {

    private final GuardianContext context;
    private final Object object;

    public GuardianProxy(GuardianContext context, Object object) {
        this.context = context;
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws GuardianWrapException {
        String targetName = getTargetMethod(method);
        if (targetName == null) {
            throw new GuardianWrapException(String.format("No wrap target specified: %s->%s#???",
                method.getName(),
                object.getClass().getName()
            ));
        }
        Method target;
        try {
            target = object.getClass().getDeclaredMethod(targetName);
        } catch (NullPointerException | IllegalArgumentException | NoSuchMethodException exception) {
            throw new GuardianWrapException(String.format("Could not bind %s->%s#%s: method %s(arguments=%d) does not exist",
                method.getName(),
                object.getClass().getName(),
                targetName,
                targetName,
                args == null ? 0 : args.length
            ));
        }
        if (!meetsCriteria(target)) {
            throw new GuardianWrapException(String.format("Could not bind %s->%s#%s: method %s does not fulfil: \n" +
                    "- Method must not return a primitive data type, except void\n" +
                    "- Method must not return an array",
                method.getName(),
                object.getClass().getName(),
                targetName,
                targetName
            ));
        }
        Object result;
        try {
            result = target.invoke(this.object);
        } catch (InvocationTargetException exception) {
            throw new GuardianWrapException(String.format("Could not invoke %s->%s#%s",
                method.getName(),
                object.getClass().getName(),
                targetName
            ), exception);
        } catch (IllegalAccessException exception) {
            throw new GuardianWrapException(String.format("Could not access %s->%s#%s: is it public?",
                method.getName(),
                object.getClass().getName(),
                targetName
            ), exception);
        }
        if (result == null) {
            return null;
        }
        Object resultClass = result.getClass();
        if (resultClass.equals(method.getReturnType())) {
            return result;
        }
        Class<? extends Wrappable> newIface = context.getAssociations().get(resultClass);
        if (newIface == null) {
            throw new GuardianWrapException(String.format("Could not return from %s->%s#%s: target method returned %s " +
                "which differs from wrapped method %s, and %s has no associations in the context",
                method.getName(),
                object.getClass().getName(),
                target,
                resultClass,
                method.getReturnType(),
                resultClass));
        }
        if (!newIface.equals(method.getReturnType())) {
            throw new GuardianWrapException(String.format("Could not return from %s->%s#%s: target method returned %s " +
                "which differs from wrapped method %s, and %s is associated with %s",
                method.getName(),
                object.getClass().getName(),
                target,
                resultClass,
                method.getReturnType(),
                resultClass,
                newIface
            ));
        }
        return new GuardianWrap(context, result).get();
    }

    private String getTargetMethod(Method method) {
        if (!method.isAnnotationPresent(Wrap.class)) {
            return null;
        }
        Wrap wrap = method.getAnnotation(Wrap.class);
        return wrap.value();
    }

    private boolean meetsCriteria(Method method) {
        Class<?> returnType = method.getReturnType();
        if (!returnType.equals(void.class) && returnType.isPrimitive()) {
            return false;
        }
        return !method.getReturnType().isArray();
    }
}
