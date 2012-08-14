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

package ${package}.app1.module.screen.simple;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 这是最简单的页面：不需要模板，直接输出到浏览器，就像最简单的servlet一样。
 *
 * @author Michael Zhou
 */
public class SayHi {
    @Autowired
    private HttpServletResponse response;

    public void execute() throws Exception {
        // 设置content type，但不需要设置charset。框架会设置正确的charset。
        response.setContentType("text/plain");

        // 如同servlet一样：取得输出流。
        PrintWriter out = response.getWriter();

        out.println("Hi there, how are you doing today?");
    }
}
