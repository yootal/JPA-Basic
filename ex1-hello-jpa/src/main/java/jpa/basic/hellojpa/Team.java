package jpa.basic.hellojpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // 반대편의 필드명
    // 객체 연관관계 단방향 + 단방향(2개), 테이블 연관관계(1개)
    // 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.
    // 객체의 두 관계중 하나를 연관관계의 주인으로 지정
    // 주인만이 외래 키 등록, 수정 / 주인이 아닌 쪽은 읽기만 가능
    // 주인은 mappedBy 속성 X
    // 외래 키가 있는 곳을 주인으로 설정 (N)
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }
}
