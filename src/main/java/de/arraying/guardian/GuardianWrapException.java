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

/**
 * This exception gets thrown when the wrapping or invocation causes an exception.
 */
public class GuardianWrapException extends RuntimeException {

    /**
     * Creates an exception from a message.
     * @param message The message.
     */
    public GuardianWrapException(String message) {
        super(message);
    }

    /**
     * Creates an exception from a message and an underlying cause.
     * @param message The message.
     * @param cause The cause, such as an exception during method invocation.
     */
    public GuardianWrapException(String message, Throwable cause) {
        super(message, cause);
    }

}
