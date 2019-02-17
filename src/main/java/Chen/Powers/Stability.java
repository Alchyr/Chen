package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Interfaces.NoLoseBlockPower;
import Chen.Interfaces.OnShiftSubscriber;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Stability extends Power implements OnShiftSubscriber, NoLoseBlockPower {
    public static final String NAME = "Stability";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public Stability(final AbstractCreature owner) {
        super(NAME, TYPE, TURN_BASED, owner, null, 0);
    }

    @Override
    public void OnShiftForm() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public void stackPower(int stackAmount) {
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0];
    }
}