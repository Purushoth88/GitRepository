import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to commit a file
 * 
 * @author dominik.stadler@gmx.at
 */
public class CommitFile {

    public static void main(String[] args) throws IOException, GitAPIException {
    	FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(
				new File("/home/lazher/git/Repository/.git/TestCloneRepository/.git"))
		// scan up the file system tree
				.build();
        Git git = new Git(repository);

        // create the file
        File myfile = new File(repository.getDirectory().getParent()+"/FirstFolder", "testfile");
        myfile.createNewFile();

        // run the add
        git.add()
                .addFilepattern("testfile")
                .call();

        // and then commit the changes
        git.commit()
                .setMessage("Added testfile")
                .call();

        System.out.println("Committed file " + myfile.getName() + " to the repository at " + repository.getDirectory());

        repository.close();
    }
}
