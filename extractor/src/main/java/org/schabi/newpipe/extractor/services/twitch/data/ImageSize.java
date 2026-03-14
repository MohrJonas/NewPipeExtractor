package org.schabi.newpipe.extractor.services.twitch.data;

public final class ImageSize {

    private final int imageWidth;
    private final int imageHeight;

    public ImageSize(int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
}
