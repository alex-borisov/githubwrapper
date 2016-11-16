package org.githubwrapper;

import junit.framework.TestCase;

import java.util.Collection;

public class GithubWrapperTest extends TestCase {
    String userName = "alex-borisov";
    String repository = "spring4fun";
    String modifyCommit = "715bf3c1cfaa7b0661b43fd26b4a826e49f67951";
    String addCommit = "840df8b3018c3d1622c0eb99ca62af551d8aee38";
    GithubWrapper githubWrapper;

    protected void setUp() throws Exception {
        githubWrapper = new GithubWrapper(userName, repository);
    }

    public void testGetCommits() throws Exception {
        Collection<GitCommit> commits = githubWrapper.getCommits();
        assertTrue(commits.size() > 0);
        for (GitCommit commit : commits)
            validateBasicCommit(commit);
    }

    public void testGetCommit() throws Exception {
        GitCommit commit = githubWrapper.getCommit(addCommit);
        assertEquals(addCommit, commit.getId());
        validateBasicCommit(commit);
        assertTrue(commit.getAdded().size() > 0);
    }

    public void testDiffs() throws Exception {
        GitCommit commit = githubWrapper.getCommit(modifyCommit);
        assertEquals(modifyCommit, commit.getId());
        validateBasicCommit(commit);
        assertTrue(commit.getModified().size() > 0);
        for (GitModification mod : commit.getModified())
            validateModification(mod);
    }

    private void validateModification(GitModification mod) {
        assertNotNull(mod.getDiff());
        assertNotNull(mod.getFileName());
    }

    public void testGetUserInfo() throws Exception {
        GitUser alex = githubWrapper.getUserInfo();
        validateCommitter(alex);
    }

    private void validateRepository(GitRepository repo) {
        assertNotNull(repo.getName());
        assertNotNull(repo.getUrl());
        assertNotNull(repo.getDescription());
    }

    private void validateBasicCommit(GitCommit commit) {
        assertNotNull(commit.getMessage());
        assertNotNull(commit.getParents());
        validateCommitter(commit.getAuthor());
        assertNotNull(commit.getId());
       // assertNotNull(commit.getCommitDate());
     //   assertNotNull(commit.getAuthorDate());
        assertNotNull(commit.getTree());
        validateCommitter(commit.getCommitter());
    }

    private void validateCommitter(GitCommitter committer) {
        assertNotNull(committer);
        assertNotNull(committer.getName());
        assertNotNull(committer.getEmail());
    }
}
