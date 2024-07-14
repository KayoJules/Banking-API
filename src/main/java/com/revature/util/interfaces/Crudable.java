package com.revature.util.interfaces;

public interface Crudable<O> extends Serviceable<O>{
    boolean update(O updatedObject);
    boolean delete(O removedObject);
}
