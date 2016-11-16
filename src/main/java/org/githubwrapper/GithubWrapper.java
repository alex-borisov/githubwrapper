package org.githubwrapper;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

//  http://github.com/api/version/format/username/repository/type/object
public class GithubWrapper {
    private static final String GITHUB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(GithubWrapper.GITHUB_DATE_FORMAT);
    private Logger log = Logger.getLogger(GithubWrapper.class);
    private String userName;
    private String repository;
    private String version = "1";
    private String branch = "master";
    private HttpClient http;

    public GithubWrapper(String userName, String repository) {
        this.userName = userName;
        this.repository = repository;
        this.http = new HttpClient();
    }

    public GithubWrapper(String userName, String repository, String branch, String version) {
        this(userName, repository);
        this.version = version;
        this.branch = branch;
    }

    //https://api.github.com/repos/romantal/flightflex/commits
    public static String formatUrl(String userName, String repository, String type, String objectId) {
        return String.format("http://api.github.com/repos/%s/%s/%s/%s", userName, repository, type, objectId);
    }

    public static String formatUrl(String userName, String repository, String type) {
        return String.format("http://api.github.com/repos/%s/%s/%s", userName, repository, type);
    }

    public static String formatCommitInfoUrl(String userName, String repository, String commitId) {
        return String.format("https://api.github.com/repos/%s/%s/commits/%s", userName, repository, commitId);
    }



    public static String formatUserInfoUrl(String userName) {
        return String.format("http://api.github.com/users/%s", userName);
    }

    public static Date parseDate(String dateString) throws ParseException {
        int tzError = dateString.lastIndexOf(':');
        String rfc822 = dateString.substring(0, tzError) + dateString.substring(tzError + 1);
        return dateFormat.parse(rfc822);
    }

    public Collection<GitCommit> getCommits() {
        String url = GithubWrapper.formatUrl(getUserName(), getRepository(), "commits");
        JSONArray a = fetchJsonArray(url);
        Collection<GitCommit> commits = new ArrayList<GitCommit>();

        try {
            for (int i = 0; i < a.length(); i++) {
                Object commit = a.get(i);
                if (commit.getClass().equals(JSONObject.class))
                    commits.add(GitCommit.loadJSON( (JSONObject) JSONUtils.getIfExists("commit", "", (JSONObject)commit)));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return commits;
    }

    public GitCommit getCommit(String id) {
        String url = GithubWrapper.formatCommitInfoUrl(getUserName(), getRepository(), id);
        JSONObject response = fetchJsonObject(url);
        try {
            return GitCommit.loadJSON(response);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public GitUser getUserInfo() {
        String url = GithubWrapper.formatUserInfoUrl(getUserName());
        JSONObject response = fetchJsonObject(url);
        try {
            return GitUser.loadJSON(response);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONObject fetchJsonObject(String url) {
        String body = fetchJsonString(url);

        JSONObject json = null;
        try {
            json = new JSONObject(body.trim());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    private JSONArray fetchJsonArray(String url) {
        String body = fetchJsonString(url);

        JSONArray json = null;
        try {
            json = new JSONArray(body.trim());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    private String fetchJsonString(String url) {
        GetMethod get = new GetMethod(url);
        String body;
        int code;
        try {
            log.debug("Executing HTTP/GET " + url);
            code = http.executeMethod(get);
            if (code >= 200 && code < 300) {
                body = new String(get.getResponseBody());
            } else {
                log.warn("HTTP/GET Failed with code " + code);
                throw new RuntimeException(String.format("HTTP Error %s while connecting to GithubWrapper: %s", code, url));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to GithubWrapper: " + url, e);
        }
        return body;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
