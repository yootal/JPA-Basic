package jpa.basic.hellojpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED) // 조인
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 성능상 이점
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 성능상 이점
@DiscriminatorColumn
public abstract class Item {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

    // 1. 조인 전략
    // 장점: 테이블 정규화, 외래 키 참조 무결성 제약조건 활용가능, 저장공간 효율화
    // 단점: 조회시 조인을 많이 사용, 성능 저하, 조회 쿼리 복잡, 저장시 insert sql 2번 호출

    // 2. 단일 테이블 전략
    // 장점: 조인이 필요X 조회 빠르다, 쿼리가 단순함
    // 단점: 자식 엔티티가 매핑한 컬럼은 모두 null 허용, 단일 테이블에 모든 것을 저장 -> 테이블이 커져서 상황에 따라
    // 조회 성능이 오히려 느려질 수 있다.
    
    // 3. 구현 클래스마다 테이블 전략
    // 장점: 서브 타입을 명확하게 구분해서 처리시 효과적, not null 제약조건 사용 가능
    // 단점: 여러 자식 테이블을 함께 조회할 때 성능이 느림(UNION), 자식 테이블을 통합해서 쿼리하기 어려움
}
