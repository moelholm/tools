package com.moelholm.tools.mediaorganizer;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediaOrganizerConfiguration {

    @Value("${daemon.scheduleAsCronExpression}")
    private String scheduleAsCronExpression;

    @Value("${mediafiles.datepattern}")
    private String mediaFilesDatePattern;

    @Value("${mediafiles.mediaFileExtensionsToMatch}")
    private String[] mediaFileExtensionsToMatch;

    @Value("${destination.amountOfMediaFilesIndicatingAnEvent}")
    private int amountOfMediaFilesIndicatingAnEvent;

    @Value("${destination.localeForGeneratingDestinationFolderNames}")
    private Locale locale;

    @Value("${destination.suffixForDestinationFolderOfUnknownEventMediaFiles}")
    private String suffixForDestinationFolderOfUnknownEventMediaFiles;

    @Value("${destination.suffixForDestinationFolderOfMiscMediaFiles}")
    private String suffixForDestinationFolderOfMiscMediaFiles;

    public String getMediaFilesDatePattern() {
        return mediaFilesDatePattern;
    }

    public List<String> getMediaFileExtensionsToMatch() {
        return Arrays.asList(mediaFileExtensionsToMatch);
    }

    public int getAmountOfMediaFilesIndicatingAnEvent() {
        return amountOfMediaFilesIndicatingAnEvent;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getSuffixForDestinationFolderOfUnknownEventMediaFiles() {
        return suffixForDestinationFolderOfUnknownEventMediaFiles;
    }

    public String getSuffixForDestinationFolderOfMiscMediaFiles() {
        return suffixForDestinationFolderOfMiscMediaFiles;
    }

    public String getScheduleAsCronExpression() {
        return scheduleAsCronExpression;
    }

}
