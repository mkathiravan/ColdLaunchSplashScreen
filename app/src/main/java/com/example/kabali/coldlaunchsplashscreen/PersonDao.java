package com.example.kabali.coldlaunchsplashscreen;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface PersonDao {

    @Query("SELECT * FROM person")
    List<Person> getAll();


    @Insert
    void insert(Person person);


    @Delete
    void delete(Person person);


    @Update
    void update(Person person);
}
