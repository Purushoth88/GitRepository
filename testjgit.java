

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.internal.storage.file.ObjectDirectory;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class testjgit {

	public static void main(String[] args)
	{
        File localRepoDir = new File("C:\\Users\\Joe\\Desktop\\Eclipse4.4PDE\\eclipse\\workspace\\testjgit");
        File localRepoGitDir = new File("C:\\Users\\Joe\\Desktop\\Eclipse4.4PDE\\eclipse\\workspace\\testjgit", "/.git");
        if(!localRepoDir.isDirectory())
        {
      	  localRepoDir.mkdirs();
        }
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try {
        	
        	
            System.out.println(localRepoDir.getAbsolutePath().toString());
            System.out.println(Paths.get("").toAbsolutePath().toString());
            //Git git = Git.open(localRepoDir)
			//Repository localRepo = builder.setGitDir(localRepoGitDir).readEnvironment().findGitDir().setup().build();
			Git git = Git.init().setDirectory(localRepoDir).call();

	        //ObjectDatabase o = git.getRepository().getObjectDatabase();
	        //ObjectDirectory d = (ObjectDirectory)o;
	        //System.out.println(d.getDirectory());
			
	        
	     
			File myfile = new File(localRepoDir + "/myfile");
	        myfile.createNewFile();
	        git.add().addFilepattern("myfile").call();
	        git.add().addFilepattern(".").call();
	        git.commit().setMessage("first commit").call();
			
	        git.push().setRemote("https://github.com/jluke13/testjgit.git").setCredentialsProvider(new UsernamePasswordCredentialsProvider("jluke13", "4phunmat")).call();
	        
	        git.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoFilepatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
