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

package de.arraying.guardian.invalids;

import de.arraying.guardian.Wrap;
import de.arraying.guardian.Wrappable;
import de.arraying.guardian.dummy.Address;
import de.arraying.guardian.dummy.Blank;

public interface InvalidCountry extends Wrappable {

    String getNothing();

    @Wrap("get")
    String getNonExistent();

    @Wrap("getNameWithException")
    String getException();

    @Wrap("getSomething")
    String getPrivate();

    @Wrap("isPrimitive")
    boolean getPrimitive();

    @Wrap("getCities")
    String[] getArray();

    @Wrap("imprison")
    String arguments(String in);

    @Wrap("getName")
    Blank getBlank();

    @Wrap("getPresident")
    Address getPresident();
}
