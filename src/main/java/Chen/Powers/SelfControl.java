package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.ChenMod;
import Chen.Interfaces.NoShapeshiftPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SelfControl extends Power implements NoShapeshiftPower {
    public static final String NAME = "Self Control";
    public static final String POWER_ID = ChenMod.makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public SelfControl(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onCancelShapeshift() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = descriptions[0] + descriptions[1];
        }
        else
        {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[2];
        }
    }
}
