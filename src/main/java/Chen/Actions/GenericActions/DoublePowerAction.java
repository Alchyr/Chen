package Chen.Actions.GenericActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DoublePowerAction extends AbstractGameAction {
    private AbstractCreature owner;
    private AbstractPower doublePower;

    public DoublePowerAction(AbstractCreature owner, AbstractPower toDouble) {
        this.actionType = ActionType.WAIT;
        this.owner = owner;
        this.doublePower = toDouble;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.owner.hasPower(doublePower.ID)) {
            int powAmount = this.owner.getPower(doublePower.ID).amount;

            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, doublePower, powAmount));
        }

        this.tickDuration();
    }
}
