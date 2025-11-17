/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.flowabledemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author scott
 */
@Service
@Slf4j
public class ArticleWorkflowServiceImpl implements ArticleWorkflowService {
    
    @Autowired
    private RuntimeService runtimeService;
 
    @Autowired
    private TaskService taskService;

    @Transactional
    @Override
    public void startProcess(Article article) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("author", article.getAuthor());
        variables.put("url", article.getUrl());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("articleReview", variables);
        log.info("process status = {}", processInstance.getBusinessStatus());
    }
 
    @Transactional
    @Override
    public List<Article> getTasks(String assignee) {
        log.info("getting tasks for assignee {}", assignee);
        List<Task> tasks = taskService.createTaskQuery()
          .taskCandidateGroup(assignee)
          .list();
        return tasks.stream()
          .map(task -> {
              Map<String, Object> variables = taskService.getVariables(task.getId());
              return new Article(task.getId(), (String) variables.get("author"), (String) variables.get("url"));
          })
          .collect(Collectors.toList());
    }
 
    @Transactional
    @Override
    public void submitReview(Approval approval) {
        log.info("submitting a review {}", approval);
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", approval.isStatus());
        taskService.complete(approval.getId(), variables);
    }
}
