// RandomProducer.aidl
package cc.aoeiuv020.sample.aidl.service;

// Declare any non-default types here with import statements

interface IRandomProducerService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     int nextRandom(int min,int max);
}
