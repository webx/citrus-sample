package com.alibaba.sample.petstore.web.home.module.screen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;

/**
 * 外部重定向，用来示范redirect token的使用方法。
 * 
 * @author Michael Zhou
 */
public class Redirect {
    private final static Logger log = LoggerFactory.getLogger(Redirect.class);

    public void execute(@Param("location") String location, Navigator nav) {
        if (location != null) {
            log.info("Redirect to location which is not in whitelist: {}", location);
            nav.redirectToLocation(location);
        } else {
            nav.forwardTo("homepage");
        }
    }
}
