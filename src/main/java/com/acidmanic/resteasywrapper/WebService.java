/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.resteasywrapper;

import com.acidmanic.lightweight.logger.Logger;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author diego
 */
public class WebService {

    private Server server;
    private final int port;
    private final File webDirectory;
    private final File descriptorFile;

    private final Logger logger;

    public WebService(Logger logger, int port, File webDirectory, ControllersProvider controllersProvider) {

        this.port = port;

        this.logger = logger;

        this.webDirectory = webDirectory;

        this.descriptorFile = this.webDirectory.toPath()
                .resolve("web.xml").toFile();

        ControllerRegistery.setup(controllersProvider);
    }

    private void buildUpWebDirectory() {

        this.teardownServingDirectory();

        this.webDirectory.mkdirs();

        String webXmlContent = new InMemoryResource().webXmlTemplate();

        webXmlContent = webXmlContent.replaceAll(
                InMemoryResource.APPLICATION_CLASS_TAG,
                ControllerRegistery.class.getName());

        try {
            if (descriptorFile.exists()) {
                descriptorFile.delete();
            }
            Files.write(descriptorFile.toPath(), webXmlContent.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (Exception e) {
            this.logger.error("Problem exporting web.xml: " + e.getClass().getSimpleName());
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private void teardownServingDirectory() {
        if (this.webDirectory.exists()) {
            try {
                if (this.descriptorFile.exists()) {
                    this.descriptorFile.delete();
                }
            } catch (Exception e) {
            }
        }
    }

    public void syncStart() {
        this.server = new Server(this.port);

        buildUpWebDirectory();

        WebAppContext context = new WebAppContext();

        context.setDescriptor(this.descriptorFile.toPath().toString());
        //TODO: webapp directory can contain webapp resources
        context.setResourceBase(this.webDirectory.getAbsolutePath());

        context.setContextPath("/");

        context.setParentLoaderPriority(true);

        //context.addServlet(new ServletHolder(new ServletContainer), "/*");
        server.setHandler(context);

        try {
            server.start();

            server.join();

        } catch (Exception e) {
            this.logger.warning("Problem starting jetty server."
                    + e.getClass().getSimpleName());
            this.logger.warning(e.getMessage());
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (InterruptedException e) {

        } catch (Exception e) {
            this.logger.error("Problem stopping jetty server."
                    + e.getClass().getSimpleName());
            this.logger.error(e.getMessage());
        }
        teardownServingDirectory();
    }

}
