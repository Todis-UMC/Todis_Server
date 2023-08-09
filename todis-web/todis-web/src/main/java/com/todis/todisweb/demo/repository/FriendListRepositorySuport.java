package com.todis.todisweb.demo.repository;

import static com.todis.todisweb.demo.domain.QFriendList.friendList;
import static com.todis.todisweb.demo.domain.QUser.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todis.todisweb.demo.domain.FriendList;
import com.todis.todisweb.demo.dto.FriendListDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class FriendListRepositorySuport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public FriendListRepositorySuport(JPAQueryFactory queryFactory) {
        super(FriendList.class);
        this.queryFactory = queryFactory;
    }

    String text = "가깋낗닣딯띻맇밓빟삫싷잏짛찧칳킿팋핗힣";
    String jamoRef = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅇㅈㅉㅊㅋㅌㅍㅎ";

    char[] textArr = text.toCharArray();
    char[] jamoArr = jamoRef.toCharArray();

    public List<FriendListDto> searchFriendList(int user_id, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        char targetChar = keyword.charAt(keyword.length() - 1);
        char endChar = 0;
        if (targetChar <= 12622) {
            for (int i = 0; i < jamoArr.length; i++) {
                if (targetChar == jamoArr[i]) {
                    log.info("Search Friend from " + targetChar + " to " + textArr[i + 1]);
                    endChar = textArr[i + 1];
                }
            }
        } else {
            // 입력값의 끝 문자가 가...힣 인 경우
            for (int i = 0; i < text.length(); i++) {
                if(targetChar >= textArr[i] && targetChar < textArr[i + 1]) {
                    log.info("Search Friend from " + targetChar + " to " + textArr[i + 1]);
                    endChar = textArr[i + 1];
                }
            }
        }
        String endKeyword = keyword.substring(0, keyword.length() - 1) + endChar;
        builder.and(user.name.loe(endKeyword));
        log.info("Search Friend from " + keyword + " to " + endKeyword);
        return queryFactory
                .select(Projections.constructor(FriendListDto.class, user.name, user.profileImageUrl))
                .from(user)
                .where(user.id.in(
                        JPAExpressions
                                .select(friendList.friendId)
                                .from(friendList)
                                .where(friendList.userId.eq(user_id))
                )
                        .and(user.name.goe(keyword))
                        .and(builder))
                .fetch();
    }
}