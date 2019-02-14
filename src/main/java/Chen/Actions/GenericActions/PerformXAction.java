package Chen.Actions.GenericActions;

import Chen.Abstracts.AbstractXAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class PerformXAction extends AbstractGameAction {
    protected int baseValue = -1;
    protected boolean freeToPlayOnce = false;

    protected AbstractXAction XAction = null;

    private AbstractPlayer p;

    public PerformXAction(AbstractXAction XAction, AbstractPlayer p, int baseValue, boolean freeToPlayOnce)
    {
        this.baseValue = baseValue;
        this.freeToPlayOnce = freeToPlayOnce;
        this.XAction = XAction;
        this.p = p;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.baseValue != -1) {
            effect = this.baseValue;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0)
        {
            XAction.initialize(effect);
            AbstractDungeon.actionManager.addToTop(XAction);
        }

        if (!this.freeToPlayOnce) {
            this.p.energy.use(EnergyPanel.totalCount);
        }
        isDone = true;
    }
}
