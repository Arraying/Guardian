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

package de.arraying.guardian.real;

public class RealCountry {

    public String getName() {
        return "Country";
    }

    public String getNameWithException() {
        throw new RuntimeException();
    }

    public String getNuclearLaunchCodes() {
        return "abc123";
    }

    private String getSomething() {
        return "Something";
    }

    public boolean isPrimitive() {
        return true;
    }

    public String[] getCities() {
        return new String[0];
    }

    public void playAnthem() {}

    public String imprison(String name) {
        return name;
    }

    public RealPerson getPresident() {
        return new RealPerson();
    }
}
