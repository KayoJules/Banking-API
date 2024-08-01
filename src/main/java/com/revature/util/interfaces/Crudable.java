package com.revature.util.interfaces;

import com.revature.User.User;

public interface Crudable<O> extends Serviceable<O>{
    boolean update(O updatedObject);
    boolean delete(O removedObject);
}
