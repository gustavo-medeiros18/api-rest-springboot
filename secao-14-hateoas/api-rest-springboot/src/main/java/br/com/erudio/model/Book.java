package br.com.erudio.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String author;

  @Column(name = "launch_date", nullable = false)
  private String launchDate;

  @Column(precision = 65, nullable = false)
  private Double price;

  @Column
  private String title;

  public Long getId() {
    return id;
  }

  public String getAuthor() {
    return author;
  }

  public String getLaunchDate() {
    return launchDate;
  }

  public Double getPrice() {
    return price;
  }

  public String getTitle() {
    return title;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setLaunchDate(String launchDate) {
    this.launchDate = launchDate;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Book book = (Book) o;
    return Objects.equals(id, book.id) &&
        Objects.equals(author, book.author) &&
        Objects.equals(launchDate, book.launchDate) &&
        Objects.equals(price, book.price) &&
        Objects.equals(title, book.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, author, launchDate, price, title);
  }
}
