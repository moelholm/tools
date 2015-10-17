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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MediaOrganizer {

    private static final String DATE_PATTERN_IN_MEDIA_FILE_NAMES = "yyyy-MM-dd HH.mm.ss";

    private static final Logger LOG = LoggerFactory.getLogger(MediaOrganizer.class);

    @Async
    public void undoFlatMess(Path from, Path to) {
        LOG.info("Copying files from [{}] to [{}]", from, to);

        mediaFilesFromSourcePath(from) //
                .filter(jpegOrMov())//
                .collect(groupByYearMonthDayString()) //
                .forEach((folderName, mediaFilePaths) -> {
                    LOG.info("Processing folder [{}] which has [{}] media files", folderName, mediaFilePaths.size());
                    if (mediaFilePaths.size() > 5) {

                    } else {

                    }
                });
    }

    private String toYearMonthDayString(Path path) {
        Date date = parseDateFromPathName(path);

        if (date == null) {
            return "unknown";
        }

        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);

        int year = dateCal.get(Calendar.YEAR);
        String month = new DateFormatSymbols().getMonths()[dateCal.get(Calendar.MONTH) - 1];
        int day = dateCal.get(Calendar.DAY_OF_MONTH);

        return String.format("%s - %s - %s", year, month, day);
    }

    private Date parseDateFromPathName(Path path) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_IN_MEDIA_FILE_NAMES);
        try {
            return sdf.parse(path.getFileName().toString());
        } catch (ParseException e) {
            LOG.warn("Failed to extract date from {}", path, e);
            return null;
        }
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

    private Predicate<? super Path> jpegOrMov() {
        return p -> p.toString().toLowerCase().endsWith(".jpg") || p.toString().toLowerCase().endsWith(".mov");
    }

    private void move(Path fromFile, Path toFile) {
        try {
            Files.copy(fromFile, toFile, REPLACE_EXISTING, COPY_ATTRIBUTES, NOFOLLOW_LINKS);
        } catch (IOException e) {
            LOG.warn(String.format("Failed to copy file from [%s] to [%s]", fromFile, toFile), e);
        }
    }
}
