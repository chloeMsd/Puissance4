package com.projetpuissance4.models;

public enum Options {
    OPTION_1("Jeu contre un autre joueur"),
    OPTION_2("Jeu contre une IA débutante"),
    OPTION_3("Jeu contre une IA intermédiaire"),
    OPTION_4("Jeu contre une IA experte");

    private final String label;

    Options(String option) {
        this.label=option;
    }
    public String getOption()
    {
        return label;
    }
    public Options getOption1() {
        return OPTION_1;
    }
    public Options getOption2() {
        return OPTION_2;
    }
    public Options getOption3() {
        return OPTION_3;
    }
    public Options getOption4() {
        return OPTION_4;
    }

}
