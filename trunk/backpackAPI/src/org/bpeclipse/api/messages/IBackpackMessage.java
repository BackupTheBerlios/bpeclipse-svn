package org.bpeclipse.api.messages;

import org.bpeclipse.api.BPException;
import org.bpeclipse.api.bpobjects.IBPObject;



/**
 * An interface for messages
 * @author achoi
 *
 */
public interface IBackpackMessage {
    
    public static final String PAGE_ID = "pageId";
    
    /**
     * Send the HTTP request
     * @throws BPException 
     */
    public void sendRequest() throws BPException;
    
    /**
     * After receiving and handling the response, get the object encapsulated in the response 
     * @return the response object, or null if there was none
     */
    public IBPObject getResponseObject();

}
