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
