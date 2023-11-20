package org.academiadecodigo.javabank.persistence.daos;

import jdk.internal.icu.text.NormalizerBase;
import org.academiadecodigo.javabank.model.Model;

import java.awt.*;

public interface DAO <T extends Model> {
    List<T> findAll();

    T findById(Integer id);

    T saveOrUpdate(T model);

    void delete(Integer id);


}
