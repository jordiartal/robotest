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
package com.castinfo.devops.robotest.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.castinfo.devops.robotest.RobotestException;
import com.castinfo.devops.robotest.StepStatus;
import com.fasterxml.jackson.core.JsonGenerator;

/**
 * Validation info under steps.
 */
public class ValidationEntry {

    private static final int MAX_STACK_TRACE_CAUSE_LIMIT = 5;
    private int validationOrder;
    private StepStatus status;
    private ValidationType type;
    private List<String> resource;

    /**
     * Constructor.
     *
     * @param status
     *            status level
     */
    public ValidationEntry(final StepStatus status) {
        this.status = status;
        this.resource = new ArrayList<>();
    }

    /**
     * Setter method for the type.
     *
     * @param type
     *            the type to set
     */
    public void setType(final ValidationType type) {
        this.type = type;
    }

    /**
     * Setter method for the resource.
     *
     * @param resource
     *            the resource to set
     */
    public void setResource(final List<String> resource) {
        this.resource = resource;
    }

    /**
     * Getter method for status.
     *
     * @return the status
     */
    public StepStatus getStatus() {
        return this.status;
    }

    /**
     * Setter method for the status.
     *
     * @param status
     *            the status to set
     */
    public void setStatus(final StepStatus status) {
        this.status = status;
    }

    /**
     * Getter method for resource.
     *
     * @return the resource
     */
    public List<String> getResource() {
        return this.resource;
    }

    /**
     * Getter method for validationOrder.
     *
     * @return the validationOrder
     */
    public int getValidationOrder() {
        return this.validationOrder;
    }

    /**
     * Setter method for the validationOrder.
     *
     * @param validationOrder
     *            the validationOrder to set
     */
    public void setValidationOrder(final int validationOrder) {
        this.validationOrder = validationOrder;
    }

    /**
     * Getter method for type.
     *
     * @return the type
     */
    public ValidationType getType() {
        return this.type;
    }

    /**
     * Sets CONSOLE in fluent api mode.
     *
     * @param vt
     *            Type of validation
     * @return the fluent api object
     */
    public ValidationEntry withType(final ValidationType vt) {
        this.type = vt;
        return this;
    }

    /**
     * Debug and message fluent api builder.
     *
     * @return the fluent api object
     */
    public static ValidationEntry buildDebug() {
        return new ValidationEntry(StepStatus.DEBUG);
    }

    /**
     * Info and message fluent api builder.
     *
     * @return the fluent api object
     */
    public static ValidationEntry buildInfo() {
        return new ValidationEntry(StepStatus.INFO);
    }

    /**
     * Warning and message fluent api builder.
     *
     * @return the fluent api object
     */
    public static ValidationEntry buildWarning() {
        return new ValidationEntry(StepStatus.WARNING);
    }

    /**
     * Error and message fluent api builder.
     *
     * @return the fluent api object
     */
    public static ValidationEntry buildError() {
        return new ValidationEntry(StepStatus.ERROR);
    }

    /**
     * Defect and message fluent api builder.
     *
     * @return the fluent api object
     */
    public static ValidationEntry buildDefect() {
        return new ValidationEntry(StepStatus.DEFECT);
    }

    /**
     * Critical and message fluent api builder.
     *
     * @return the fluent api object
     */
    public static ValidationEntry buildCritical() {
        return new ValidationEntry(StepStatus.CRITICAL);
    }

    /**
     * Set capture image in fluent api builder. If set, only one resource be set and previous resources lost.
     *
     * @param cptr
     *            capture
     * @return the fluent api object
     * @throws RobotestException
     *             If previous other type of resource is set.
     */
    public ValidationEntry withCapture(final File cptr) throws RobotestException {
        if (null != this.type && !ValidationType.SCREENSHOT.equals(this.type)) {
            throw new RobotestException("PREVIOUS TYPE VALIDATION WAS FOUND SETTING CAPTURE RESORCE: " + this.type);
        }
        this.resource.clear();
        this.resource.add(cptr.getName());
        this.type = ValidationType.SCREENSHOT;
        return this;
    }

    /**
     * Set source HTML in fluent api builder. If set, only one resource be set and previous resources lost.
     *
     * @param htmlSrc
     *            source.
     * @return the fluent api object
     * @throws RobotestException
     *             If previous other type of resource is set.
     */
    public ValidationEntry withHtmlSource(final File htmlSrc) throws RobotestException {
        if (null != this.type && !ValidationType.HTML.equals(this.type)) {
            throw new RobotestException("PREVIOUS TYPE VALIDATION WAS FOUND SETTING HTML RESOURCE: " + this.type);
        }
        this.resource.clear();
        this.resource.add(htmlSrc.getName());
        this.type = ValidationType.HTML;
        return this;
    }

    /**
     * Set TEXT message in fluent api builder.
     *
     * @param msg
     *            message.
     * @return the fluent api object
     * @throws RobotestException
     *             If previous other type of resource is set.
     */
    public ValidationEntry withMessage(final String msg) throws RobotestException {
        if (null != this.type && !ValidationType.TEXT.equals(this.type)) {
            throw new RobotestException("PREVIOUS TYPE VALIDATION WAS FOUND SETTING TEXT RESOURCE: " + this.type);
        }
        this.resource.add(msg);
        this.type = ValidationType.TEXT;
        return this;
    }

    /**
     * Adapt Throawble to TEXT message in fluent api builder.
     *
     * @param e
     *            Throwable.
     * @return the fluent api object
     * @throws RobotestException
     *             If previous other type of resource is set.
     */
    public ValidationEntry withException(final Throwable e) throws RobotestException {
        if (null == e) {
            this.withMessage("NO STACKTRACE PROVIDED");
        }
        List<Throwable> exList = ExceptionUtils.getThrowableList(e);
        if (null == exList || exList.isEmpty()) {
            this.withMessage("NO STACKTRACE PROVIDED");
        } else {
            for (Throwable ex : exList) {
                this.withMessage(ValidationEntry.throwableToString(ex));
            }
        }
        return this;

    }

    /**
     * Utlity to report exception trace.
     *
     * @param ex
     *            something trhowable
     * @return Text to show
     */
    public static String throwableToString(final Throwable ex) {
        StringBuilder errorMsg = new StringBuilder();
        StackTraceElement st = null;
        if (null == ex.getStackTrace() || ex.getStackTrace().length == 0) {
            st = new Exception().getStackTrace()[1];
        } else {
            st = ex.getStackTrace()[0];
        }
        errorMsg.append(ex.getClass().getName());
        if (null == ex.getMessage()) {
            errorMsg.append(" No message: ");
            errorMsg.append(ex.getClass().getName());
        } else {
            errorMsg.append(" with message: ");
            errorMsg.append(ex.getMessage());
        }
        errorMsg.append(" in ");
        errorMsg.append(st.getClassName());
        errorMsg.append(".");
        errorMsg.append(st.getMethodName());
        if (st.getLineNumber() > 0) {
            errorMsg.append(" (line: ");
            errorMsg.append(st.getLineNumber());
            errorMsg.append(")");
        }
        Throwable cause = ex.getCause();
        int causeLimit = MAX_STACK_TRACE_CAUSE_LIMIT;
        while (null != cause && causeLimit > 0) {
            if (null != cause.getMessage()) {
                errorMsg.append(" with message: ");
                errorMsg.append(cause.getMessage());
            }
            errorMsg.append(" (");
            errorMsg.append(cause.getClass().getName());
            errorMsg.append(")");
            cause = cause.getCause();
            causeLimit--;
        }
        return errorMsg.toString();
    }

    /**
     * Set CONSOLE log in fluent api builder.
     *
     * @param log
     *            CONSOLE log.
     * @return the fluent api object
     * @throws RobotestException
     *             If previous other type of resource is set.
     */
    public ValidationEntry withConsole(final String log) throws RobotestException {
        if (null != this.type && !ValidationType.CONSOLE.equals(this.type)) {
            throw new RobotestException("PREVIOUS TYPE OF VALIDATION: " + this.type
                    + " WAS FOUND SETTING CONSOLE RESOURCE");
        }
        this.resource.add(log);
        this.type = ValidationType.CONSOLE;
        return this;
    }

    /**
     * Write validation entry JSON generator writer.
     *
     * @param jGenerator
     *            writer.
     * @throws RobotestException
     *             Errors, probably IO.
     */
    public void writeValidation(final JsonGenerator jGenerator) throws RobotestException {
        try {
            jGenerator.writeStartObject();
            jGenerator.writeNumberField("order", this.getValidationOrder());
            jGenerator.writeStringField("status", this.getStatus().name());
            jGenerator.writeStringField("type", this.getType().name());
            if (null != this.getResource()) {
                jGenerator.writeArrayFieldStart("resource");
                for (String res : this.resource) {
                    jGenerator.writeObject(res);
                }
                jGenerator.writeEndArray();
            }
            jGenerator.writeEndObject();
        } catch (IOException e) {
            throw new RobotestException("ROBOTEST VALIDATION REPORTER ERROR", e);
        }
    }

}
