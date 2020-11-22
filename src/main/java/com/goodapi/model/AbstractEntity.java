package com.goodapi.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

/**
 * @author msaritas
 *
 */
@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> implements Persistable<PK>, Serializable {

    private static final long serialVersionUID = 141481953116476081L;

    @Id
    @javax.persistence.Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(final PK id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ClassUtils.getUserClass(obj))) {
            return false;
        }

        @SuppressWarnings("unchecked")
        AbstractEntity<PK> that = (AbstractEntity<PK>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

}
