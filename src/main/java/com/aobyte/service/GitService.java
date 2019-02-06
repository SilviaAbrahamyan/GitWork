package com.aobyte.service;

import com.aobyte.domain.GitRepository;
import com.aobyte.helper.GitUrlHelper;
import com.aobyte.helper.LoadHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Service
public class GitService {

    public List<GitRepository> loadGitRepository() throws GitAPIException, IOException {
        List<GitRepository> gitRepositoryList = new ArrayList<GitRepository>();
        String propGitRoot = "gitroot.properties";
        Properties properties = LoadHelper.loadProperties(propGitRoot);

        String gitRepositoryUrl;
        for (int i = 1; i <= properties.size(); i++) {
            gitRepositoryUrl = properties.getProperty("git" + i + ".root"); //https://github.com/SilviaAbrahamyan/BackEnd
            String repoName = GitUrlHelper.getRepositoryName(gitRepositoryUrl); //BackEnd
            GitRepository gitRepository = new GitRepository(gitRepositoryUrl);
            gitRepository.setOrigin(gitRepositoryUrl);
            gitRepository.setRepositoryName(repoName);
            showStatus(gitRepository);
            gitRepositoryList.add(gitRepository);
        }
        return gitRepositoryList;
    }

    public  void commitAndPush(String message, String name, String psw) throws GitAPIException, IOException {
        for (GitRepository gitRepository : this.loadGitRepository()) {
                gitRepository
                        .getGit()
                        .add()
                        .addFilepattern(".")
                        .call();
                gitRepository
                        .getGit()
                        .commit()
                        .setAll(true)
                        .setMessage(message)
                        .call();
                gitRepository
                        .getGit()
                        .push()
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(name, psw))
                        .setRemote(gitRepository.getOrigin())
                        .call();
            showStatus(gitRepository);
        }
    }
    public static void showStatus(GitRepository gitRepository) {
        System.out.println(gitRepository.getRepositoryName());
        for (String add : gitRepository.getAdded()) {
            System.out.println("Added: " + add);
        }
        for (String uncommitted : gitRepository.getUncommittedChanges()) {
            System.out.println("Uncommitted: " + uncommitted);
        }

        for (String untrack : gitRepository.getUntracked()) {
            System.out.println("Untracked: " + untrack);
        }
    }

}
