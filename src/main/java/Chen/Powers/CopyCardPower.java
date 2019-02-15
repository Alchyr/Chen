package Chen.Powers;

import Chen.Abstracts.Power;
import com.megacrit.cardcrawl.actions.common.MakeTempCardAtBottomOfDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CopyCardPower extends Power {
    public static final String NAME = "Copy";
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    private int upToDateAmount;

    public CopyCardPower(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
        upToDateAmount = amount;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        upToDateAmount = amount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        upToDateAmount = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);

        if (card != null && this.upToDateAmount > 0)
        {
            this.upToDateAmount--; //In case of cards playing other cards and adding to top, which will cause them to trigger before the reducepoweraction

            AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(card.makeStatEquivalentCopy(), 1, false, true, false));

            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
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