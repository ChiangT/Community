package com.example.demo.util;

import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    private static final String REPLACEMENT = "***";
    private TrieNode root = new TrieNode();

    @PostConstruct
    public void init(){
        try(
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ){
            String keywords;
            while((keywords=reader.readLine()) != null){
                this.addKeyword(keywords);
            }

        } catch (IOException e){
            logger.error("加载敏感词文件失败：" + e.getMessage());
        }
    }

    private void addKeyword(String keyword){
        TrieNode cur = root;
        for(int i=0; i<keyword.length(); i++){
            char ch = keyword.charAt(i);
            TrieNode subNode = cur.getSubNode(ch);
            if(subNode == null){
                subNode = new TrieNode();
                cur.addSubNode(ch, subNode);
            }
            cur = subNode;
            if(i == keyword.length()-1){
                cur.setWordEnd(true);
            }
        }
    }

    public String filter(String text){
        TrieNode cur = root;
        int start = 0;
        int end = 0;
        StringBuilder sb = new StringBuilder();
        while(end < text.length()){
            char ch = text.charAt(end);
            if(isSymbol(ch)){
                if(cur == root){
                    sb.append(ch);
                    start++;
                }
                end++;
                continue;
            }
            cur = cur.getSubNode(ch);
            if(cur == null){
                sb.append(text.charAt(start));
                start++;
                end = start;
                cur = root;
            } else if(cur.isWordEnd()){
                sb.append(REPLACEMENT);
                end++;
                start = end;
                cur = root;
            } else {
                end++;
            }
        }
        sb.append(text.substring(start));
        return sb.toString();
    }

    private boolean isSymbol(char ch){
        return !CharUtils.isAsciiAlphanumeric(ch) && (ch < 0x2E80 || ch > 0x9FFF);
    }

    private class TrieNode{
        private boolean isWordEnd = false;
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isWordEnd() {
            return isWordEnd;
        }

        public void setWordEnd(boolean wordEnd) {
            isWordEnd = wordEnd;
        }

        //添加子节点
        public void addSubNode(Character ch, TrieNode node){
            subNodes.put(ch, node);
        }

        //获取子节点
        public TrieNode getSubNode(Character ch){
            return subNodes.get(ch);
        }
    }



}
