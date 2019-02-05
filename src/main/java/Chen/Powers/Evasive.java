package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.ChenMod;
import Chen.Util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Chen.Util.PowerImages.PowerPath;

public class Evasive extends Power {
    public static final String NAME = "Evasive";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public Evasive(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);

        if (card.type == AbstractCard.CardType.ATTACK)
        {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(owner, owner, this.amount, true));
        }
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + "#b" + this.amount + descriptions[1];
    }
}