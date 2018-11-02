# CodacyChallenge
Assumptions:
1. This program was executed in eclipse and is working in this condition
  TODO: Create an autonomous execution script
2. Documentation is here: CommitViewer\doc\index.html

Steps:
1. Edit the target\classes\config.properties and adjust the properties
    - command.line.separator: The command line separator to execute multiple commands in one line (Windows: &&, Linux: ;)
	- repo.name: The name of the repo, this don't need to be changed
    - repo.path: The local repo path destination, the directory must exist
    - repo.url: The url of the repo
	- rest.commits.url: the url of the public commit api (git)
	- rest.commits.sincedatetag: the since date tag to replace in the public commit api, don't need to change this
	- rest.commits.untildatetag: the until date tag to replace in the public commit api, don't need to change this
    - api.dateFormat: the acceptable date format of the request parameters
    - api.minThreads: Min threads for the commit api listener (no need to change this)
    - api.maxThreads: Max threads for the commit api listener (no need to change this)  
    - api.timeoutMillis: Timeout in milliseconds for the commit api listener (no need to change this)
    - api.port: Port listening for the commit api
2. Copy the files log4j2.xml and config.properties to the folder target\classes
3. Run the Main class in eclipse
4. Invoke the WebService (e.g. in browser) adjusting the following parameters to control payload
  REST API: http://localhost:8888/commits
  Parameter untilDate: the date until the commits will be showned (format: yyyy-mm-dd)
  Paramenter maxCount: the total number of commits to return (format: yyyy-mm-dd)
  Example: http://localhost:8888/commits?sinceDate=2018-09-09&untilDate=2018-11-09