package com.ttknpdev.springbootsecuritywithdbjdbc.log;

import com.ttknpdev.springbootsecuritywithdbjdbc.configuration.ConfigSecure;
import com.ttknpdev.springbootsecuritywithdbjdbc.controller.ControlPath;
import org.apache.log4j.Logger;

public interface Mylog {
    Logger configSecure = Logger.getLogger(ConfigSecure.class);
    Logger controlPath = Logger.getLogger(ControlPath.class);
}
