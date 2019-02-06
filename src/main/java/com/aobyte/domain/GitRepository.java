package com.aobyte.domain;

import com.aobyte.helper.LoadHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by home on 2/4/2019.
 */
public class GitRepository {

    private Git git;
    private String url;
    private String repositoryName;
    private String origin;
    private Set<String> added;
    private Set<String> uncommittedChanges;
    private Set<String> untracked;

    public GitRepository(String localUrl) throws IOException, GitAPIException {
        Git git = LoadHelper.loadGit(localUrl);
        this.git = git;
        this.added = git.status().call().getAdded();
        this.uncommittedChanges = git.status().call().getUncommittedChanges();
        this.untracked = git.status().call().getUntracked();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Git getGit() {
        return git;
    }

    public void setGit(Git git) {
        this.git = git;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public Set<String> getAdded() {
        return added;
    }

    public void setAdded(Set<String> added) {
        this.added = added;
    }

    public Set<String> getUncommittedChanges() {
        return uncommittedChanges;
    }

    public void setUncommittedChanges(Set<String> uncommittedChanges) {
        this.uncommittedChanges = uncommittedChanges;
    }

    public Set<String> getUntracked() {
        return untracked;
    }

    public void setUntracked(Set<String> untracked) {
        this.untracked = untracked;
    }

}
