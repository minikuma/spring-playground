package me.minikuma.batch.classfier;

import org.springframework.classify.Classifier;

public class ProcessorClassifier<C, T> implements Classifier<C, T> {
    @Override
    public T classify(C classifiable) {
        return null;
    }
}
