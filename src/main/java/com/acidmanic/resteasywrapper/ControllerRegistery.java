/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.resteasywrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author diego
 */
public class ControllerRegistery extends Application {

    private static final Set<Object> CONTROLLERS = new HashSet<>();

    static void setup(ControllersProvider provider){
        CONTROLLERS.clear();
        
        List<Object> controllers = provider.provideAllControllerInstances();
        
        CONTROLLERS.addAll(controllers);
    }
    
    @Override
    public Set<Object> getSingletons() {
        return CONTROLLERS;
    }

}
