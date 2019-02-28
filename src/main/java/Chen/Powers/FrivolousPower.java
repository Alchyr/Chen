package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Actions.GenericActions.ModifyRandomCardCost;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FrivolousPower extends Power {
    public static final String NAME = "Frivolous";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public FrivolousPower(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        for (int i = 0; i < this.amount; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyRandomCardCost(AbstractDungeon.player.hand, -1));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = descriptions[0] + descriptions[1];
        } else {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[2];
        }
    }
}