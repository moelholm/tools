package com.moelholm.tools.mediaorganizer.filesystem;

public enum FileSystemType {

    LOCAL, DROPBOX;

    public static FileSystemType fromString(String fileSystemTypeAsString) {
        return (fileSystemTypeAsString == null) ? LOCAL : valueOf(fileSystemTypeAsString.toUpperCase());
    }
}
