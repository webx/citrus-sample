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

package ${package}.app1.module.screen.multievent;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Screen方法可带有返回值。
 * <p/>
 * 这个screen所返回的对象将被转换成json格式，并输出到用户浏览器。
 *
 * @author Michael Zhou
 */
public class SayHello2 {
    /** 英文 */
    public Hello doEnglish() {
        return new Hello("English", "Hello");
    }

    /** 中文 */
    public Hello doChinese() {
        return new Hello("Chinese", "你好");
    }

    /** 法语 */
    public Hello doFrench() {
        return new Hello("French", "Bonjour");
    }

    /** 西班牙语 */
    public Hello doSpanish() {
        return new Hello("Spanish", "Hola");
    }

    public static class Hello {
        private String language;
        private String howToSay;

        public Hello(String language, String howToSay) {
            this.language = language;
            this.howToSay = howToSay;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getHowToSay() {
            return howToSay;
        }

        public void setHowToSay(String howToSay) {
            this.howToSay = howToSay;
        }
    }
}

