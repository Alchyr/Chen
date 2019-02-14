package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Actions.GenericActions.DrawAndSaveCardsAction;
import Chen.Actions.GenericActions.ReduceDrawnCostAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FollowUpPower extends Power {
    public static final String NAME = "FollowUp";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private int amountThisTurn;

    public FollowUpPower(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
        amountThisTurn = 0;
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        amountThisTurn = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);

        if (card.type == AbstractCard.CardType.ATTACK && this.amountThisTurn < this.amount)
        {
            DrawAndSaveCardsAction drawAction = new DrawAndSaveCardsAction(this.owner, 1);
            AbstractDungeon.actionManager.addToBottom(drawAction);
            AbstractDungeon.actionManager.addToBottom(new ReduceDrawnCostAction(drawAction, 1));
            amountThisTurn++;
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = descriptions[0] + descriptions[1];
        }
        else
        {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[2];
        }
    }
}