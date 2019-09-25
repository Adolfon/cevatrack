package com.ceva.cevatrack.util;

/**
 * CJFPermissionConstants.
 * 
 * @author mohammad.rasheed
 *
 */
public class CJFPermissionConstants {
    public static final String TODO_VIEW = "todo:view";
    public static final String TODO_EDIT = "todo:edit";
    public static final String TODO_CREATE = "todo:create";
    public static final String TODO_APPROVE = "todo:approve";
    public static final String TODO_DELETE = "todo:delete";
    public static final String TODO = "todo";

    /** Administration Model. **/
    public static final String ADMIN_MANAGE_HEAP = "cjf-admin:manage:heap";
    public static final String ADMIN_MANAGE_SERVING = "cjf-admin:manage:serving";
    public static final String ADMIN_VIEW_LOG = "cjf-admin:view:log";
    public static final String ADMIN_MANAGE_LOG_LEVELS = "cjf-admin:manage:log-config";

    // Utility classes should not have public constructors.
    private CJFPermissionConstants() {

    }
}
