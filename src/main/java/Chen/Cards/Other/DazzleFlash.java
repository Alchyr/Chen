package Chen.Cards.Other;

import Chen.Abstracts.ShiftChenCard;
import Chen.Actions.ChenActions.FlashAction;
import Chen.Character.Chen;
import Chen.ChenMod;
import Chen.Effects.DazzleEffect;
import Chen.Interfaces.SpellCard;
import Chen.Powers.Disoriented;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.List;

import static Chen.ChenMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class DazzleFlash extends ShiftChenCard implements SpellCard {
    private final static CardInfo cardInfo = new CardInfo(
        "DazzleFlash",
        1,
        CardType.SKILL,
        CardTarget.ENEMY,
        CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE_A = 0;
    private final static int DAMAGE_B = 4;
    private final static int UPG_DAMAGE_A = 0;
    private final static int UPG_DAMAGE_B = 2;

    private final static int DEBUFF_A = 2;
    private final static int UPG_DEBUFF_A = 1;

    private final static int VULN = 1;

    public DazzleFlash(boolean preview)
    {
        super(cardInfo, CardType.ATTACK, CardTarget.ALL_ENEMY, false, preview);

        setDamage(DAMAGE_A, DAMAGE_B, UPG_DAMAGE_A, UPG_DAMAGE_B);
        setMagic(DEBUFF_A, 0, UPG_DEBUFF_A, 0);
    }
    @Override
    public AbstractCard makeCopy() {
        return new DazzleFlash(true);
    }
    @Override
    protected ShiftChenCard noPreviewCopy() {
        return new DazzleFlash(false);
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();

        if (copy instanceof DazzleFlash)
        {
            copy.magicNumber = this.magicNumber;
        }

        return copy;
    }

    @Override
    public List<String> getCardDescriptors() {
        return SpellCard.spellDescriptor;
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        DazzleFlash returnCard = new DazzleFlash(true);
        returnCard.shift(Chen.ChenHuman);
        return returnCard;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            if (m != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new DazzleEffect(m.hb.cX, m.hb.cY)));
            }

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Disoriented(m, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, VULN, false), VULN));
        }
        else //cat
        {
            AbstractDungeon.actionManager.addToBottom(new FlashAction(new DamageInfo(p, this.baseDamage, this.damageTypeForTurn), this.magicNumber));
        }
    }

    public void applyPowers() {
        if (!Form) //cat form only
        {
            this.baseMagicNumber = this.magicNumber = getHits();
            this.isMagicNumberModified = false;
            this.rawDescription = descriptionB + cardStrings.EXTENDED_DESCRIPTION[2];
            this.initializeDescription();
        }
        else {
            this.magicNumber = this.baseMagicNumber;
            AbstractPower pow = player.getPower(FocusPower.POWER_ID);
            if (pow != null)
                this.magicNumber += pow.amount;

            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        }
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (!Form) //cat form only
        {
            this.baseMagicNumber = this.magicNumber = getHits();
            this.isMagicNumberModified = false;
            this.rawDescription = descriptionB + cardStrings.EXTENDED_DESCRIPTION[2];
            this.initializeDescription();
        }
        else {
            this.magicNumber = this.baseMagicNumber;
            AbstractPower pow = player.getPower(FocusPower.POWER_ID);
            if (pow != null)
                this.magicNumber += pow.amount;

            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        }
        super.calculateCardDamage(mo);
    }

    private int getHits()
    {
        return ChenMod.battleDisorientCount;
    }
}
