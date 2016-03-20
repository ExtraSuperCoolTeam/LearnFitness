package com.codepath.apps.learnfitness.models;

/**
 * Created by spandhare on 3/20/16.
 */
public class TrainerReply {
    FormMessageReply formMessageReply;
    Trainer trainer;

    public FormMessageReply getFormMessageReply() {
        return formMessageReply;
    }

    public void setFormMessageReply(FormMessageReply formMessageReply) {
        this.formMessageReply = formMessageReply;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}
