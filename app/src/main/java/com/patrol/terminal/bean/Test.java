package com.patrol.terminal.bean;

import java.io.File;

public class Test {
    private String id;
    private Test2 test2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Test2 getTest2() {
        return test2;
    }

    public void setTest2(Test2 test2) {
        this.test2 = test2;
    }

    public static class Test2 {
        private File file;

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }
    }
}

