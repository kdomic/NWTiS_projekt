/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.kdomic.beans.general;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@SessionScoped
public class Localization {

    private static Map<String, Object> supportedLanguage;

    private String selectedLanguage;
    private Locale currentLanguage;
    
    static{
        supportedLanguage = new LinkedHashMap<>();
        supportedLanguage.put("Hrvatski", new Locale("hr"));
        supportedLanguage.put("English", Locale.ENGLISH);
    }
    
    public Localization() {
        currentLanguage = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        selectedLanguage = currentLanguage.getLanguage();
    }
    
    public String selectLanguage(){
        for(String key : supportedLanguage.keySet()){
            Locale l = (Locale) supportedLanguage.get(key);
            if(selectedLanguage.compareTo(l.getLanguage())==0){
                FacesContext.getCurrentInstance().getViewRoot().setLocale(l);
                currentLanguage = l;
                break;
            }
        }
        return null;
    }

    public Map<String, Object> getSupportedLanguage() {
        return supportedLanguage;
    }

    public void setSupportedLanguage(Map<String, Object> supportedLanguage) {
        Localization.supportedLanguage = supportedLanguage;
    }
    

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public Locale getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(Locale currentLanguage) {
        this.currentLanguage = currentLanguage;
    }
    
    
}
