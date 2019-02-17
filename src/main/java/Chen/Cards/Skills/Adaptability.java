package Chen.Cards.Skills;

import Chen.Abstracts.ShiftChenCard;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;
import static Chen.Patches.CardTagsEnum.CHEN_SHIFT_CARD;

public class Adaptability extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Adaptability",
            2,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    public Adaptability()
    {
        super(cardInfo, CardType.ATTACK, CardTarget.ENEMY,false);
    }

    @Override
    public void applyPowers() {
        this.baseBlock = countShiftCards();
        this.baseDamage = this.baseBlock;

        if (this.Form) {
            this.rawDescription = descriptionA + cardStrings.EXTENDED_DESCRIPTION[2];
            this.initializeDescription();
        }
        else {
            this.rawDescription = descriptionA + cardStrings.EXTENDED_DESCRIPTION[3];
            this.initializeDescription();
        }
        super.applyPowers();
    }

    private int countShiftCards()
    {
        int count = 0;

        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c.tags.contains(CHEN_SHIFT_CARD))
                count++;
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            if (c.tags.contains(CHEN_SHIFT_CARD))
                count++;
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            if (c.tags.contains(CHEN_SHIFT_CARD))
                count++;
        }

        return count;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SMASH));
        }
    }
}