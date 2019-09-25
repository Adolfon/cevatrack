package com.ceva.cevatrack.presentation;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import com.ceva.cjf.base.web.jsf.CJFBaseBean;

/**
 * HomeBean.
 * 
 * @author mohammad.rasheed
 *
 */
@Named
@SessionScoped
public class HomeBean extends CJFBaseBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
