
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException; 
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider; 
 
 
 
/**
 * Simple snippet which shows how to clone a repository from a remote source 
 * 
 * @author dominik.stadler at gmx.at 
 */ 
public class PullFromRemoteRepository { 
 
    static Workbook wb = new XSSFWorkbook();

    //private static final String REMOTE_URL = "https://github.com/Purushoth88/Sauce-Java-Sample-Working.git"; 
    static String name = "Purushoth88";
    static String password = "October@12";
    static String REMOTE_URL = "git://github.com/Purushoth88/Test.git";

    // credentials
    CredentialsProvider cp = new UsernamePasswordCredentialsProvider(name, password);
    public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException { 
        // prepare a new folder for the cloned repository 
        File localPath = File.createTempFile("TestGitRepository", ""); 
        localPath.delete(); 
 
        // then clone 
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider(name, password);

        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath); 
        CloneCommand result = new CloneCommand()
                .setCredentialsProvider(cp)
                .setDirectory(localPath)
                .setURI(REMOTE_URL);
        Git git = result.call();
/*         // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks! 
         System.out.println("Having repository: " + result.getRepository().getDirectory()); 
         try (Git git = new Git(result.getRepository())) { 
                git.pull() 
                .call(); 
         } */
 
        	
            // prepare a new folder for the cloned repository
            String ResultfileToImport = localPath + "Result_"
            	 + "_" + new Random().nextInt(50046846) + ".xlsx";
            FileOutputStream out = new FileOutputStream(ResultfileToImport, true);
            System.out.println("out from " + out);
            System.out.println("ResultfileToImport from " + ResultfileToImport);
            wb.write(out);
            	
            	//Git git = result.call();
                // add
                AddCommand ac = git.add();
                String LogStatus = git.log().toString();
                System.out.println("LogStatus" + LogStatus);
                String GitStatus = git.status().toString();
                System.out.println("GitStatus" + GitStatus);
                ac.addFilepattern(ResultfileToImport);
                try {
                    ac.call();
                } catch (NoFilepatternException e) {
                    e.printStackTrace();
                }
                
                git.pull().setRemoteBranchName(ResultfileToImport).setCredentialsProvider(cp);
                git.pull().call();
                // commit
                CommitCommand commit = git.commit().setAll(true);
              commit.setMessage("text.xls");
                try {
                    commit.call();
                } catch (NoHeadException e) {
                    e.printStackTrace();
                } catch (NoMessageException e) {
                    e.printStackTrace();
                } catch (ConcurrentRefUpdateException e) {
                    e.printStackTrace();
                } catch (WrongRepositoryStateException e) {
                    e.printStackTrace();
                }
                
                PushCommand pc = git.push();
    	        pc.setCredentialsProvider(cp)
    	                .setForce(true)
    	                .setOutputStream(out).setPushAll();
    	        try {
    	            Iterator<PushResult> it = pc.call().iterator();
    	            if(it.hasNext()){
    	                System.out.println(it.next().toString());
    	            }
    	        } catch (InvalidRemoteException e) {
    	            e.printStackTrace();
    	        }
                
                
        	
        	
            // workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=474093 
        } 
}
