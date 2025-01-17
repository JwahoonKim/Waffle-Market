package com.wafflestudio.team03server.core.user.service

import com.wafflestudio.team03server.core.chat.api.dto.ReceivedChatMessage
import com.wafflestudio.team03server.common.Exception400
import com.wafflestudio.team03server.common.Exception403
import com.wafflestudio.team03server.common.Exception404
import com.wafflestudio.team03server.common.Exception409
import com.wafflestudio.team03server.core.chat.service.ChatService
import com.wafflestudio.team03server.core.neighbor.entity.NeighborPost
import com.wafflestudio.team03server.core.neighbor.repository.NeighborPostRepository
import com.wafflestudio.team03server.core.neighbor.service.NeighborPostService
import com.wafflestudio.team03server.core.trade.api.request.CreatePostRequest
import com.wafflestudio.team03server.core.trade.service.TradePostService
import com.wafflestudio.team03server.core.user.api.request.EditLocationRequest
import com.wafflestudio.team03server.core.user.api.request.EditPasswordRequest
import com.wafflestudio.team03server.core.user.api.request.EditUsernameRequest
import com.wafflestudio.team03server.core.user.entity.Coordinate
import com.wafflestudio.team03server.core.user.entity.User
import com.wafflestudio.team03server.core.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKTReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@SpringBootTest
internal class UserServiceImplTest @Autowired constructor(
    val userRepository: UserRepository,
    val userService: UserService,
    val neighborPostRepository: NeighborPostRepository,
    val neighborPostService: NeighborPostService,
    val tradePostService: TradePostService,
    val chatService: ChatService,
    val passwordEncoder: PasswordEncoder
) {

    @Test
    fun 유저_정보_조회_성공() {
        //given
        val user = createUser("user1", "a@naver.com", "1234", "송도동")
        val savedUser = userRepository.save(user)
        //when
        val userResponse = userService.getProfile(savedUser.id)
        //then
        val findUser = userRepository.findByIdOrNull(savedUser.id) ?: throw Exception404("")
        assertThat(findUser.username).isEqualTo(userResponse.username)
        assertThat(findUser.email).isEqualTo(userResponse.email)
        assertThat(findUser.location).isEqualTo(userResponse.location)
        assertThat(findUser.temperature).isEqualTo(userResponse.temperature)
        assertThat(findUser.imgUrl).isEqualTo(userResponse.imgUrl)
        assertThat(findUser.createdAt).isEqualTo(userResponse.createdAt)
        assertThat(findUser.modifiedAt).isEqualTo(userResponse.modifiedAt)
    }

    @Test
    fun 유저_정보_조회_실패() {
        //given

        //when
        val exception = assertThrows(Exception404::class.java) {
            userService.getProfile(12108)
        }
        //then
        assertThat(exception.message).isEqualTo("사용자를 찾을 수 없습니다.")
    }

    @Test
    fun 유저네임_수정_성공() {
        //given
        val user = createUser("user1", "a@naver.com", "1234", "송도동")
        val savedUser = userRepository.save(user)
        //when
        userService.editUsername(savedUser.id, EditUsernameRequest("Edited"))
        //then
        val findUser = userRepository.findByIdOrNull(savedUser.id) ?: throw Exception404("")
        assertThat(findUser.username).isEqualTo("Edited")
    }

    @Test
    fun 유저네임_수정_실패() {
        //given
        val user1 = createUser("user1", "a@naver.com", "1234", "송도동")
        val user2 = createUser("user2", "b@naver.com", "1234", "송도동")
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)
        //when
        val exception = assertThrows(Exception409::class.java) {
            userService.editUsername(savedUser1.id, EditUsernameRequest("user2"))
        }
        //then
        assertThat(exception.message).isEqualTo("이미 존재하는 유저네임 입니다.")
    }

    @Test
    fun 주소_수정_성공() {
        //given
        val user = createUser("user1", "a@naver.com", "1234", "송도동")
        val savedUser = userRepository.save(user)
        //when
        userService.editLocation(savedUser.id, EditLocationRequest("Edited", Coordinate(37.0, 127.0)))
        //then
        val findUser = userRepository.findByIdOrNull(savedUser.id) ?: throw Exception404("")
        assertThat(findUser.location).isEqualTo("Edited")
    }

    @Test
    fun 비밀번호_수정_성공() {
        //given
        val user = createUser("user1", "a@naver.com", "1234", "송도동")
        user.password = passwordEncoder.encode(user.password)
        val savedUser = userRepository.save(user)
        //when
        userService.editPassword(
            savedUser.id,
            EditPasswordRequest("1234", "Edited123!", "Edited123!")
        )
        //then
        val findUser = userRepository.findByIdOrNull(savedUser.id) ?: throw Exception404("")
        assertThat(passwordEncoder.matches("Edited123!", findUser.password)).isTrue
    }

    @Test
    fun 비밀번호_수정_실패_비밀번호_틀림() {
        //given
        val user = createUser("user1", "a@naver.com", "1234", "송도동")
        user.password = passwordEncoder.encode(user.password)
        val savedUser = userRepository.save(user)
        //when
        val exception = assertThrows(Exception403::class.java) {
            userService.editPassword(
                savedUser.id,
                EditPasswordRequest("wrongPw", "Edited123!", "Edited123!")
            )
        }
        //then
        assertThat(exception.message).isEqualTo("기존 비밀번호가 틀렸습니다.")
    }

    @Test
    fun 비밀번호_수정_실패_새비밀번호_불일치() {
        //given
        val user = createUser("user1", "a@naver.com", "1234", "송도동")
        user.password = passwordEncoder.encode(user.password)
        val savedUser = userRepository.save(user)
        //when
        val exception = assertThrows(Exception400::class.java) {
            userService.editPassword(
                savedUser.id,
                EditPasswordRequest("1234", "Edited123!", "NotIdentical!")
            )
        }
        //then
        assertThat(exception.message).isEqualTo("비밀번호가 일치하지 않습니다.")
    }

    @Test
    fun 글_구매_내역_조회_성공() {
        // given
        val user1 = createUser("user1", "abc1@naver.com", "1234", "관악구")
        val user2 = createUser("user2", "abc2@naver.com", "1234", "관악구")
        val user3 = createUser("user3", "abc3@naver.com", "1234", "관악구")
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)
        val savedUser3 = userRepository.save(user3)

        val request1 = CreatePostRequest("title1", "String1", 10000, mutableListOf("img1", "img2"))
        val request2 = CreatePostRequest("title2", "String2", 10000, mutableListOf("img1", "img2"))
        val request3 = CreatePostRequest("title3", "String3", 10000, mutableListOf("img1", "img2"))
        val post1 = tradePostService.createPost(savedUser1.id, request1)
        val post2 = tradePostService.createPost(savedUser1.id, request2)
        val post3 = tradePostService.createPost(savedUser1.id, request3)

        // 예약 + 구매 확정
        chatService.startChat(savedUser2.id, post1.postId)
        chatService.startChat(savedUser2.id, post2.postId)
        chatService.startChat(savedUser3.id, post3.postId)
        tradePostService.changeBuyer(savedUser1.id, savedUser2.id, post1.postId)
        tradePostService.changeBuyer(savedUser1.id, savedUser2.id, post2.postId)
        tradePostService.changeBuyer(savedUser1.id, savedUser3.id, post3.postId)
        tradePostService.confirmTrade(savedUser1.id, post1.postId)
        tradePostService.confirmTrade(savedUser1.id, post2.postId)
        tradePostService.confirmTrade(savedUser1.id, post3.postId)

        // when
        val buyTradePosts = userService.getBuyTradePosts(savedUser2.id)

        // then
        assertThat(buyTradePosts.posts.size).isEqualTo(2)
        assertThat(buyTradePosts.posts[0].title).isEqualTo(post1.title)
    }

    @Test
    fun 글_판매_내역_조회_성공() {
        // given
        val user1 = createUser("user1", "abc1@naver.com", "1234", "관악구")
        val user2 = createUser("user2", "abc2@naver.com", "1234", "관악구")
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)

        val request1 = CreatePostRequest("title1", "String1", 10000, mutableListOf("img1", "img2"))
        val request2 = CreatePostRequest("title2", "String2", 10000, mutableListOf("img1", "img2"))
        val request3 = CreatePostRequest("title3", "String3", 10000, mutableListOf("img1", "img2"))
        val post1 = tradePostService.createPost(savedUser1.id, request1)
        val post2 = tradePostService.createPost(savedUser1.id, request2)
        val post3 = tradePostService.createPost(savedUser2.id, request3)

        // when
        val sellTradePosts = userService.getSellTradePosts(savedUser1.id, savedUser1.id)

        // then
        assertThat(sellTradePosts.posts.size).isEqualTo(2)
        assertThat(sellTradePosts.posts[0].title).isEqualTo(post1.title)
    }

    @Test
    fun 찜한_채팅목록_내역_조회_성공() {
        // given
        val user1 = createUser("user1", "abc1@naver.com", "1234", "관악구")
        val user2 = createUser("user2", "abc2@naver.com", "1234", "관악구")
        val user3 = createUser("user3", "abc3@naver.com", "1234", "관악구")
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)
        val savedUser3 = userRepository.save(user3)

        val request1 = CreatePostRequest("title1", "String1", 10000, mutableListOf("img1", "img2"))
        val request2 = CreatePostRequest("title2", "String2", 20000, mutableListOf("img1", "img2"))
        val request3 = CreatePostRequest("title3", "String3", 30000, mutableListOf("img1", "img2"))
        val post1 = tradePostService.createPost(savedUser2.id, request1)
        val post2 = tradePostService.createPost(savedUser2.id, request2)
        val post3 = tradePostService.createPost(savedUser3.id, request3)

        tradePostService.likePost(savedUser1.id, post1.postId)
        tradePostService.likePost(savedUser1.id, post2.postId)

        // when
        val likeTradePosts = userService.getLikeTradePosts(savedUser1.id)

        // then
        assertThat(likeTradePosts.posts.size).isEqualTo(2)
        assertThat(likeTradePosts.posts[0].title).isEqualTo(post1.title)
        assertThat(likeTradePosts.posts[0].isLiked).isTrue
        assertThat(likeTradePosts.posts[1].isLiked).isTrue
    }

    @Test
    fun 내_채팅목록_내역_조회_성공() {
        // given
        val user1 = createUser("user1", "abc1@naver.com", "1234", "관악구")
        val user2 = createUser("user2", "abc2@naver.com", "1234", "관악구")
        val user3 = createUser("user3", "abc3@naver.com", "1234", "관악구")
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)
        val savedUser3 = userRepository.save(user3)

        val request1 = CreatePostRequest("title1", "String1", 10000, mutableListOf("img1", "img2"))
        val request2 = CreatePostRequest("title2", "String2", 20000, mutableListOf("img1", "img2"))
        val post1 = tradePostService.createPost(savedUser1.id, request1)
        val post2 = tradePostService.createPost(savedUser2.id, request2)
        val chat1 = chatService.startChat(savedUser2.id, post1.postId)
        val chat2 = chatService.startChat(savedUser3.id, post1.postId)
        val chat3 = chatService.startChat(savedUser1.id, post2.postId)
        val chatMessage = ReceivedChatMessage(chat1.roomUUID, savedUser2.id, "안녕하세요!", LocalDateTime.now())
        val chatMessage2 = ReceivedChatMessage(chat1.roomUUID, savedUser1.id, "안녕하세요?", LocalDateTime.now())
        val chatMessage3 = ReceivedChatMessage(chat2.roomUUID, savedUser1.id, "반갑습니다.", LocalDateTime.now())
        chatService.saveMessage(chatMessage)
        chatService.saveMessage(chatMessage2)
        chatService.saveMessage(chatMessage3)

        // when
        val myChats = userService.getMyChats(savedUser1.id)

        // then
        assertThat(myChats.chats.size).isEqualTo(3)
    }

    @Test
    fun 좋아요한_글_조회_성공() {
        // given
        // 유저 생성
        val user1 = createUser("user1", "abc1@naver.com", "1234", "관악구")
        val user2 = createUser("user2", "abc2@naver.com", "1234", "관악구")
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)

        // 글 생성
        val post1 = createNeighborPost("내용", user1)
        val post2 = createNeighborPost("내용", user1)
        val post3 = createNeighborPost("내용", user1)
        val post4 = createNeighborPost("내용", user1)
        val post5 = createNeighborPost("내용", user1)
        val savedPost1 = neighborPostRepository.save(post1)
        val savedPost2 = neighborPostRepository.save(post2)
        val savedPost3 = neighborPostRepository.save(post3)
        val savedPost4 = neighborPostRepository.save(post4)
        val savedPost5 = neighborPostRepository.save(post5)

        // 좋아요 설정
        neighborPostService.likeOrUnlikeNeighborPost(savedUser2.id, savedPost1.id)
        neighborPostService.likeOrUnlikeNeighborPost(savedUser2.id, savedPost3.id)
        neighborPostService.likeOrUnlikeNeighborPost(savedUser2.id, savedPost3.id)
        neighborPostService.likeOrUnlikeNeighborPost(savedUser2.id, savedPost2.id)

        // when
        val pageable = PageRequest.of(0, 5)
        val likedPosts = userService.getLikeNeighborhoodPosts(savedUser2.id, pageable)

        // then
        assertThat(likedPosts.posts.size).isEqualTo(2)
        assertThat(likedPosts.posts.map { it.postId }).contains(savedPost2.id, savedPost1.id)
        assertThat(likedPosts.posts.map { it.postId }).doesNotContain(savedPost3.id)
    }

    private fun createUser(username: String, email: String, password: String, location: String): User {
        return User(username, email, password, location, WKTReader().read("POINT(1.0 1.0)") as Point)
    }

    private fun createNeighborPost(content: String, user: User): NeighborPost {
        return NeighborPost(content, user)
    }
}
