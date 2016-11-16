package org.githubwrapper;

import org.json.JSONException;
import org.json.JSONObject;

public class GitModification {
    String fileName, diff;

    public GitModification() {
    }

    public GitModification(String fileName, String diff) {
        this.fileName = fileName;
        this.diff = diff;
    }

    public static GitModification loadJSON(JSONObject o) throws JSONException {
        return new GitModification(JSONUtils.getIfExists("filename", "", o).toString(), JSONUtils.getIfExists("diff", "", o).toString());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }
}
