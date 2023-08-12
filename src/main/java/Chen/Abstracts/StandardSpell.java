package Chen.Abstracts;

import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.List;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public abstract class StandardSpell extends BaseCard implements SpellCard {
    private boolean magicNumSpell = false;

    public StandardSpell(CardInfo cardInfo, boolean upgradesDescription)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }

    public StandardSpell(String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription)
    {
        super(cardName, cost, cardType, target, rarity, upgradesDescription);
    }

    @Override
    public List<String> getCardDescriptors() {
        return SpellCard.spellDescriptor;
    }

    public void magicNumSpell() {
        this.magicNumSpell = true;
    }

    @Override
    public void applyPowers() {
        if (magicNumSpell) {
            this.magicNumber = this.baseMagicNumber;
            AbstractPower pow = player.getPower(FocusPower.POWER_ID);
            if (pow != null) {
                this.magicNumber += pow.amount;
                if (this.magicNumber < 0)
                    this.magicNumber = 0;
            }

            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;

            super.applyPowers();
        }
        else {
            int str = 0, dex = 0, origBlock = baseBlock;

            AbstractPower strPow = player.getPower(StrengthPower.POWER_ID);
            AbstractPower dexPow = player.getPower(DexterityPower.POWER_ID);
            AbstractPower focusPow = player.getPower(FocusPower.POWER_ID);

            if (focusPow != null) {
                if (baseBlock >= 0) {
                    baseBlock += focusPow.amount;
                }
            }
            if (strPow != null) {
                str = strPow.amount;
                strPow.amount = 0;
            }
            if (dexPow != null) {
                dex = dexPow.amount;
                dexPow.amount = 0;
            }

            super.applyPowers();

            baseBlock = origBlock;
            if (block != baseBlock)
                isBlockModified = true;

            if (strPow != null) {
                strPow.amount = str;
            }
            if (dexPow != null) {
                dexPow.amount = dex;
            }
        }
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (magicNumSpell) {
            this.magicNumber = this.baseMagicNumber;
            AbstractPower pow = player.getPower(FocusPower.POWER_ID);
            if (pow != null) {
                this.magicNumber += pow.amount;
                if (this.magicNumber < 0)
                    this.magicNumber = 0;
            }

            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;

            super.calculateCardDamage(mo);
        }
        else {
            int str = 0, dex = 0, origBlock = baseBlock;

            AbstractPower strPow = player.getPower(StrengthPower.POWER_ID);
            AbstractPower dexPow = player.getPower(DexterityPower.POWER_ID);
            AbstractPower focusPow = player.getPower(FocusPower.POWER_ID);

            if (focusPow != null) {
                if (baseBlock >= 0) {
                    baseBlock += focusPow.amount;
                }
            }
            if (strPow != null) {
                str = strPow.amount;
                strPow.amount = 0;
            }
            if (dexPow != null) {
                dex = dexPow.amount;
                dexPow.amount = 0;
            }

            super.calculateCardDamage(mo);

            baseBlock = origBlock;
            if (block != baseBlock)
                isBlockModified = true;

            if (strPow != null) {
                strPow.amount = str;
            }
            if (dexPow != null) {
                dexPow.amount = dex;
            }
        }
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int focus = 0;
        if (!magicNumSpell) {
            AbstractPower pow = player.getPower(FocusPower.POWER_ID);
            if (pow != null)
                focus = pow.amount;
        }
        return tmp + focus;
    }
}
