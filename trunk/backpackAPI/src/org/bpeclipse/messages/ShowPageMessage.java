package org.bpeclipse.messages;


import java.util.Map;

import org.bpeclipse.bpobjects.BPPage;
import org.bpeclipse.bpobjects.IBPObject;
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

    protected boolean parseObject(Element root) {
        
        if (!root.getName().equals("page")) {
            logger.error("Wrong type of response for ShowPageMessage: " + root.getName());
        }
        
        // TODO: use a factory?
        page = new BPPage();
        try {
            page.loadFromXml(root);
        } catch (Exception e) {
            logger.error("Error parsing page: ", e);
            return false;
        }
        
        return true;
    }

}
