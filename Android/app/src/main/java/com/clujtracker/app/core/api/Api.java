package com.clujtracker.app.core.api;

/**
 * Created by Alex on 3/14/14.
 */
public class Api implements Api_I {

    private static Api instance;


    public static Api getInstance() {
        if (instance == null)
            instance = new Api();
        return instance;
    }

    private Api() {
    }

    /*
     ****************************************
      * *********** Methods ***************
       * ********************************
     */

    @Override
    public void getCurrentPositions() {

    }
}
