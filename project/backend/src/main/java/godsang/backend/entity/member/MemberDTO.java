package godsang.backend.entity.member;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime joinDate;
    private LocalDateTime modifiedDate;
    private String role;

    public Map<String, Object> getDataMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("name", name);
        map.put("email", email);
        map.put("role", role);
        return map;
    }

    public MemberDTO(Member member) {
        this.userId = member.getUserId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.joinDate = member.getCreateDate();
        this.modifiedDate = member.getLastModifiedDate();
        this.role = member.getRole();
    }
}
