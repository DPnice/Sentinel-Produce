package com.alibaba.csp.sentinel.datasource;

/**
 * @author DPn!ce
 * @date 2019/04/22.
 */
public interface Converter2<S, T> {

    /**
     * Convert {@code source} to the target type.
     *
     * @param source the source object
     * @param parameter parameter
     * @return the target object
     */
    T convert(S source, S... parameter);

}
