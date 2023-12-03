package com.example.oopmap.Crits;

public class PredatorBrain extends Brain {

    public PredatorBrain(int type) {
        super(type);
    }

    @Override
    protected void zeroSetup() {
        wl = 8;
    }

    @Override
    protected Brain InitBrain() {
        return (new PredatorBrain(type));
    }
}
