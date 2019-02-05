package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Interfaces.OnShiftSubscriber;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class BlockOnShift extends Power implements OnShiftSubscriber {
    public static final String NAME = "Unstable Block";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public BlockOnShift(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, source, amount);
    }

    @Override
    public void OnShiftForm() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, this.amount));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[1];
    }
}
