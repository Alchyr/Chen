package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.ChenMod;
import Chen.Interfaces.BetterOnGainBlockSubscriber;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class PreventAllBlockPower extends Power implements BetterOnGainBlockSubscriber {
    public static final String NAME = "No Block";
    public static final String POWER_ID = ChenMod.makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    public PreventAllBlockPower(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public float modifyBlock(float blockAmount) {
        return 0.0F;
    }

    @Override
    public boolean onBetterGainBlock(float[] blockAmount) {
        return false; //no block for you.
    }

    public void atEndOfRound() {
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[1];
        }
        else
        {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[2];
        }
    }
}