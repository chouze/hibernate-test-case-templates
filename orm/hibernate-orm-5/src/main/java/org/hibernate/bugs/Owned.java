package org.hibernate.bugs;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
class Owned {
  private Long id;
  private Owner owner;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return this.id;
  }

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  public Owner getOwner() {
    return this.owner;
  }

  public void setId(Long newValue) {
    this.id = newValue;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }
}