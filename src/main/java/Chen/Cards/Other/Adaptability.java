package Chen.Cards.Other;

import Chen.Abstracts.ShiftChenCard;
import Chen.Util.CardInfo;
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

    private final static int BASE_BONUS = 0;
    private final static int UPG_BONUS = 5;

    public Adaptability()
    {
        super(cardInfo, CardType.ATTACK, CardTarget.ENEMY,true);

        setMagic(BASE_BONUS, BASE_BONUS, UPG_BONUS, UPG_BONUS);
    }

    @Override
    public void applyPowers() {
        this.block = this.baseBlock = countShiftCards() + this.baseMagicNumber;
        this.baseDamage = this.baseBlock;

        if (this.Form) {
            this.rawDescription = descriptionA + cardStrings.EXTENDED_DESCRIPTION[3];
            this.initializeDescription();
        }
        else {
            this.rawDescription = descriptionB + cardStrings.EXTENDED_DESCRIPTION[4];
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