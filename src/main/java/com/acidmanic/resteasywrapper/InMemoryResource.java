/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.resteasywrapper;

/**
 *
 * @author diego
 */
public class InMemoryResource {
    
    public static String APPLICATION_CLASS_TAG ="APPLICATION_CLASS_TAG";

    public String webXmlTemplate() {
        return "<web-app xmlns:javaee=\"http://java.sun.com/xml/ns/javaee\" xmlns:web=\"http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\">\n"
                + "    <context-param>\n"
                + "        <param-name>resteasy.scan</param-name>\n"
                + "        <param-value>true</param-value>     \n"
                + "    </context-param>\n"
                + "\n"
                + "    <context-param>\n"
                + "        <param-name>javax.ws.rs.Application</param-name>\n"
                + "        <param-value>APPLICATION_CLASS_TAG</param-value>\n"
                + "    </context-param>\n"
                + "   \n"
                + "    <context-param>\n"
                + "        <param-name>resteasy.resource.method-interceptors</param-name>\n"
                + "        <param-value>\n"
                + "            org.jboss.resteasy.core.ResourceMethodSecurityInterceptor\n"
                + "        </param-value>\n"
                + "    </context-param>\n"
                + "\n"
                + "    <listener>\n"
                + "        <listener-class>org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap</listener-class>\n"
                + "    </listener>\n"
                + "\n"
                + "    <servlet>     \n"
                + "        <servlet-name>Resteasy</servlet-name>\n"
                + "        <servlet-class>org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher</servlet-class>\n"
                + "        <load-on-startup>1</load-on-startup>\n"
                + "    </servlet>\n"
                + "\n"
                + "    <servlet-mapping>\n"
                + "        <servlet-name>Resteasy</servlet-name>\n"
                + "        <url-pattern>/*</url-pattern>\n"
                + "    </servlet-mapping>\n"
                + "</web-app>";
    }
}
