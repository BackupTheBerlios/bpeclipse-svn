package org.bpeclipse.api.messages;


import java.util.Map;

import org.bpeclipse.api.BPException;
import org.bpeclipse.api.bpobjects.BPPage;
import org.bpeclipse.api.bpobjects.IBPObject;
import org.jdom.Element;

public class ShowPageMessage extends AbstractBPMessage {
    
    private String pageID;
    
    private IBPObject page;

    public ShowPageMessage(Map params) {
        pageID = (String)params.get(IBackpackMessage.PAGE_ID);
    }

    protected String getRequestURL() {
        
        return "/page/" + pageID;
    }

    protected void createRequestBody(Element root) {
        // do nothing
        return;
    }

    public IBPObject getResponseObject() {
        // will return one page object
        return page;
    }

    protected void parseObject(Element root) throws BPException {
        
        if (!root.getName().equals("page")) {
            logger.error("Wrong type of response for ShowPageMessage: " + root.getName());
        }
        
        // TODO: use a factory?
        page = new BPPage();
        try {
            page.loadFromXml(root);
        } catch (Exception e) {
            String errMsg = "Error parsing page: ";
            logger.error(errMsg, e);
            throw new BPException(errMsg, e);
        }
        
    }

}
