package Chen.Cards.Powers;

import Chen.Abstracts.ShiftChenCard;
import Chen.Character.Chen;
import Chen.Interfaces.SpellCard;
import Chen.Powers.Evasive;
import Chen.Powers.Rapidfire;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.List;

import static Chen.ChenMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class RapidfireEvasive extends ShiftChenCard implements SpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RapidfireEvasive",
            1,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int MAGIC_A = 4;
    private final static int MAGIC_B = 3;
    private final static int UPG_MAGIC_A = 1;
    private final static int UPG_MAGIC_B = 1;

    public RapidfireEvasive(boolean preview)
    {
        super(cardInfo,false, preview);

        setMagic(MAGIC_A, MAGIC_B, UPG_MAGIC_A, UPG_MAGIC_B);
    }
    @Override
    public AbstractCard makeCopy() {
        return new RapidfireEvasive(true);
    }
    @Override
    protected ShiftChenCard noPreviewCopy() {
        return new RapidfireEvasive(false);
    }

    @Override
    public List<String> getCardDescriptors() {
        return SpellCard.spellDescriptor;
    }

    @Override
    public void applyPowers() {
        this.magicNumber = this.baseMagicNumber;
        if (Form) {
            AbstractPower pow = player.getPower(FocusPower.POWER_ID);
            if (pow != null)
                this.magicNumber += pow.amount;

            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        }
        super.applyPowers();
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.magicNumber = this.baseMagicNumber;
        if (Form) {
            AbstractPower pow = player.getPower(FocusPower.POWER_ID);
            if (pow != null)
                this.magicNumber += pow.amount;

            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        }
        super.calculateCardDamage(mo);
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        RapidfireEvasive returnCard = new RapidfireEvasive(true);
        returnCard.shift(Chen.ChenHuman);
        return returnCard;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Rapidfire(p, this.magicNumber), this.magicNumber));
        }
        else //cat
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Evasive(p, this.magicNumber), this.magicNumber));
        }
    }
}