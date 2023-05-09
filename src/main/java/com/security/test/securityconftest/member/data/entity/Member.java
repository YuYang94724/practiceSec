package com.security.test.securityconftest.member.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.test.securityconftest.member.data.enu.MemberRole;
import com.security.test.securityconftest.member.data.enu.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @Column(name = "user_name", unique = true)
    String username;
    @Column
    String password;
    @Column
    String about;
    @Column
    String avatarLink;

    // EnumType.STRING比較推薦使用， EnumType.ORDINAL是返回該index(從0開始)的值
    @Enumerated(value = EnumType.STRING)
    @Column
    MemberRole role;

    @Enumerated(value = EnumType.STRING)
    @Column
    MemberStatus status;
    //下方為使用配置的方式
    @CreatedBy
    @Column
    String createdBy;

    @JsonFormat(pattern = "yyyyMMdd")
    @CreatedDate
    @Column
    LocalDate createdDate;

    @LastModifiedBy
    @Column
    String lastModifiedBy;

    @JsonFormat(pattern = "yyyyMMdd")
    @LastModifiedDate
    @Column
    LocalDate lastModifiedDate;
}
