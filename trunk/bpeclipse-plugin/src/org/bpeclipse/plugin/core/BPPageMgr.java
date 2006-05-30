/**
 * 
 */
package org.bpeclipse.plugin.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bpeclipse.api.BPException;
import org.bpeclipse.api.bpobjects.BPPage;
import org.bpeclipse.api.bpobjects.BPPageList;
import org.bpeclipse.api.config.IBPConfig;
import org.bpeclipse.api.messages.IBackpackMessage;
import org.bpeclipse.api.messages.ListAllPagesMessage;
import org.bpeclipse.api.messages.ShowPageMessage;
import org.bpeclipse.plugin.BPPluginException;

/**
 * @author achoi
 *
 */
public class BPPageMgr {
    
    private static BPPageMgr instance;
    
    private static Logger logger = Logger.getLogger(IBPConfig.BP_LOGGER);
    
    private Map pages;
    
    public static BPPageMgr getInstance() {
        if (instance == null) {
            instance = new BPPageMgr();
        }
        
        return instance;
    }
    
    protected BPPageMgr() {
    }
    
    public void initialize() throws BPPluginException {
        pages = new HashMap();
        
        // request the listing of all pages, and store them
        try {
            ListAllPagesMessage pageReq = new ListAllPagesMessage();
            
            pageReq.sendRequest();
            
            BPPageList pageList = (BPPageList)pageReq.getResponseObject();
            
            List ids = pageList.getPageIDList();
            
            // for each page, send a request for the page
            for (Iterator it = ids.iterator(); it.hasNext(); ) {
                String id = (String)it.next();
                
                logger.debug("Page ID: " + id);
                
                Map param = new HashMap();
                param.put(IBackpackMessage.PAGE_ID, id);
                ShowPageMessage showPageMsg = new ShowPageMessage(param);
                
                showPageMsg.sendRequest();
                
                pages.put(id, showPageMsg.getResponseObject());
            }
            
        } catch (BPException e) {
            
            throw new BPPluginException("Failed to initialize", e);
        }
    }
    
    public BPPage getPageByID(String id) {
        return (BPPage)pages.get(id);
    }

}
