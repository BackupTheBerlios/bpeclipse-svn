package org.bpeclipse.api.messages;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.bpeclipse.api.BPException;
import org.bpeclipse.api.bpobjects.IBPObject;
import org.bpeclipse.api.config.BPConfigMgr;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author achoi
 *
 */
public abstract class AbstractBPMessage implements IBackpackMessage {
    
    protected static Logger logger = Logger.getLogger(AbstractBPMessage.class);

    public void sendRequest() throws BPException {
        
        String tokenStr = BPConfigMgr.getInstance().getToken();
        String userName = BPConfigMgr.getInstance().getUserName();
        
        if (tokenStr == null || userName == null || 
                "".equals(tokenStr.trim()) || "".equals(userName.trim())) {
            throw new BPException("User name and password cannot be null");
        }

        Document req = new Document();
        
        Element root = new Element("request");
        Element token = new Element("token");

        token.setText(tokenStr);
        
        root.addContent(token);
        
        createRequestBody(root);
        
        req.addContent(root);
        
        String reqXML = new XMLOutputter(Format.getPrettyFormat()).outputString(req);
        
        HttpClient client = new HttpClient();
        
        String requestURL = "http://" + userName + ".backpackit.com/ws";
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
                String errMsg = "HTTP request failed with status " + post.getStatusCode() + ": " + post.getStatusText();
                logger.error(errMsg);
                throw new BPException(errMsg);
            }
            
            InputStream is = post.getResponseBodyAsStream();
            response = new SAXBuilder().build(is);
            is.close();
            
        } catch (IOException e) {
            throw new BPException("Failed to send request: ", e);
        } catch (JDOMException e) {
            throw new BPException("Failed to send request: ", e);
        } finally {
            post.releaseConnection();
        }
        
        parseResponse(response);
    }

    protected void parseResponse(Document response) throws BPException {
        
        logger.debug("response:\n" + new XMLOutputter(Format.getPrettyFormat()).outputString(response));
        
        Element root = response.getRootElement();
        
        if (!root.getName().equals("response")) {
            String errMsg = "Response document root is not <response>: " + root.getName();
            logger.error(errMsg);
            throw new BPException(errMsg);
        }
        
        String successStr = root.getAttributeValue("success");
        boolean success = Boolean.valueOf(successStr).booleanValue();
        
        if (!success) {
            String errMsg = "Request was not successful";
            logger.error(errMsg);
            throw new BPException(errMsg);
        }
        
        // we expect only one child
        Element child = (Element)root.getChildren().get(0);
        
        parseObject(child);
    }
    
    protected abstract void parseObject(Element root) throws BPException;

    protected abstract String getRequestURL();

    protected abstract void createRequestBody(Element root);

    public abstract IBPObject getResponseObject();

}
