package jpa.basic.hellojpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID") // 외래키
    private Team team;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVOLITE_FOODS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressesHistory = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();


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

    /**
     * Java 8 이상
     * private LocalDate testLocalDate;
     * private LocalDateTime testLocalDateTime;
     */

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//    @Lob
//    private String description;

//    연관관계 메서드
//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }
}
