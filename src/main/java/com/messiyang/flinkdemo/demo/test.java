package com.messiyang.flinkdemo.demo;



public class test {
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0  ) return "";
        String reg = strs[0];
        for (String str : strs){
            while (!str.startsWith(reg)) {
                if (reg.length() == 1) {
                    return "";
                }
                reg = reg.substring(0, reg.length()-1);
            }
        }
        return reg;
    }

    public static void main(String[] args) {


}
}
