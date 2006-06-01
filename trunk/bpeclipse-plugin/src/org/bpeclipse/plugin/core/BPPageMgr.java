/**
 * 
 */
package org.bpeclipse.plugin.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bpeclipse.api.BPException;
import org.bpeclipse.api.bpobjects.BPPage;
import org.bpeclipse.api.bpobjects.BPPageList;
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
    
    private static Logger logger = Logger.getLogger(BPPageMgr.class);
    
    private Map pageMap;
    private Map pageTitleMap;
    
    public static BPPageMgr getInstance() {
        if (instance == null) {
            instance = new BPPageMgr();
        }
        
        return instance;
    }
    
    protected BPPageMgr() {
        pageMap = new HashMap();
        pageTitleMap = new HashMap();
    }
    
    public void initialize() throws BPPluginException {
        
        // request the listing of all pages, and store them
        try {
            ListAllPagesMessage pageReq = new ListAllPagesMessage();
            
            pageReq.sendRequest();
            
            BPPageList pageList = (BPPageList)pageReq.getResponseObject();
            
            this.pageTitleMap = pageList.getPageMap();
            
        } catch (BPException e) {
            
            throw new BPPluginException("Failed to initialize", e);
        }
    }
    
    public BPPage getPageByID(String id) {
        
        if (!pageMap.containsKey(id)) {

            logger.debug("Retrieving Page ID: " + id);
            logger.debug("....");
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            
            Map param = new HashMap();
            param.put(IBackpackMessage.PAGE_ID, id);
            ShowPageMessage showPageMsg = new ShowPageMessage(param);
            
            try {
                showPageMsg.sendRequest();
            } catch (BPException e) {
                logger.error("Could not retrieve page " + id, e);
                return null;
            }
            
            pageMap.put(id, showPageMsg.getResponseObject());
            
        }
        
        return (BPPage)pageMap.get(id);
    }
    
    public Collection getPageIDs() {
        return this.pageTitleMap.keySet();
    }

    public String getPageTitle(String id) {
        return (String)this.pageTitleMap.get(id);
    }

}
