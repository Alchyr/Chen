package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Actions.GenericActions.EndTurnNowAction;
import Chen.Interfaces.OnShiftSubscriber;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class Unbalanced extends Power implements OnShiftSubscriber {
    public static final String NAME = "Unbalanced";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public Unbalanced(final AbstractCreature owner) {
        super(NAME, TYPE, TURN_BASED, owner, null, 0);
    }

    @Override
    public void OnShiftForm() {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID)); //ensure it gets removed before reapplication if you play two in a row
        AbstractDungeon.actionManager.addToBottom(new EndTurnNowAction());
    }

    @Override
    public void stackPower(int stackAmount) {
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0];
    }
}