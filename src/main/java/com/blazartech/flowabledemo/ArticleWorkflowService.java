/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.flowabledemo;

import java.util.List;

/**
 *
 * @author scott
 */
public interface ArticleWorkflowService {
    
    public void startProcess(Article article);
    
    public List<Article> getTasks(String assignee);
 
    public void submitReview(Approval approval);
}
