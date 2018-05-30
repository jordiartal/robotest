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
package com.castinfo.devops.robotest;

/**
 * Enumeration of posible status results of robotest execution validations.
 */
public enum StepStatus {

    /**
     * For debugging purposes.
     */
    DEBUG,
    /**
     * The general info validation status.
     */
    INFO,
    /**
     * When validations detect unknown problem, but test can continue.
     */
    WARNING,
    /**
     * When validations detect a knownledge base problem, but test can continue.
     */
    DEFECT,
    /**
     * When validations detect a problem, but test can't continue and stop test.
     */
    ERROR,
    /**
     * When Robotest can't do normal operations, or your validation error detected is more important than usual errors.
     */
    CRITICAL;

}
