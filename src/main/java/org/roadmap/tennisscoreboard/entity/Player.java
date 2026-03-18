package org.roadmap.tennisscoreboard.entity;

import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players",
        check = @CheckConstraint(constraint = "LENGTH(name) >= 2"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, updatable = false, length = 20)
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
