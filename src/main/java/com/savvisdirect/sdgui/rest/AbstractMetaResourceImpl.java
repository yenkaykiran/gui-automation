package com.savvisdirect.sdgui.rest;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMetaResourceImpl<E extends Enum<E>> {

    private E type;

    public List<String> listTypes() {
        Class<E> clazz = (Class<E>) getType().getClass();
        List<String> types = new ArrayList<String>();
        for (E option : clazz.getEnumConstants()) {
            types.add(option.toString());
        }
        return types;
    }

    public E getType() {
        return type;
    }

    public abstract void setType(E type);
}