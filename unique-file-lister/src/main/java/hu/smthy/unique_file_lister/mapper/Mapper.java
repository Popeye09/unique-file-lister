package hu.smthy.unique_file_lister.mapper;

/**
 * Interface for mapping objects
 * @param <T> Class
 * @param <F> Class
 */
public interface Mapper<T, F> {
    F mapTo(T t);
    T mapFrom(F f);
}
