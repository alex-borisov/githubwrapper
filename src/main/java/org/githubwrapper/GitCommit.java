package org.githubwrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class GitCommit {
    String id, message, url, tree;
    GitCommitter author, committer;
    Collection<String> added, removed, parents;
    Date commitDate, authorDate;
    Collection<GitModification> modified;

    public GitCommit() {
    }

    public static GitCommit loadJSON(JSONObject o) throws JSONException {
        GitCommit commit = new GitCommit();
        //JSONObject jsonCommit = (JSONObject) JSONUtils.getIfExists("commit", "", o);
        commit.setMessage(JSONUtils.getIfExists("message", "", o).toString());
        commit.setUrl(JSONUtils.getIfExists("url", "", o).toString());
        commit.setId(JSONUtils.getIfExists("sha", "", o).toString());
        commit.setTree(JSONUtils.getIfExists("tree", "", o).toString());
        commit.setAuthor(GitCommitter.loadJSON((JSONObject) JSONUtils.getIfExists("author", new JSONObject(), o)));
        commit.setCommitter(GitCommitter.loadJSON((JSONObject) JSONUtils.getIfExists("committer", new JSONObject(), o)));
        commit.setParents(mapToCollection("id", (JSONArray) JSONUtils.getIfExists("parents", new JSONArray(), o)));
        /*
        try {
            commit.setCommitDate(GithubWrapper.parseDate(JSONUtils.getIfExists("committed_date", "", o).toString()));
            commit.setAuthorDate(GithubWrapper.parseDate(JSONUtils.getIfExists("authored_date", "", o).toString()));
        } catch (ParseException e) {
            throw new JSONException(e);
        }
        */
        commit.setAdded(mapToCollection("filename", (JSONArray) JSONUtils.getIfExists("files", new JSONArray(), o)));
        commit.setRemoved(mapToCollection("filename", (JSONArray) JSONUtils.getIfExists("removed", new JSONArray(), o)));

        JSONArray a = (JSONArray) JSONUtils.getIfExists("modified", new JSONArray(), o);
        Collection<GitModification> mods = new ArrayList<GitModification>();

        for (int i = 0; i < a.length(); i++)
            mods.add(GitModification.loadJSON((JSONObject) a.get(i)));

        commit.setModified(mods);

        return commit;
    }

    private static Collection<String> mapToCollection(String key, JSONArray a) throws JSONException {
        Collection<String> s = new ArrayList<String>();
        for (int i = 0; i < a.length(); i++) {
            Object parent = ((JSONObject) a.get(i)).get(key);
            s.add(parent.toString());
        }
        return s;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public GitCommitter getAuthor() {
        return author;
    }

    public void setAuthor(GitCommitter author) {
        this.author = author;
    }

    public GitCommitter getCommitter() {
        return committer;
    }

    public void setCommitter(GitCommitter committer) {
        this.committer = committer;
    }

    public Collection<String> getAdded() {
        return added;
    }

    public void setAdded(Collection<String> added) {
        this.added = added;
    }

    public Collection<String> getRemoved() {
        return removed;
    }

    public void setRemoved(Collection<String> removed) {
        this.removed = removed;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public Date getAuthorDate() {
        return authorDate;
    }

    public void setAuthorDate(Date authorDate) {
        this.authorDate = authorDate;
    }

    public Collection<GitModification> getModified() {
        return modified;
    }

    public void setModified(Collection<GitModification> modified) {
        this.modified = modified;
    }

    public Collection<String> getParents() {
        return parents;
    }

    public void setParents(Collection<String> parents) {
        this.parents = parents;
    }

    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }
}
