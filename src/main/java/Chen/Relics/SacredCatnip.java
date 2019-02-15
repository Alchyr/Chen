package Chen.Relics;

import Chen.Abstracts.Relic;
import Chen.ChenMod;
import Chen.Interfaces.OnShiftSubscriber;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Boot;
import com.megacrit.cardcrawl.relics.StrangeSpoon;

public class SacredCatnip extends Relic implements OnShiftSubscriber {
    public static final String ID = ChenMod.makeID("SacredCatnip");

    private boolean canTrigger;

    public SacredCatnip()
    {
        super(ID, "SacredCatnip",
                RelicTier.BOSS, LandingSound.MAGICAL);
        canTrigger = true;
    }

    public void OnShiftForm()
    {
        if (canTrigger)
        {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            this.canTrigger = false;
            this.pulse = false;
            this.flash();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(Catnip.ID);
    }

    public void obtain()
    {
        if (AbstractDungeon.player.hasRelic(Catnip.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(Catnip.ID)) {
                    this.counter = AbstractDungeon.player.getRelic(Catnip.ID).counter;
                    instantObtain(AbstractDungeon.player, i, true);

                    break;
                }
            }
        } else {
            super.obtain();
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