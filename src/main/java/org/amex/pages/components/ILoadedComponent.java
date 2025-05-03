package org.amex.pages.components;

public interface ILoadedComponent<T> {

    public boolean isLoaded();

    public default T get(){
        if(isLoaded()){
            return (T) this;
        } else {
            throw new RuntimeException(String.format("%s component not loaded", this.getClass().getCanonicalName()));
        }
    }
}
