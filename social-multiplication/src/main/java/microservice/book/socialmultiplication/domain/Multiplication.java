package microservice.book.socialmultiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by r.spasojevic on 2/14/2018.
 */

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
@Entity
public final class Multiplication {

    @Id
    @GeneratedValue
    @Column(name = "MULTIPLICATION_ID")
    private Long id;
    private final int factorA;
    private final int factorB;

   Multiplication() {
       this(0, 0);
   }

}
