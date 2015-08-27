package br.com.webmobidata.findworkers.utils;

import java.util.ArrayList;
import java.util.List;

public class FacebookUtils {

    public static List<String> getPermissionsReadFacebook(){
        List<String> permissions = new ArrayList<>();
        permissions.add("user_about_me");
        permissions.add("user_birthday");
        permissions.add("user_friends");
        permissions.add("user_location");
        permissions.add("user_status");
        permissions.add("user_tagged_places");
        return permissions;
    }

    public static List<String> getPermissionsPublishFacebook(){
        List<String> permissions = new ArrayList<>();
        permissions.add("publish_actions");
        return permissions;
    }

}
