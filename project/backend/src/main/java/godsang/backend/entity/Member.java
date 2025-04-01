package godsang.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Member extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "m_id")
    private Long id;

    @Column(name = "member_id")
    private String memberId;
    private String password;
    private String email;
    private String phone_number;
    private String name;

}
