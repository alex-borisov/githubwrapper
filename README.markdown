## A Java Client for the GitHub API.

### Installing
* Clone this repository and build the project with maven.

        git clone git://github.com/alex-borisov/githubwrapper.git
        mvn install
  
* Or simply include the library as a dependency in your maven project.

  * Add the dependency
  
               <dependency>
                   <groupId>org.githubwrapper</groupId>
                   <artifactId>githubwrapper</artifactId>
                   <version>1.0</version>
               </dependency>
  
### Using GithubWrapper
* Create a GithubWrapper client.

        GithubWrapper hub = new GithubWrapper("user", "repository");
  
* Get the recent commits for a project.

        Collection<GitCommit> commits = hub.getCommits();

* Get a specific commit.

        GitCommit anImportantCommit = hub.getCommit("840df8b3018c3d1622c0eb99ca62af551d8aee38");
  
* Get the information for a GitHub user including their repositories.

        GitUser alex = hub.getUserInfo();



