package com.bawp.nodo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "nodo_table")
public class NoDo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "nodo_col")
    private String noDo;

    public NoDo(@NonNull String noDo) {
        this.noDo = noDo;
    }

    public String getNoDo() {
        return noDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoDo(@NonNull String noDo) {
        this.noDo = noDo;
    }
}
