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

import static com.alibaba.citrus.util.StringEscapeUtil.*;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.service.requestcontext.buffered.BufferedRequestContext;
import com.alibaba.citrus.turbine.dataresolver.Param;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 在浏览器上显示计数。
 * <p/>
 * 多数动态WEB页面是这样的：先在内存中生成整个页面，然后一次性提交给浏览器。这样，在页面完全生成之前，用户必须等待。
 * <p/>
 * 此程序展示了一种技术，能让页面一边生成、一边展示给用户。虽然页面还没有完全生成，但用户已经能够看到部分页面。
 * 这不仅改善了用户的体验，也使浏览器处理页面、下载、服务器生成页面三者实现了并发，从而加快了显示页面的总时间。
 *
 * @author Michael Zhou
 */
public class Count {
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private BufferedRequestContext brc;

    public void execute(@Param("to") int toNumber) throws Exception {
        // 必须关闭buffering，未完成的页面才会被显示在浏览器上。
        brc.setBuffering(false);

        // 设置content type，但不需要设置charset，框架会设置正确的charset。
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("  <title>Count to " + toNumber + "</title>");
        out.println("</head>");
        out.println("<body>");

        for (int i = 1; i <= toNumber; i++) {
            for (int j = 0; j < 10000; j++) {
                out.print(i);
            }

            out.println();
            out.flush(); // 将当前的结果立即显示到浏览器上

            Thread.sleep(1000); // 特意等待1秒，仅用于演示。
        }

        out.println("</body>");
        out.println("</html>");
    }
}
