package org.example.olx_config;

public class AppData {
    private final String client_id = "200152";
    private final String client_secret = "9VxduNCWMJJAz3LFkndkuZRRYy26CDlfkUxiKkqBWUjB2VmD";
    private final String scope = "v2 read write";
//    private String refresh_token;
//    private String refresh_token = "db106df5ea05b9dffe1ac0e79a77e4d707cc1d7b";


//    public AppData(String refresh_token) {
//        this.refresh_token = refresh_token;
//    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getScope() {
        return scope;
    }

}
