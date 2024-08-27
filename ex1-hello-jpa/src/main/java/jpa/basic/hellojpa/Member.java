package jpa.basic.hellojpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false) // DB 저장 이름
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

//    @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID") // 외래키
    private Team team;

    // 연관관계 메서드
//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;

    /**
     * Java 8 이상
     * private LocalDate testLocalDate;
     * private LocalDateTime testLocalDateTime;
     */

    @Lob
    private String description;
}
