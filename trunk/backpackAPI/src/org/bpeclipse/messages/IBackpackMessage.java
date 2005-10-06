package org.bpeclipse.messages;

import org.bpeclipse.bpobjects.IBPObject;



/**
 * An interface for messages
 * @author achoi
 *
 */
public interface IBackpackMessage {
    
    public static final String PAGE_ID = "pageId";
    
    /**
     * Send the HTTP request
     * @return Whether the request was successful
     */
    public boolean sendRequest();
    
    /**
     * After receiving and handling the response, get the object encapsulated in the response 
     * @return the response object, or null if there was none
     */
    public IBPObject getResponseObject();

}
