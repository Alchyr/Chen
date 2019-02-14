package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Actions.GenericActions.PlayCardAction;
import Chen.ChenMod;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShikigamiFormBase extends Power {
    public static final String NAME = "ShikigamiBase";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;


    public ShikigamiFormBase(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        for (int i = 0; i < this.amount; i++)
        {
            AbstractCard toPlay = ChenMod.returnTrulyRandomSpellInCombat();

            toPlay.applyPowers();

            AbstractDungeon.actionManager.addToBottom(new PlayCardAction(toPlay, null, true));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[1];
        }
        else
        {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[2];
        }
    }
}