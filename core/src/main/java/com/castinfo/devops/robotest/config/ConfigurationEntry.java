/********************************************************************************
 * ROBOTEST
 * Copyright (C) 2018 CAST-INFO, S.A. www.cast-info.es
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.castinfo.devops.robotest.config;

/**
 * Configurations are managed with this class in Robotest.
 *
 * @param <T>
 *            generic type to be cast
 */
public class ConfigurationEntry<T> {

    private T value;

    /**
     * Getter method for value.
     *
     * @return the value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Setter method for the value.
     *
     * @param value
     *            the value to set
     */
    public void setValue(final T value) {
        this.value = value;
    }

}
