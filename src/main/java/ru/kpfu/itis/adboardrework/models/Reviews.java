package ru.kpfu.itis.adboardrework.models;


import jakarta.persistence.*;
import kotlin.BuilderInference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Integer score;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User authorId;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipientId;
    @ManyToOne
    @JoinColumn(name = "advert_id")
    private Advert advertId;
}
