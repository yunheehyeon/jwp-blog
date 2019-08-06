package techcourse.myblog.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import techcourse.myblog.domain.comment.CommentDto;
import techcourse.myblog.domain.user.UserDto;
import techcourse.myblog.domain.user.UserInfoDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestCommentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getComment() {
        webTestClient.get().uri("/comments/1")
                .exchange()
                .expectStatus().isOk();
//                .expectBody()
//                .consumeWith(response -> assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void createComment() {
//        UserDto userDto = UserInfoDto.builder().email("test@test.com").name("미스터코").build();
//
//        CommentDto commentDto = CommentDto.builder().contents("test").author(userDto).build();
//
//        webTestClient.post().uri("/comments/1")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .body(Mono.just(commentDto), CommentDto.class)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//                .expectBody()
//                .jsonPath("$.name").isNotEmpty()
//                .jsonPath("$.name").isEqualTo("test-webclient-repository");
    }

}