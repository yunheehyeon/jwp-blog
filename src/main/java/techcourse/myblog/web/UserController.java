package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.SnsInfoRepository;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.UserDto;
import techcourse.myblog.domain.UserRepository;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SnsInfoRepository snsInfoRepository;

    @GetMapping("/signup")
    public String showSignUpPage() {
        // TODO: 로그인 된 유저인지 체크
        return "signup";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        // TODO: 로그인 된 유저인지 체크
        return "login";
    }

    @PostMapping("/login")
    public String showLoginPage(UserDto userDto, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());

        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            session.setAttribute("userId", user.getId());

            return "redirect:/";
        }

        return "";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            session.removeAttribute("userId");
        }

        return "redirect:/";
    }

    @PostMapping("/users")
    public String addUser(UserDto userDto) {
        User user = userRepository.save(userDto.toEntity());

        return "redirect:/login";
    }

    @GetMapping("/users")
    public String showUserListPage(Model model) {
        List<User> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(user);
        }

        model.addAttribute("users", users.stream()
                .map(user -> UserDto.fromWithoutPassword(user))
                .collect(toList()));

        System.out.println(users);

        return "user-list";
    }

    @GetMapping("users/{id}/mypage")
    public String showMypage(@PathVariable final long id, Model model) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            UserDto userDto = UserDto.fromWithoutPassword(user.get());
            model.addAttribute("userInfo", userDto);

            return "mypage";
        }

        return "redirect:/";
    }

    @GetMapping("/users/{id}/mypage-edit")
    public String showMypageEdit(@PathVariable final long id, HttpSession session, Model model) {
        // TODO: 로그인되었는지 확인 (아니면 메인으로)

        Object userId = session.getAttribute("userId");
        if (userId == null || id != (long) userId) {
            return "redirect:/";
        }

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            UserDto userDto = UserDto.fromWithoutPassword(user.get());
            model.addAttribute("userInfo", userDto);
        }

        return "mypage-edit";
    }

    @PutMapping("/users/{id}/mypage-edit")
    @Transactional
    public String updateUser(@PathVariable final long id, HttpSession session, UserDto userDto) {
        // TODO: 로그인되었는지 확인 (아니면 메인으로)

        Object userId = session.getAttribute("userId");
        if (userId == null || id != (long) userId) {
            return "redirect:/";
        }

        Optional<User> maybeUser = userRepository.findById(id);
        if (maybeUser.isPresent()) {
            UserDto findUserDto = UserDto.from(maybeUser.get());
            findUserDto.updateUserInfo(userDto);
            snsInfoRepository.deleteByUserId(id);
            userRepository.save(findUserDto.toEntity());
        }

        return "redirect:/users/" + id + "/mypage";
    }

    @DeleteMapping("/users/{id}/mypage-edit")
    public String deleteUser(@PathVariable final long id, HttpSession session, UserDto userDto) {
        // TODO: 로그인되었는지 확인 (아니면 메인으로)

        Object userId = session.getAttribute("userId");
        if (userId == null || id != (long) userId) {
            return "redirect:/";
        }

        userRepository.deleteById(id);

        return "redirect:/logout";
    }
}
