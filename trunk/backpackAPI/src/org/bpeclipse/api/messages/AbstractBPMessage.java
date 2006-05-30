package org.bpeclipse.api.messages;

import java.io.InputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.bpeclipse.api.bpobjects.IBPObject;
import org.bpeclipse.api.config.BPConfigMgr;
import org.bpeclipse.api.config.IBPConfig;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author achoi
 *
 */
public abstract class AbstractBPMessage implements IBackpackMessage {
    
    protected static Logger logger = Logger.getLogger(IBPConfig.BP_LOGGER);

    public boolean sendRequest() {
        
        Document req = new Document();
        
        Element root = new Element("request");
        Element token = new Element("token");
        token.setText(BPConfigMgr.getInstance().getToken());
        
        root.addContent(token);
        
        createRequestBody(root);
        
        req.addContent(root);
        
        String reqXML = new XMLOutputter(Format.getPrettyFormat()).outputString(req);
        
        HttpClient client = new HttpClient();
        
        String requestURL = "http://" + BPConfigMgr.getInstance().getUserName() + ".backpackit.com/ws";
        requestURL += getRequestURL();
            
        logger.debug("request to " + requestURL + ":\n " + reqXML);
        
        PostMethod post = new PostMethod(requestURL);
        post.addRequestHeader(new Header("X-POST_DATA_FORMAT", "xml"));
        
        RequestEntity body = new StringRequestEntity(reqXML);
        post.setRequestEntity(body);
        
        Document response = null;
        try {
            client.executeMethod(post);
            
            if (post.getStatusCode() != HttpStatus.SC_OK) {
                logger.error("HTTP request failed with status " + post.getStatusCode() + ": " + post.getStatusText());
                return false;
            }
            
            InputStream is = post.getResponseBodyAsStream();
            response = new SAXBuilder().build(is);
            is.close();

        } catch (Exception e) {
            logger.error("Exception when getting or processing response: ", e);
            return false;
        } finally {
            post.releaseConnection();
        }
        
        return parseResponse(response);
    }

    protected boolean parseResponse(Document response) {
        
        logger.debug("response:\n" + new XMLOutputter(Format.getPrettyFormat()).outputString(response));
        
        Element root = response.getRootElement();
        
        if (!root.getName().equals("response")) {
            logger.error("Response document root is not <response>: " + root.getName());
            return false;
        }
        
        String successStr = root.getAttributeValue("success");
        boolean success = Boolean.valueOf(successStr).booleanValue();
        
        if (!success) {
            logger.error("Request was not successful");
            return false;
        }
        
        // we expect only one child
        Element child = (Element)root.getChildren().get(0);
        
        return parseObject(child);
    }
    
    protected abstract boolean parseObject(Element root);

    protected abstract String getRequestURL();

    protected abstract void createRequestBody(Element root);

    public abstract IBPObject getResponseObject();

}
