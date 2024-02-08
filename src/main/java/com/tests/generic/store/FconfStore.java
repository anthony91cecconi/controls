package com.tests.generic.store;

import java.util.ArrayList;
import java.util.List;

import com.tests.generic.entitis.FileConfComplete;

public class FconfStore {
    private List<FileConfComplete> filesConfs;

    public FconfStore() {
        this.filesConfs = new ArrayList();
    }

    public List<FileConfComplete> getFilesConfs() {
        return filesConfs;
    }

    public void setFilesConfs(List<FileConfComplete> filesConfs) {
        this.filesConfs = filesConfs;
    }

    public void addFilesConfs(FileConfComplete filesConfs) {
        this.filesConfs.add(filesConfs);
    }

}
