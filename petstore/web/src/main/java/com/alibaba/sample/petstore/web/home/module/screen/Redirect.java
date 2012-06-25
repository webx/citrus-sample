/*
 * Copyright (c) 2002-2012 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.sample.petstore.web.home.module.screen;

import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
