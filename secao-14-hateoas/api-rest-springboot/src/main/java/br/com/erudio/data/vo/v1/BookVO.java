package br.com.erudio.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "author", "launchDate", "price", "title"})
public class BookVO extends RepresentationModel<BookVO> implements Serializable {
  @JsonProperty("id")
  @Mapping("id")
  private Long key;
  private String author;
  private String launchDate;
  private Double price;
  private String title;

  public BookVO() {

  }

  public Long getKey() {
    return key;
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

  public void setKey(Long key) {
    this.key = key;
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
    if (!super.equals(o)) return false;

    BookVO bookVO = (BookVO) o;
    return Objects.equals(key, bookVO.key) &&
        Objects.equals(author, bookVO.author) &&
        Objects.equals(launchDate, bookVO.launchDate) &&
        Objects.equals(price, bookVO.price) &&
        Objects.equals(title, bookVO.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), key, author, launchDate, price, title);
  }
}
