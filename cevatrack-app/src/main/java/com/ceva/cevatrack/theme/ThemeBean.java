/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ceva.cevatrack.theme;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;

/**
 * ThemeBean.
 * 
 * @author mohammad.weshah
 *
 */
@Named(value = "themeBean")
@ApplicationScoped
public class ThemeBean {

    private List<SelectItem> themes;

    /**
     * init.
     */
    @PostConstruct
    public void init() {
        themes = new ArrayList<SelectItem>();
        themes.add(new SelectItem("cjf", "CJF"));
        createThemeGroups();
    }

    private void addThemesToGroup(SelectItemGroup group, String themeName) {
        group.setSelectItems(new SelectItem[] { new SelectItem(themeName, group.getLabel() + " - Colored"),
                new SelectItem(themeName + "-light", group.getLabel() + " - Light"),
                new SelectItem(themeName + "-dark", group.getLabel() + " - Dark") });
        themes.add(group);
    }

    private void createThemeGroups() {
        addThemesToGroup(new SelectItemGroup("Absolution"), "absolution");
        addThemesToGroup(new SelectItemGroup("Rebirth"), "rebirth");
        addThemesToGroup(new SelectItemGroup("Hope"), "hope");
        addThemesToGroup(new SelectItemGroup("Bliss"), "bliss");
        addThemesToGroup(new SelectItemGroup("Grace"), "grace");
        addThemesToGroup(new SelectItemGroup("Dusk"), "dusk");
        addThemesToGroup(new SelectItemGroup("Navy"), "navy");
        addThemesToGroup(new SelectItemGroup("Infinity"), "infinity");
        addThemesToGroup(new SelectItemGroup("Fate"), "fate");
        addThemesToGroup(new SelectItemGroup("Ruby"), "ruby");
        addThemesToGroup(new SelectItemGroup("Comfort"), "comfort");
        addThemesToGroup(new SelectItemGroup("Faith"), "faith");
        addThemesToGroup(new SelectItemGroup("Violet"), "violet");
        addThemesToGroup(new SelectItemGroup("Honor"), "honor");
        addThemesToGroup(new SelectItemGroup("Rebel"), "rebel");
        addThemesToGroup(new SelectItemGroup("Vanity"), "vanity");
        addThemesToGroup(new SelectItemGroup("Valor"), "valor");
        addThemesToGroup(new SelectItemGroup("Merit"), "merit");
        addThemesToGroup(new SelectItemGroup("Esprit"), "esprit");
        addThemesToGroup(new SelectItemGroup("Concord"), "concord");
        addThemesToGroup(new SelectItemGroup("Dulce"), "dulce");
        addThemesToGroup(new SelectItemGroup("Royal"), "royal");
        addThemesToGroup(new SelectItemGroup("Hazel"), "hazel");
        addThemesToGroup(new SelectItemGroup("Essence"), "essence");
        addThemesToGroup(new SelectItemGroup("Eternity"), "eternity");
        addThemesToGroup(new SelectItemGroup("Clarity"), "clarity");
        addThemesToGroup(new SelectItemGroup("Solace"), "solace");
        addThemesToGroup(new SelectItemGroup("Joy"), "joy");
        addThemesToGroup(new SelectItemGroup("Purity"), "purity");
        addThemesToGroup(new SelectItemGroup("Euclid"), "euclid");
        addThemesToGroup(new SelectItemGroup("Elegance"), "elegance");
        addThemesToGroup(new SelectItemGroup("Tranquil"), "tranquil");
    }

    public List<SelectItem> getThemes() {
        return themes;
    }

}
