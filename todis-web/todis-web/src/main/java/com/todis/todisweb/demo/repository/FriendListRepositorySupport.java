package com.todis.todisweb.demo.repository;

import static com.todis.todisweb.demo.domain.QCody.cody;
import static com.todis.todisweb.demo.domain.QFriendList.friendList;
import static com.todis.todisweb.demo.domain.QUser.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todis.todisweb.demo.domain.FriendList;
import com.todis.todisweb.demo.dto.FriendListDetailDto;
import com.todis.todisweb.demo.dto.FriendListDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class FriendListRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public FriendListRepositorySupport(JPAQueryFactory queryFactory) {
        super(FriendList.class);
        this.queryFactory = queryFactory;
    }

    String text = "가깋낗닣딯띻맇밓빟삫싷잏짛찧칳킿팋핗힣";
    String jamoRef = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅇㅈㅉㅊㅋㅌㅍㅎ";

    char[] textArr = text.toCharArray();
    char[] jamoArr = jamoRef.toCharArray();

    int searchLimit = 6;

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
                .select(Projections.constructor(FriendListDto.class, user.name, user.email, user.profileImageUrl))
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

//    public List<FriendListDetailDto> findFriendIdByUserIdDetail(int user_id, int id){
//        val u = user;
//        val fl1 = friendList;
//        val fl2 = friendList;
//        if(id==1){
//            return queryFactory
//                    .select(Projections.constructor(FriendListDetailDto.class,ExpressionUtils.as(
//                            JPAExpressions
//                                    .select(friendList.id)
//                                    .from(friendList)
//                                    .where(friendList.userId.eq(user_id)
//                                            .and(friendList.friendId.eq(u.id))),"id")
//                            , u)
//                    )
//                    .from(u, fl1)
//                    .where(user.id.eq(fl1.friendId)
//                            .and(fl1.friendId.in(
//                                    JPAExpressions
//                                            .selectDistinct(fl1.friendId)
//                                            .from(fl2)
//                                            .where(fl1.userId.eq(user_id)
//                                                    .and(fl2.friendId.eq(fl1.friendId))
//                            ))))
//                    .limit(searchLimit)
//                    .fetch();
//        } else {
//            return queryFactory
//                    .select(Projections.constructor(FriendListDetailDto.class, fl1.id, u.name, u.profileImageUrl,
//                            u.codyImage, u.comment))
//                    .from(u, fl1)
//                    .where(user.id.eq(fl1.friendId)
//                            .and(fl1.friendId.in(
//                                    JPAExpressions
//                                            .selectDistinct(fl2.friendId)
//                                            .from(fl2)
//                                            .where(fl2.userId.eq(user_id)
//                                                    .and(fl2.friendId.eq(fl1.friendId))
//                                            )))
//                            .and(user.id.goe(id))
//                            )
//                    .fetch();
//        }
//    }
}
