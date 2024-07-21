package by.svistunovvv.accountManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_account")
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private Double amount;
    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    private User user;
    @Column(name = "is_block")
    private boolean isBlock = false;
}
