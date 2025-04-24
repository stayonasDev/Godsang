package godsang.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
public class Member extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private Long id;

    @Column(name = "user_id")
    private String userId;
    private String password;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String name;

}
