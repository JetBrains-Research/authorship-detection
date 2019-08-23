package com.pengrad.telegrambot.model.request;

/**
 * stas
 * 1/12/16.
 */
public class InlineQueryResultVideo extends InlineQueryResult<InlineQueryResultVideo> {

    public static final String MIME_TEXT_HTML = "text/html";
    public static final String MIME_VIDEO_MP4 = "video/mp4";

    private String video_url;
    private String mime_type;
    private String thumb_url;
    private String title;

    private String caption;
    private Integer video_width;
    private Integer video_height;
    private Integer video_duration;
    private String description;

    public InlineQueryResultVideo(String id, String videoUrl, String mimeType, String messageText, String thumbUrl, String title) {
        this(id, videoUrl, mimeType, new InputTextMessageContent(messageText), thumbUrl, title);
    }

    public InlineQueryResultVideo(String id, String videoUrl, String mimeType, InputMessageContent inputMessageContent, String thumbUrl, String title) {
        super("video", id);
        this.video_url = videoUrl;
        this.mime_type = mimeType;
        this.thumb_url = thumbUrl;
        this.title = title;
        inputMessageContent(inputMessageContent);
    }

    public InlineQueryResultVideo caption(String caption) {
        this.caption = caption;
        return this;
    }

    public InlineQueryResultVideo videoWidth(Integer videoWidth) {
        this.video_width = videoWidth;
        return this;
    }

    public InlineQueryResultVideo videoHeight(Integer videoHeight) {
        this.video_height = videoHeight;
        return this;
    }

    public void videoDuration(Integer videoDuration) {
        this.video_duration = videoDuration;
    }

    public InlineQueryResultVideo description(String description) {
        this.description = description;
        return this;
    }
}
