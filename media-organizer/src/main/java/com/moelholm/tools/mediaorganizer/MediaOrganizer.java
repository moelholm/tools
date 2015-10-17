package com.moelholm.tools.mediaorganizer;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MediaOrganizer {

    private static final Logger LOG = LoggerFactory.getLogger(MediaOrganizer.class);

    @Autowired
    private MediaOrganizerConfiguration configuration;

    @Async
    public void undoFlatMessAsync(Path from, Path to) {
        undoFlatMess(from, to);
    }

    public void undoFlatMess(Path from, Path to) {
        LOG.info("Copying files from [{}] to [{}]", from, to);

        mediaFilesFromSourcePath(from) //
                .filter(selectMediaFiles())//
                .collect(groupByYearMonthDayString()) //
                .forEach((folderName, mediaFilePaths) -> {
                    LOG.info("Processing folder [{}] which has [{}] media files", folderName, mediaFilePaths.size());
                    Path destinationFolderPath = to.resolve(generateRealFolderName(folderName, mediaFilePaths));
                    mediaFilePaths.stream().forEach(p -> {
                        Path destinationFilePath = destinationFolderPath.resolve(p.getFileName());
                        LOG.info("    {}", destinationFilePath.getFileName());
                        if (destinationFilePath.toFile().exists()) {
                            LOG.info("File [{}] exists at destination folder - so skipping that", destinationFilePath.getFileName());
                        } else {
                            copyFile(p, destinationFolderPath.resolve(p.getFileName()));
                        }
                    });
                });
    }

    private Collector<Path, ?, Map<String, List<Path>>> groupByYearMonthDayString() {
        return Collectors.groupingBy(p -> toYearMonthDayString(p));
    }

    private Stream<Path> mediaFilesFromSourcePath(Path from) {
        try {
            return Files.list(from);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Predicate<? super Path> selectMediaFiles() {
        return p -> {
            for (String mediaFileExtensionsToMatch : configuration.getMediaFileExtensionsToMatch()) {
                if (p.toString().toLowerCase().endsWith(String.format(".%s", mediaFileExtensionsToMatch))) {
                    return true;
                }
            }
            return false;
        };
    }

    private String toYearMonthDayString(Path path) {
        Date date = parseDateFromPathName(path);

        if (date == null) {
            return "unknown";
        }

        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);

        int year = dateCal.get(Calendar.YEAR);
        String month = new DateFormatSymbols(configuration.getLocale()).getMonths()[dateCal.get(Calendar.MONTH)];
        month = Character.toUpperCase(month.charAt(0)) + month.substring(1);
        int day = dateCal.get(Calendar.DAY_OF_MONTH);

        return String.format("%s - %s - %s", year, month, day);
    }

    private String generateRealFolderName(String folderName, List<Path> mediaFilePaths) {
        String lastPartOfFolderName = "( - \\d+)$";
        String replaceWithNewLastPartOfFolderName;
        if (mediaFilePaths.size() >= configuration.getAmountOfMediaFilesIndicatingAnEvent()) {
            replaceWithNewLastPartOfFolderName = String.format("$1 - %s", configuration.getSuffixForDestinationFolderOfUnknownEventMediaFiles());
        } else {
            replaceWithNewLastPartOfFolderName = String.format(" - %s", configuration.getSuffixForDestinationFolderOfMiscMediaFiles());
        }
        return folderName.replaceAll(lastPartOfFolderName, replaceWithNewLastPartOfFolderName);
    }

    private Date parseDateFromPathName(Path path) {
        SimpleDateFormat sdf = new SimpleDateFormat(configuration.getMediaFilesDatePattern());
        try {
            return sdf.parse(path.getFileName().toString());
        } catch (ParseException e) {
            LOG.warn("Failed to extract date from {}", path, e);
            return null;
        }
    }

    private void copyFile(Path fromFilePath, Path toFilePath) {
        try {
            toFilePath.toFile().mkdirs();
            Files.copy(fromFilePath, toFilePath, REPLACE_EXISTING, COPY_ATTRIBUTES, NOFOLLOW_LINKS);
        } catch (IOException e) {
            LOG.warn(String.format("Failed to copy file from [%s] to [%s]", fromFilePath, toFilePath), e);
        }
    }
}
