package org.hibernate.bugs;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
class Owner {

  private Long id;
  private Owned owned;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return this.id;
  }

  @OneToOne(mappedBy = "owner", fetch=FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
  @LazyToOne(value = LazyToOneOption.NO_PROXY)
  public Owned getOwned() {
    return this.owned;
  }

  public void setId(Long newValue) {
    this.id = newValue;
  }

  public void setOwned(Owned owned) {
    this.owned = owned;
    if (owned != null) {
      owned.setOwner(this);
    }
  }
}
