# CodacyChallenge
Assumptions:
1. This program was executed in eclipse and is working in this condition
  TODO: Create an autonomous execution script
2. TODO: Generalization exersise not completed

Steps:
1. Edit the target\classes\config.properties and adjust the properties
    - command.line.separator: The command line separator to execute multiple commands in one line (Windows: &&, Linux: ;)
    - repo.path: The local repo path destination, the directory must exist
    - repo.url: The url of the repo
    - api.minThreads: Min threads for the commit api listener (no need to change this)
    - api.maxThreads: Max threads for the commit api listener (no need to change this)  
    - api.timeoutMillis: Timeout in milliseconds for the commit api listener (no need to change this)
    - api.port: Port listening for the commit api
2. Run the Main class in eclipse
3. Invoke the WebService (e.g. in browser) adjusting the following parameters to control payload
  REST API: http://localhost:8888/commits
  Parameter beforeDate: the date until the commits will be showned
  Paramenter maxCount: the total number of commits to return
  Example: http://localhost:8888/commits?beforeDate=2018.10.09&maxCount=10
  
  
  
  
  