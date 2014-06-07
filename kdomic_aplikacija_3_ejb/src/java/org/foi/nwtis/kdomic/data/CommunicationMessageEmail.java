/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.kdomic.data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Krunoslav
 */
public class CommunicationMessageEmail implements Serializable{
    
    private Date startTime;
    private Date endTime;
    private Integer numberOfMessages;
    private Integer numberOfNwtisMessage;
    private Integer numberOfOtherMessages;

    public CommunicationMessageEmail(Date startTime, Date endTime, Integer numberOfMessages, Integer numberOfNwtisMessage, Integer numberOfOtherMessages) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfMessages = numberOfMessages;
        this.numberOfNwtisMessage = numberOfNwtisMessage;
        this.numberOfOtherMessages = numberOfOtherMessages;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(Integer numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public Integer getNumberOfNwtisMessage() {
        return numberOfNwtisMessage;
    }

    public void setNumberOfNwtisMessage(Integer numberOfNwtisMessage) {
        this.numberOfNwtisMessage = numberOfNwtisMessage;
    }

    public Integer getNumberOfOtherMessages() {
        return numberOfOtherMessages;
    }

    public void setNumberOfOtherMessages(Integer numberOfOtherMessages) {
        this.numberOfOtherMessages = numberOfOtherMessages;
    }
    
    
    
}
