package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Interfaces.ModifyCardPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FinalSpellPower extends Power implements ModifyCardPower {
    public static final String NAME = "FinalSpellCard";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public FinalSpellPower(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse) {
            this.flash();

            for (int i = 0; i < this.amount; i++)
            {
                AbstractMonster m = null;
                if (action.target != null) {
                    m = (AbstractMonster)action.target;
                }

                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float)Settings.HEIGHT / 2.0F;
                tmp.freeToPlayOnce = true;
                if (m != null) {
                    tmp.calculateCardDamage(m);
                }

                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, card.energyOnUse));
            }
        }
    }

    @Override
    public void ModifyCard(AbstractCard c) {
        c.exhaust = true;
        c.isEthereal = true;
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