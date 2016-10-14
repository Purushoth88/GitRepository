import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class SampleGitTest {

	private static String localNewPath, localRemoPath, remotePath="https://github.com/Purushoth88/Test.git", remoteNewPath,
	projectName = "Test", userName="Purushoth88", passWord="October@12", userId="purush_it2007@yahoo.co.in";
	private Repository localRemoRepo, localNewRepo;
	private Git gitPrimary, gitSecondary;
	private static File index, delDir;
	private UsernamePasswordCredentialsProvider upcp = null;

	
	
	/*public CreateRepository(String uid,String uName, String pWd, String pName, String remPath ){
		
		userId=uid;
		userName=uName;
		passWord=pWd;
		projectName=pName;
		remotePath=remPath;
		
	}*/
	
	public static void main(String[] args) throws InterruptedException {
		SampleGitTest cR = new SampleGitTest();
		try {
		
			cR.init();
		//	cR.createLocalRepo();
			cR.createRemoteRepo();
			cR.clonePrimaryRepo();
			cR.pullPrimaryRepo();
			cR.checkOutPrimaryRepo();
			cR.pushChangesToNewRepo();
		} catch (JGitInternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void init() throws IOException, GitAPIException {
	//	localNewPath = "/tmp/VOTSH/"+projectName;
		localRemoPath = System.getProperty("user.dir") + "/Test";
	//	index = new File(localNewPath);
		String[] rPath = remotePath.split("/");
		String basePath = rPath[0]+"//"+rPath[1]+rPath[2]+"/"+rPath[3]+"/";
		remoteNewPath = basePath + projectName
				+ ".git";
		localRemoRepo = new FileRepository(localRemoPath + "/.git");
	//	localNewRepo = new FileRepository(localNewPath + "/.git");
		upcp = new UsernamePasswordCredentialsProvider("Purushoth88",
				"October@12");
		gitPrimary = new Git(localRemoRepo);
	//	gitSecondary = new Git(localNewRepo);
		System.out.println("in init");
	}

	public String createRemoteRepo() {
		String status = "";
		GitHub github;
		try {
			github = GitHub.connectUsingOAuth(userName, passWord);
			GHRepository repo = github
					.createRepository(projectName, "Created By"
							+" "+ userId,
							" ", true/* public */);
			status = "Success";
			System.out.println("Create Remote Repo");
		} catch (IOException e) {
			status = "Failure";
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public void clonePrimaryRepo() throws IOException, GitAPIException {
		try {
			Git.cloneRepository().setURI(remotePath)
					.setDirectory(new File(localRemoPath)).call();
			System.out.println("Cloned");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pullPrimaryRepo() throws IOException, GitAPIException {
		try {
			gitPrimary.pull().setCredentialsProvider(upcp).call();
			System.out.println("pull");
		} catch (Exception e) {
			System.out.println("Exception in pull");
			e.printStackTrace();
		}
	}

	public void checkOutPrimaryRepo() throws IOException, GitAPIException {
		try {
			gitPrimary.checkout().setAllPaths(true).call();
		} catch (Exception e) {
			System.out.println("exception checkout :");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void pushChangesToNewRepo() throws IOException,
			JGitInternalException, GitAPIException {
		gitPrimary.push().setRemote(remoteNewPath)
				.setCredentialsProvider(upcp).call();
		System.out.println("pushed");
	}


	public String remoteNewPath() {
		
	return remoteNewPath;
	}
}
