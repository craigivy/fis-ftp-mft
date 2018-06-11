package org.mycompany;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class UploaderService implements Processor {

    public void process(Exchange exchange) throws Exception {
        String body = "<!DOCTYPE html><html><body><form method=\"post\" enctype=\"multipart/form-data\" action=\"/upload\">Select file:<input type=\"file\" name=\"fileUploaded\" id=\"fileToUpload\"><input type=\"submit\" value=\"Upload\" name=\"submit\"></form></body></html>";
        exchange.getOut().setBody(body);
    }
}