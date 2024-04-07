package com.sarop.saropbackend.note.entity;

import com.sarop.saropbackend.restapi.entity.Map;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgis.Point;


@Data
@Builder
@Entity
@Table(name="note")
@AllArgsConstructor
@NoArgsConstructor
public class Note  {
    @Id
    @Column()

    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable=false)
    private String title;
    @Column(nullable=false)
    private String comment;

    @Column(columnDefinition = "Geometry")
    private Point point;

    @ManyToOne
    private Map map;



}
