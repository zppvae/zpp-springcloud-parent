package org.zpp.gateway.util;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;

public class DataBufferWrapper {
    private byte[] data;
    private DataBufferFactory factory;

    public DataBufferWrapper() {
    }

    public DataBufferWrapper(byte[] data, DataBufferFactory factory) {
        this.data = data;
        this.factory = factory;
    }

    public byte[] getData() {
        return data;
    }

    public DataBufferFactory getFactory() {
        return factory;
    }

    public DataBuffer newDataBuffer() {
        if (factory == null)
            return null;

        return factory.wrap(data);
    }

    public Boolean clear() {
        data = null;
        factory = null;

        return Boolean.TRUE;
    }
}