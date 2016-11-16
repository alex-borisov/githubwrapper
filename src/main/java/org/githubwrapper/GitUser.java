package org.githubwrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class GitUser extends GitCommitter {
    private String company, blog, userName, location;
    private Collection<GitRepository> repositories;

    public GitUser() {
    }

    public GitUser(String company, String name, String blog, String userName, String email, String location, Collection<GitRepository> repositories) {
        super(name, email);
        this.company = company;
        this.blog = blog;
        this.userName = userName;
        this.location = location;
        this.repositories = repositories;
    }

    public static GitUser loadJSON(JSONObject o) throws JSONException {
        GitUser user = new GitUser();
        user.setName(JSONUtils.getIfExists("name", "", o).toString());
        user.setCompany(JSONUtils.getIfExists("company", "", o).toString());
        user.setBlog(JSONUtils.getIfExists("blog", "", o).toString());
        user.setUserName(JSONUtils.getIfExists("login", "", o).toString());
        user.setEmail(JSONUtils.getIfExists("email", "", o).toString());
        user.setLocation(JSONUtils.getIfExists("location", "", o).toString());
        JSONArray repos = (JSONArray) JSONUtils.getIfExists("repositories", new JSONArray(), o);
        Collection<GitRepository> repositories = new ArrayList<GitRepository>();
        for (int i = 0; i < repos.length(); i++)
            repositories.add(GitRepository.loadJSON((JSONObject) repos.get(i)));
        user.setRepositories(repositories);
        return user;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Collection<GitRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(Collection<GitRepository> repositories) {
        this.repositories = repositories;
    }
}
