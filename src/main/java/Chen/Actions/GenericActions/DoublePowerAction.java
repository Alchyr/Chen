package Chen.Actions.GenericActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DoublePowerAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractPower doublePower;

    public DoublePowerAction(AbstractPower toDouble) {
        this.actionType = ActionType.WAIT;
        this.p = AbstractDungeon.player;
        this.doublePower = toDouble;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.p.hasPower(doublePower.ID)) {
            int powAmount = this.p.getPower(doublePower.ID).amount;

            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.p, this.p, doublePower, powAmount));
        }

        this.tickDuration();
    }
}
