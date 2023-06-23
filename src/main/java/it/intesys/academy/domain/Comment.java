package it.intesys.academy.domain;

import jakarta.persistence.*;
@Entity
@Table(name = "Comment")
public class Comment {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(name="id")
        private Integer id;


        @Column(name="text")
        private String text;

        @Column(name="author")
        private String author;

        @ManyToOne
        @JoinColumn(name="issueid")
        private Issue issueid;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public Issue getIssueid() {
                return issueid;
        }

        public void setIssueid(Issue issueid) {
                this.issueid = issueid;
        }
}
