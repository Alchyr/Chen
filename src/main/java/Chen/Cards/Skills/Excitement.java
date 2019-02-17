package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.ShiftChenCard;
import Chen.Actions.ChenActions.FlashAction;
import Chen.Cards.Other.DazzleFlash;
import Chen.Character.Chen;
import Chen.Effects.DazzleEffect;
import Chen.Interfaces.SpellCard;
import Chen.Powers.Disoriented;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;
import static Chen.ChenMod.shiftsThisTurn;

public class Excitement extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Excitement",
            0,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    public Excitement()
    {
        super(cardInfo,true);

        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
    }

     @Override
     public void upgrade()
     {
         super.upgrade();
         AlwaysRetainField.alwaysRetain.set(this, true);
     }

    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = this.magicNumber = getEnergyGain();
        if (upgraded)
        {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }
        else
        {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
    }

    private int getEnergyGain()
    {
        return shiftsThisTurn;
    }
}