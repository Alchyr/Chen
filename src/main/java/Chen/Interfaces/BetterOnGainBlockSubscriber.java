package Chen.Interfaces;

public interface BetterOnGainBlockSubscriber {
    //Return false to disable block gain completely.
    boolean onBetterGainBlock(float[] blockAmount);
}
