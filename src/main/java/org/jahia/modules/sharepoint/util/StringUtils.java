package org.jahia.modules.sharepoint.util;

public class StringUtils {

    public StringUtils() {}

     public String notEmpty(String str,String msg){
         String result;
         result = (str != null && !str.isEmpty()) ? str : msg;
         return result;
     }
}
