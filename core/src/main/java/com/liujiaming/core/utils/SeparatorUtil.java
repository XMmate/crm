package com.liujiaming.core.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.liujiaming.core.common.Const;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 字符串分割
 */
public class SeparatorUtil {

    public static Set<Integer> toSet(String tagStr){
        Set<Integer> tag=new HashSet<>();
        if(null==tagStr){
            return tag;
        }
        for (String str : tagStr.split(Const.SEPARATOR)) {
            if(StrUtil.isEmpty(str)){
                continue;
            }
            tag.add(Integer.valueOf(str));
        }
        return tag;
    }

    public static Set<Long> toLongSet(String tagStr){
        Set<Long> tag=new HashSet<>();
        if(StrUtil.isEmpty(tagStr)){
            return tag;
        }
        for (String str : tagStr.split(Const.SEPARATOR)) {
            if(StrUtil.isEmpty(str)){
                continue;
            }
            tag.add(Long.valueOf(str));
        }
        return tag;
    }



    public static String fromSet(Collection<Integer> tag){
        if(CollectionUtil.isEmpty(tag)){
            return "";
        }
        StringBuilder sb=new StringBuilder(Const.SEPARATOR);
        for (Integer integer : tag) {
            if(integer==null){
                continue;
            }
            sb.append(integer).append(Const.SEPARATOR);
        }
        return sb.toString();
    }

    public static String fromLongSet(Collection<Long> tag){
        if(CollectionUtil.isEmpty(tag)){
            return "";
        }
        StringBuilder sb=new StringBuilder(Const.SEPARATOR);
        for (Long integer : tag) {
            if(integer==null){
                continue;
            }
            sb.append(integer).append(Const.SEPARATOR);
        }
        return sb.toString();
    }

    public static String fromString(String tagStr){
        if(StrUtil.isEmpty(tagStr)){
            return "";
        }
        StringBuilder sb=new StringBuilder();
        if(!tagStr.substring(0,1).equals(Const.SEPARATOR)){
            sb.append(Const.SEPARATOR);
        }
        sb.append(tagStr);
        if(!tagStr.substring(tagStr.length()-1).equals(Const.SEPARATOR)){
            sb.append(Const.SEPARATOR);
        }
        return sb.toString();
    }
}
