package jpa.basic.hellojpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // 반대편의 필드명
    private List<Member> members = new ArrayList<>();

//    일대다
//    @OneToMany
//    @JoinColumn(name = "TEAM_ID")
//    private List<Member> members = new ArrayList<>();

//    연관관계 메서드
//    public void addMember(Member member) {
//        member.setTeam(this);
//        members.add(member);
//    }
}
