package org.mycompany;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadService implements Processor {

    private Logger log = LoggerFactory.getLogger(DownloadService.class);

    public void process(Exchange exchange) throws Exception {

        Message in = exchange.getIn();

        String relativepath = in.getHeader(Exchange.HTTP_PATH, String.class);
        String requestPath = in.getHeader("CamelServletContextPath", String.class); // CamelServletContextPath

        if (relativepath.isEmpty() || relativepath.equals("/")) {
            relativepath = "index.html";
        }

        final String formattedPath = String.format("%s/data%s%s", System.getProperty("user.dir"), requestPath,
                relativepath);
        log.info("download: " + formattedPath);
        Path path = Paths.get(formattedPath);
        Message out = exchange.getOut();
        if (!Files.exists(path)) {
            out.setBody(relativepath + " not found.");
            out.setHeader(Exchange.HTTP_RESPONSE_CODE, "404");
            return;
        }
        InputStream pathStream = Files.newInputStream(path);
        // InputStream pathStream = this.getClass().getResourceAsStream(formattedPath);
        // Path path =
        // FileSystems.getDefault().getPath(this.getClass().getResource(formattedPath).getPath());
        log.info("download file path:" + path.toString());

        try {
            out.setBody(IOUtils.toByteArray(pathStream));
            out.setHeader(Exchange.CONTENT_TYPE, Files.probeContentType(path));
        } catch (IOException e) {
            out.setBody(relativepath + " not found.");
            out.setHeader(Exchange.HTTP_RESPONSE_CODE, "404");
        }

    }
}