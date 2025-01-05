package savenow.backend.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import savenow.backend.entity.user.Gender;
import savenow.backend.entity.user.Role;
import savenow.backend.entity.user.User;

public class DummyObject {
    protected static User newUser(String username, String email) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("12341234");

        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .birth("19991204")
                .gender(Gender.MALE)
                .role(Role.USER)
                .build();
    }

    protected User newMockUser(Long id, String username, String email) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("1234");

        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .gender(Gender.MALE)
                .birth("19991204")
                .build();
    }

//    protected Category newCategory(String name, Category parent) {
//        return Category.builder()
//                .name(name)
//                .parent(parent)
//                .build();
//    }
//
//    protected Category newMockCategory(Long id, String name, Category parent) {
//        return Category.builder()
//                .id(id)
//                .name(name)
//                .parent(parent)
//                .build();
//    }
//
//    protected UserCategory newUserCategory(Category category, User user) {
//        return UserCategory.builder()
//                .category(category)
//                .user(user)
//                .build();
//    }
//
//    protected UserCategory newMockUserCategory(Long id, Category category, User user) {
//        return UserCategory.builder()
//                .id(id)
//                .category(category)
//                .user(user)
//                .build();
//    }
}
