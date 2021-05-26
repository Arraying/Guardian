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

import de.arraying.guardian.dummy.Person;
import de.arraying.guardian.invalids.InvalidClass;
import de.arraying.guardian.invalids.InvalidInterface;
import de.arraying.guardian.real.RealPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuardianContextTest {

    private GuardianContext context;

    @BeforeEach
    void before() {
        context = new GuardianContext();
    }

    @Test
    void precondition() {
        assertEquals(0, context.getAssociations().size());
    }

    @Test
    void addValid() {
        assertDoesNotThrow(() -> context.associate(RealPerson.class, Person.class));
        assertTrue(context.getAssociations().containsKey(RealPerson.class));
        assertTrue(context.getAssociations().containsValue(Person.class));
    }

    @Test
    void addInvalid1() {
        assertThrows(IllegalArgumentException.class, () -> context.associate(null, Person.class));
        assertFalse(context.getAssociations().containsKey(null));
        assertFalse(context.getAssociations().containsValue(Person.class));
    }

    @Test
    void addInvalid2() {
        assertThrows(IllegalArgumentException.class, () -> context.associate(RealPerson.class, null));
        assertFalse(context.getAssociations().containsKey(RealPerson.class));
        assertFalse(context.getAssociations().containsValue(null));
    }

    @Test
    void addInvalid3() {
        assertThrows(IllegalArgumentException.class, () -> context.associate(InvalidInterface.class, InvalidInterface.class));
        assertFalse(context.getAssociations().containsKey(InvalidInterface.class));
        assertFalse(context.getAssociations().containsValue(InvalidInterface.class));
    }

    @Test
    void addInvalid4() {
        assertThrows(IllegalArgumentException.class, () -> context.associate(RealPerson.class, InvalidClass.class));
        assertFalse(context.getAssociations().containsKey(RealPerson.class));
        assertFalse(context.getAssociations().containsValue(InvalidClass.class));
    }

    @Test
    void removeNone() {
        assertDoesNotThrow(() -> context.dissociate(RealPerson.class));
        assertEquals(0, context.getAssociations().size());
    }

    @Test
    void removeValid() {
        context.associate(RealPerson.class, Person.class);
        assertEquals(1, context.getAssociations().size());
        assertDoesNotThrow(() -> context.dissociate(RealPerson.class));
        assertEquals(0, context.getAssociations().size());
    }

    @Test
    void removeInvalid() {
        context.associate(RealPerson.class, Person.class);
        assertEquals(1, context.getAssociations().size());
        assertThrows(IllegalArgumentException.class, () -> context.dissociate(null));
        assertEquals(1, context.getAssociations().size());
    }
}
