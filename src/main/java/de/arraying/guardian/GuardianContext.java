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

import java.util.HashMap;
import java.util.Map;

/**
 * The context where all the encapsulation associations are defined.
 */
public class GuardianContext {

    private final Map<Class<?>, Class<? extends Wrappable>> associations = new HashMap<>();

    /**
     * Associates an object with its wrapper.
     * If an association is already present for the object, it will be replaced.
     * @param clazz The object to encapsulate.
     * @param wrappable The wrapper.
     * @throws IllegalArgumentException If either the object or wrapper are null.
     */
    public synchronized void associate(Class<?> clazz, Class<? extends Wrappable> wrappable)
            throws IllegalArgumentException {
        if (clazz == null) {
            throw new IllegalArgumentException("Target class is null");
        }
        if (wrappable == null) {
            throw new IllegalArgumentException("Replacement class is null");
        }
        if (clazz.equals(wrappable)) {
            throw new IllegalArgumentException("Target and replacement cannot be the same");
        }
        if (!wrappable.isInterface()) {
            throw new IllegalArgumentException("Replacement must be an interface");
        }
        this.associations.put(clazz, wrappable);
    }

    /**
     * Removes an association of an encapsulating object.
     * If no association exist, this will drop silently.
     * @param clazz The object that is encapsulated.
     * @throws IllegalArgumentException If the object is null.
     */
    public synchronized void dissociate(Class<?> clazz)
            throws IllegalArgumentException {
        if (clazz == null) {
            throw new IllegalArgumentException("Class is null");
        }
        this.associations.remove(clazz);
    }

    /**
     * Encapsulates an object.
     * @param object An instance of an object.
     * @return Null if null, or a wrapped version.
     */
    public Object wrap(Object object) {
        if (object == null) {
            return null;
        }
        return new GuardianWrap(this, object).get();
    }

    synchronized Map<Class<?>, Class<? extends Wrappable>> getAssociations() {
        return associations;
    }
}
