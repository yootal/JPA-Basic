package jpa.basic.hellojpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
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

//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    /**
     * Java 8 이상
     * private LocalDate testLocalDate;
     * private LocalDateTime testLocalDateTime;
     */

    @Lob
    private String description;
}
