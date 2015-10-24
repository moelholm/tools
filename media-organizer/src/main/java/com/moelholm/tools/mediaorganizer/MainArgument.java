package com.moelholm.tools.mediaorganizer;

public enum MainArgument {

    DAEMON_RUNMODE("daemon"), FILESYSTEM_TYPE("filesystemtype"), FROM_DIR("fromDir"), TO_DIR("toDir");

    private MainArgument(String argumentName) {
        this.argumentName = argumentName;
    }

    private final String argumentName;

    public String getArgumentName() {
        return argumentName;
    }
}
