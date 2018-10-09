package demo.command.chain;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import demo.Common;
import demo.command.Chdir;
import demo.command.GitClone;
import demo.command.GitLog;
import demo.command.GitPull;

/**
 * This class define the command chain to obtain the commit history
 * @author fernandojribeiro
 */
public class CommitHistory extends CommandChain {
	String repoPath;
	String repoName;
	String repoUrl;
	String repoFullPath;
	String beforeDate;
	int maxCount;

	public CommitHistory(Common common) {
		this.repoName = common.getProperty(Common.REPO_NAME);
		this.repoPath = common.getProperty(Common.REPO_PATH);
		this.repoUrl = common.getProperty(Common.REPO_URL);
		this.commandSeparator = common.getProperty(Common.COMMAND_SEPARATOR);
		this.repoFullPath = repoPath + File.separator + repoName;
	}

	/**
	 * Define the Max Count for the git history
	 * @author fernandojribeiro
	 * @param number to define as max count
	 */
	public void maxCount(int number) {
		this.maxCount = number;
	}
	
	/**
	 * Define the Until Date for the git history
	 * @author fernandojribeiro
	 * @param date to define as the until date
	 */
	public void beforeDate(String date) {
		this.beforeDate = date;
	}

	/**
	 * Abstract method to define the command chain to execute.
	 * 
	 * @author fernandojribeiro
	 * @return the return status of the executed command chain
	 */
	public CommandOutput execute() {
		try {
			// This was done to make the git clone previously in the first execution.
			if(Files.notExists(Paths.get(repoFullPath), LinkOption.NOFOLLOW_LINKS)) {
				String[] cloneCommands = {
						new Chdir(repoPath).getCommand(),
						new GitClone(repoUrl).getCommand(),
				};
				executeCommands(cloneCommands);
			} else {
				String[] pullCommands = {
						new Chdir(repoFullPath).getCommand(),
						new GitPull().getCommand(),
				};
				executeCommands(pullCommands);
			}

			GitLog gitlog = new GitLog();
			gitlog.maxCount(maxCount);
			gitlog.beforeDate(beforeDate);

			String[] commands = {
					new Chdir(repoFullPath).getCommand(),
					gitlog.getCommand()
			};
			return executeCommands(commands);
		} catch(Exception e) {
			Common.getLogger().error("Error during command chain execution: " + e.getMessage(), e);
			return null;
		}
	}
}
