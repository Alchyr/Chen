package Chen.Relics;

import Chen.Abstracts.Relic;
import Chen.ChenMod;
import Chen.Interfaces.OnShiftSubscriber;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Catnip extends Relic implements OnShiftSubscriber {
    public static final String ID = ChenMod.makeID("Catnip");

    private boolean canTrigger;

    public Catnip()
    {
        super(ID, "Catnip",
                RelicTier.STARTER, LandingSound.FLAT);
        canTrigger = true;
    }

    public void OnShiftForm()
    {
        if (canTrigger)
        {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            this.canTrigger = false;
            this.pulse = false;
            this.flash();
        }
    }

    @Override
    public void atTurnStart() {
        this.canTrigger = true;
        this.pulse = true;
        this.beginPulse();
    }

    public void onVictory() {
        this.pulse = false;
    }
}
