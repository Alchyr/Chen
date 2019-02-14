package Chen.Actions.ChenActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WhateverAction extends AbstractGameAction {
    private static final float DUR = 0.5F;

    private boolean upgraded;
    private int ExhaustedCards;

    public WhateverAction(AbstractCreature source, boolean upgraded, int reduction)
    {
        this.upgraded = upgraded;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.source = source;
        this.duration = DUR;
        this.amount = reduction;
        ExhaustedCards = 0;
    }


    @Override
    public void update() {
        if (this.duration == DUR)
        {
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                ExhaustedCards++;
            }
            tickDuration();
        }
        else {
            for (int i = 0; i < ExhaustedCards; i++)
            {
                AbstractCard toAdd = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
                if (toAdd != null)
                {
                    if (upgraded)
                    {
                        toAdd.upgrade();
                    }
                    toAdd.modifyCostForCombat(-this.amount);

                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(toAdd));
                }
            }
            isDone = true;
        }
    }
}
