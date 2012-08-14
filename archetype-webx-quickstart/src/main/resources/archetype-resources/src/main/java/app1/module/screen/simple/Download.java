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

import static com.alibaba.citrus.util.ObjectUtil.*;
import static com.alibaba.citrus.util.StringEscapeUtil.*;
import static org.apache.commons.lang.StringUtils.*;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.service.requestcontext.buffered.BufferedRequestContext;
import com.alibaba.citrus.turbine.dataresolver.Param;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 动态生成下载文件。
 *
 * @author Michael Zhou
 */
public class Download {
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private BufferedRequestContext brc;

    public void execute(@Param("filename") String filename) throws Exception {
        // 为了增强用户体验，关闭buffering，让下载立即开始，而不是等待整个文件生成完才通知用户下载。
        brc.setBuffering(false);

        // 设置headers，下载文件名必须避免非us-ascii字符，因为各浏览器的兼容程度不同。
        filename = defaultIfNull(trimToNull(filename), "image") + ".txt";
        filename = "\"" + escapeURL(filename) + "\"";

        response.setHeader("Content-disposition", "attachment; filename=" + filename);

        // 只要设置了正确的content type，你就可以让用户下载任何文本或二进制的内容：
        // HTML、JSON、JavaScript、JPG、PDF、EXCEL等。
        response.setContentType("text/plain");

        PrintWriter out = response.getWriter();

        for (int i = 0; i < 100; i++) {
            out.flush(); // 立即提示用户下载

            for (int j = 0; j < 100000; j++) {
                out.print(i);
            }

            out.println();

            Thread.sleep(100); // 故意延迟，以减缓下载速度
        }
    }
}
