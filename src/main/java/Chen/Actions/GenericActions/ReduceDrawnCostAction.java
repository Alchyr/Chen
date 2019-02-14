package Chen.Actions.GenericActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ReduceDrawnCostAction extends AbstractGameAction {
    private DrawAndSaveCardsAction cardSource;
    private int reduction;
    private boolean forCombat;

    public ReduceDrawnCostAction(DrawAndSaveCardsAction drawnCardSource, int reduction)
    {
        this(drawnCardSource, reduction, false);
    }
    public ReduceDrawnCostAction(DrawAndSaveCardsAction drawnCardSource, int reduction, boolean forCombat)
    {
        this.actionType = ActionType.SPECIAL;
        cardSource = drawnCardSource;
        this.reduction = reduction;
        this.forCombat = forCombat;
        this.source = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (reduction > 0)
        {
            for (AbstractCard c : cardSource.getDrawnCards())
            {
                if (forCombat)
                {
                    c.modifyCostForCombat(-reduction);
                }
                else
                {
                    c.modifyCostForTurn(-reduction);
                }
            }
        }
        this.isDone = true;
    }
}