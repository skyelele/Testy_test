package com.bawp.nodo.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.bawp.nodo.util.NoDoRepository;

import java.util.List;

public class NoDoViewModel extends AndroidViewModel {
    private NoDoRepository noDoRepository;
    private LiveData<List<NoDo>> allNoDos;

    public NoDoViewModel(@NonNull Application application) {
        super(application);
        noDoRepository = new NoDoRepository(application);
        allNoDos = noDoRepository.getAllNoDos();
    }

    public LiveData<List<NoDo>> getAllNoDos() {
        return allNoDos;
    }

    public void insert(NoDo noDo) {
        noDoRepository.insert(noDo);
    }
}
