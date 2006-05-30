/**
 * 
 */
package org.bpeclipse.plugin.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bpeclipse.api.bpobjects.BPPage;
import org.bpeclipse.api.bpobjects.BPPageList;
import org.bpeclipse.api.config.IBPConfig;
import org.bpeclipse.api.messages.IBackpackMessage;
import org.bpeclipse.api.messages.ListAllPagesMessage;
import org.bpeclipse.api.messages.ShowPageMessage;

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
            
            try {
                instance = new BPPageMgr();
            } catch (Exception e) {
                logger.error("Failed to initialize BPPageMgr: ", e);
            }
        }
        
        return instance;
    }
    
    protected BPPageMgr() throws Exception {
        
        pages = new HashMap();
        
        // request the listing of all pages, and store them
        
        ListAllPagesMessage pageReq = new ListAllPagesMessage();
        
        if (!pageReq.sendRequest()) {
            throw new Exception("Page request failed");
        }
        
        BPPageList pageList = (BPPageList)pageReq.getResponseObject();
        
        List ids = pageList.getPageIDList();
        
        // for each page, send a request for the page
        for (Iterator it = ids.iterator(); it.hasNext(); ) {
            String id = (String)it.next();
            
            logger.debug("Page ID: " + id);
            
            Map param = new HashMap();
            param.put(IBackpackMessage.PAGE_ID, id);
            ShowPageMessage showPageMsg = new ShowPageMessage(param);
            
            if (!showPageMsg.sendRequest()) {
                throw new Exception("Page request failed");
            }
            
            pages.put(id, showPageMsg.getResponseObject());
        }
    }
    
    public BPPage getPageByID(String id) {
        return (BPPage)pages.get(id);
    }

}
