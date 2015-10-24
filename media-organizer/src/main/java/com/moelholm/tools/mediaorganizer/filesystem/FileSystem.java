package com.moelholm.tools.mediaorganizer.filesystem;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileSystem {

    void move(Path from, Path to) throws FileAlreadyExistsException, IOException;

    Stream<Path> streamOfAllFilesFromPath(Path from);

    boolean isExistingDirectory(Path from);

}
