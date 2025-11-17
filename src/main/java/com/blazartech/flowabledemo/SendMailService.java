/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.flowabledemo;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 *
 * @author scott
 */
@Slf4j
public class SendMailService implements JavaDelegate {
    
    public void execute(DelegateExecution execution) {
        log.info("Sending rejection mail to author.");
    }
}
