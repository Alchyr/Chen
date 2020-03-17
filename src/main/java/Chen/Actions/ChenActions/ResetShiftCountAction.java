package Chen.Actions.ChenActions;

import Chen.ChenMod;
import Chen.Patches.TwoFormFields;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ResetShiftCountAction extends AbstractGameAction {

    public ResetShiftCountAction()
    {
        actionType = ActionType.SPECIAL;
        duration = 0.01f;
    }

    @Override
    public void update() {
        TwoFormFields.shiftsThisTurn = 0;
        AbstractDungeon.player.hand.glowCheck();

        this.isDone = true;
    }
}
