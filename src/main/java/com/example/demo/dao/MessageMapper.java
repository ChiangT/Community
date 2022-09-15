package com.example.demo.dao;

import com.example.demo.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    //查询当前用户的会话列表，每个会话只返回一条最新的私信
    List<Message> selectConversations(int userId, int offset, int limit);

    //查询当前用户的会话数量
    int selectConversationCount(int userId);

    //查询某个会话的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    //查询当前会话的私信数量
    int selectLetterCount(String conversationId);

    //查询未读私信的数量
    int selectLetterUnreadCount(int userId, String conversationId);

    //发送私信
    int insertMessage(Message message);

    //私信状态更新
    int updateStatus(List<Integer> ids, int status);

    //查询某个topic下最新的通知
    Message selectLatestNotice(int userId, String topic);

    //查询某个topic下通知的数量
    int selectNoticeCount(int userId, String topic);

    //查询某个topic下未读通知的数量
    int selectUnreadNoticeCount(int userId, String topic);

    //查询某个topic的通知列表
    List<Message> selectNotices(int userId, String topic, int offset, int limit);
}
