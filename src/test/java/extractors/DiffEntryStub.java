package extractors;

import org.eclipse.jgit.diff.DiffEntry;

class DiffEntryStub extends DiffEntry {

    DiffEntryStub() {
        super();
    }

    @Override
    public String getNewPath(){
        return "New path";
    }

    @Override
    public  String getOldPath(){
        return "Old path";
    }

}
