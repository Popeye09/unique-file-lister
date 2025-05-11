package hu.smthy.unique_file_lister.mapper;

public interface Mapper<T, F> {
    F mapTo(T t);
    T mapFrom(F f);
}
