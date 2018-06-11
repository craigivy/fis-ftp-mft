package org.mycompany;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadService implements Processor {

    private Logger log = LoggerFactory.getLogger(UploadService.class);

    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        Set<String> aNames = in.getAttachmentNames();
        log.info(String.format("attachments: %s body: %s", aNames, in.getBody()));

        for (String aName : aNames) {
            DataHandler data = in.getAttachment(aName);
            if (data.getContentType() != null) {

                // log.info(String.format("atachment name: %s, type: %s", data.getName(), data.getContentType()));
                // log.info("ds name: " + data.getDataSource().getClass().getCanonicalName());
                // log.info("ds name2: " + data.getDataSource().getName());

                // for (String header : in.getHeaders().keySet()) {
                //     log.info("in:" + header);
                // }
                // for (String header : exchange.getProperties().keySet()) {
                //     log.info("ex: " + header);
                // }
                // log.info("in!:" + in.getHeader(aName).toString() );
                // log.info("in!!:" + in.getHeader(aName + ".org.eclipse.jetty.servlet.contentType").toString() );
                
                InputStream is = data.getInputStream();
                final String formattedPath = String.format("%s/data/upload/%s-%s", System.getProperty("user.dir"),
                        System.currentTimeMillis(), aName);

                OutputStream os = new FileOutputStream(new File(formattedPath));

                // This will copy the file from the two streams
                IOUtils.copy(is, os);

                // This will close two streams catching exception
                IOUtils.closeQuietly(os);
                IOUtils.closeQuietly(is);
            }

        }

        exchange.getOut().setBody("<html><body>Upload Complete</body></html>");
    }
}