package com.aobyte.helper;

import com.aobyte.domain.GitRepository;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by home on 2/5/2019.
 */
public class LoadHelper {
    public static Properties loadProperties(String propFileName) {
        List<GitRepository> gitRepositoryList = new ArrayList<GitRepository>();
        Properties properties = new Properties();
        InputStream input = null;
        String localUrl = null;
        try {
            input = LoadHelper.class.getClassLoader().getResourceAsStream(propFileName);
            if (input == null) {
                throw new RuntimeException("No properties file found...");
            }
            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public static Git loadGit(String url) throws GitAPIException, IOException {
        String localRoot = "C:/git" + "/" + GitUrlHelper.getRepositoryName(url);
        File file = new File(localRoot);
        Git git = null;
        if (!file.exists()) {
            git = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(file)
                    .call();
        } else {
            git = Git.open(new File(localRoot));
        }
        return git;
    }
}
