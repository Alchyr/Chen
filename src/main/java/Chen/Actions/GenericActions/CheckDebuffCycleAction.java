package Chen.Actions.GenericActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CheckDebuffCycleAction extends AbstractGameAction {
    private String powerID;
    private int draw;
    private int energy;

    public CheckDebuffCycleAction(AbstractCreature target, String powerID, int cardDraw, int energyGain) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.powerID = powerID;
        draw = cardDraw;
        energy = energyGain;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(powerID)) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, draw));
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(energy));
        }

        this.isDone = true;
    }
}