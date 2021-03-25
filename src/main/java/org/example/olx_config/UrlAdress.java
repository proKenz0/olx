package org.example.olx_config;

public enum UrlAdress {
    TOKEN ("https://www.olx.ua/api/open/oauth/token"),
    USER ("https://www.olx.ua/api/partner/users/me"),
    THREADS ("https://www.olx.ua/api/partner/threads");

    private String url;
    private static final String MESSAGES_START = "https://www.olx.ua/api/partner/threads/";
    private static final String MESSAGES_END = "/messages";
    private static final String THREAD_COMMAND_START = "https://www.olx.ua/api/partner/threads/";
    private static final String THREAD_COMMAND_END = "/commands";

    UrlAdress(String url) {
        this.url = url;
    }

    public static String getMessagesUrl(String id){
        return MESSAGES_START + id + MESSAGES_END;
    }
    public static String getCommandUrl(String id){
        return THREAD_COMMAND_START + id + THREAD_COMMAND_END;
    }
    public static String getMessagesUrl(String threadId, String messageId){
        return MESSAGES_START + threadId + MESSAGES_END + messageId;
    }

    @Override
    public String toString() {
        return url;
    }



}
