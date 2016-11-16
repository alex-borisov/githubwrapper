package org.githubwrapper;

import org.json.JSONException;
import org.json.JSONObject;

public class GitRepository {
    private String name;
    private String url;
    private String description;
    private String homePage;

    public GitRepository() {
    }

    public GitRepository(String name, String url, String description, String homePage) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.homePage = homePage;
    }

    public static GitRepository loadJSON(JSONObject o) throws JSONException {
        GitRepository repo = new GitRepository();
        repo.setName(JSONUtils.getIfExists("name", "", o).toString());
        repo.setUrl(JSONUtils.getIfExists("url", "", o).toString());
        repo.setDescription(JSONUtils.getIfExists("description", "", o).toString());
        repo.setHomePage(JSONUtils.getIfExists("homepage", "", o).toString());
        return repo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }
}
