package com.democom.news.util;

public class Queries {

    public static final String getSubbedNews = "SELECT new com.democom.news.dto.NewsResponseDto(n.title ,n.category ,n.description ,n.date ,n.author.name) " +
            "FROM News n " +
            "INNER JOIN Subscription s " +
            "ON n.author = s.author " +
            "WHERE s.stateSubscription = TRUE " +
            "AND s.reader = ?1";

    public static final String getSubbedNewsByCategory = "SELECT new com.democom.news.dto.NewsResponseDto(n.title ,n.category ,n.description ,n.date ,n.author.name) " +
            "FROM News n " +
            "INNER JOIN Subscription s " +
            "ON n.author = s.author " +
            "WHERE s.stateSubscription = TRUE " +
            "AND s.reader = ?1 " +
            "AND n.category = ?2 ";

    public static final String getNewsByDataBetween = "SELECT new com.democom.news.dto.NewsResponseDto(n.title ,n.category ,n.description ,n.date ,n.author.name) " +
            "FROM News n " +
            "WHERE n.date " +
            "BETWEEN  ?1 " +
            "AND ?2 ";


    public static  final  String getRoleByName = "SELECT new com.democom.news.entity.Role(r.name) " +
            "FROM Role r " +
            "WHERE r.name = ?1 ";

}
