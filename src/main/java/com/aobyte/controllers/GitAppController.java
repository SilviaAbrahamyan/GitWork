package com.aobyte.controllers;


import com.aobyte.domain.GitRepository;
import com.aobyte.service.GitService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Controller
public class GitAppController {

    private final GitService gitService;

    public GitAppController(GitService gitService) {
        this.gitService = gitService;
    }

    @GetMapping("/")
    public String homePage(Model model) throws GitAPIException, IOException {
        List<GitRepository> gitRepositoryList = gitService.loadGitRepository();
        model.addAttribute("properties", gitRepositoryList);
        return "home";
    }

    @PostMapping("/commit")
    public ModelAndView sendCommitMessage(HttpServletRequest request) throws GitAPIException, IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        String message = request.getParameter("message");
        String name = request.getParameter("name");
        String psw = request.getParameter("psw");
        gitService.commitAndPush(message, name, psw);
        return modelAndView;
    }
}
