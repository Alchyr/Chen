package Chen.Actions.ChenActions;

import Chen.Actions.GenericActions.DrawAndSaveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.function.Predicate;

public class ConditionalCardEnergyGain extends AbstractGameAction {
    private DrawAndSaveCardsAction cardSource;
    private Predicate<AbstractCard> condition;

    public ConditionalCardEnergyGain(DrawAndSaveCardsAction drawnCardSource, Predicate<AbstractCard> condition)
    {
        this.actionType = ActionType.SPECIAL;
        cardSource = drawnCardSource;
        this.condition = condition;
        this.source = AbstractDungeon.player;
    }

    @Override
    public void update() {
        int gain = 0;
        for (AbstractCard c : cardSource.getDrawnCards())
        {
            if (condition.test(c))
            {
                gain++;
            }
        }
        if (gain > 0)
        {
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(gain));
        }
        this.isDone = true;
    }
}
