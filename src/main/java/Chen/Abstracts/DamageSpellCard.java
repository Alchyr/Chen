package Chen.Abstracts;

import Chen.Interfaces.BetterOnDamageGiveSubscriber;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public abstract class DamageSpellCard extends BaseCard implements SpellCard {
    public int spellDamage;
    public boolean isSpellDamageModified;

    public boolean upgradedSpellValue()
    {
        return isSpellDamageModified;
    }

    public DamageSpellCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }

    public DamageSpellCard(String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription)
    {
        super (cardName, cost, cardType, target, rarity, upgradesDescription);
        this.isSpellDamageModified = false;
        this.spellDamage = SpellDamage.staticBaseValue(this);
    }

    public void upgrade()
    {
        super.upgrade();
        this.spellDamage = SpellDamage.staticBaseValue(this);
    }


    @Override
    public void applyPowers() {
        super.applyPowers();
        ApplyDamageSpell();
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        CalculateDamageSpell(mo);
    }

    public void CalculateDamageSpell(AbstractMonster mo)
    {
        AbstractPlayer player = AbstractDungeon.player;

        float baseValue = SpellDamage.staticBaseValue(this);
        isSpellDamageModified = false;

        int targetIndex = -1;
        ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
        float[] tmp = new float[m.size()];
        for (int i = 0; i < tmp.length; i++)
        {
            tmp[i] = baseValue;
        }

        int findIndex = 0;
        for (AbstractMonster monster : m)
        {
            if (monster.equals(mo))
            {
                break;
            }
            findIndex++;
        }
        targetIndex = findIndex;

        if (AbstractDungeon.player.hasRelic("WristBlade") && (this.costForTurn == 0 || this.freeToPlayOnce)) {
            for (int i = 0; i < tmp.length; i++)
            {
                tmp[i] += 3.0F;
            }
        }

        for (AbstractPower p : player.powers) {
            if (p instanceof StrengthPower) {
                continue;
            }
            if (p instanceof FocusPower)
            {
                for (int i = 0; i < tmp.length; i++)
                {
                    tmp[i] += p.amount;
                }
            }

            if (p instanceof BetterOnDamageGiveSubscriber)
            {
                for (int i = 0; i < tmp.length; i++)
                {
                    tmp[i] = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(tmp[i], this.damageTypeForTurn, m.get(i));
                }
            }

            for (int i = 0; i < tmp.length; i++)
            {
                tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn);
            }
        }

        for (int i = 0; i < tmp.length; i++)
        {
            for (AbstractPower p : m.get(i).powers)
            {
                tmp[i] = p.atDamageReceive(tmp[i], this.damageTypeForTurn);
            }
        }

        for (AbstractPower p : player.powers)
        {
            if (!(p instanceof StrengthPower))
            {
                for (int i = 0; i < tmp.length; i++)
                {
                    tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn);
                }
            }
        }

        for (int i = 0; i < tmp.length; i++)
        {
            for (AbstractPower p : m.get(i).powers)
            {
                tmp[i] = p.atDamageFinalReceive(tmp[i], this.damageTypeForTurn);
            }
        }

        for (int i = 0; i < tmp.length; i++)
        {
            if (tmp[i] < 0.0F) {
                tmp[i] = 0.0F;
            }
        }

        this.multiDamage = new int[tmp.length];
        for(int i = 0; i < tmp.length; ++i) {
            this.multiDamage[i] = MathUtils.floor(tmp[i]);
        }

        if (targetIndex < tmp.length)
        {
            this.spellDamage = MathUtils.floor(tmp[targetIndex]);
            if (this.spellDamage != baseValue)
            {
                this.isSpellDamageModified = true;
            }
        }
        else if (this.isMultiDamage && tmp.length > 0)
        {
            int val = MathUtils.floor(tmp[0]);
            boolean allSame = true;

            for (int i = 0; i < tmp.length; i++)
            {
                if (i < m.size() && !m.get(i).isDeadOrEscaped())
                {
                    if (val != MathUtils.floor(tmp[i]))
                    {
                        allSame = false;
                        break;
                    }
                }
            }

            if (allSame)
            {
                this.spellDamage = MathUtils.floor(tmp[0]);
                if (this.spellDamage != baseValue)
                {
                    this.isSpellDamageModified = true;
                }
            }
            else
            {
                ApplyDamageSpellNoMulti();
            }
        }
        else
        {
            ApplyDamageSpell();
        }
    }

    public void ApplyDamageSpell()
    {
        AbstractPlayer player = AbstractDungeon.player;

        float baseValue = SpellDamage.staticBaseValue(this);
        float tmp = baseValue;

        if (AbstractDungeon.player.hasRelic("WristBlade") && (this.costForTurn == 0 || this.freeToPlayOnce)) {
            tmp += 3.0F;
        }

        for (AbstractPower p : player.powers) {
            if (p instanceof StrengthPower) {
                continue;
            }
            if (p instanceof FocusPower)
            {
                tmp += p.amount;
            }

            if (p instanceof BetterOnDamageGiveSubscriber)
            {
                tmp = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(tmp, this.damageTypeForTurn, null);
            }

            tmp = p.atDamageGive(tmp, this.damageTypeForTurn);
        }

        for (AbstractPower p : player.powers)
        {
            if (!(p instanceof StrengthPower))
            {
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn);
            }
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        if (this.isMultiDamage)
        {
            ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            multiDamage = new int[m.size()];
            for (int i = 0; i < multiDamage.length; i++)
            {
                multiDamage[i] = MathUtils.floor(tmp);
            }
        }

        this.spellDamage = MathUtils.floor(tmp);
        if (this.spellDamage != baseValue)
        {
            this.isSpellDamageModified = true;
        }
    }
    public void ApplyDamageSpellNoMulti()
    {
        AbstractPlayer player = AbstractDungeon.player;

        float baseValue = SpellDamage.staticBaseValue(this);
        float tmp = baseValue;

        if (AbstractDungeon.player.hasRelic("WristBlade") && (this.costForTurn == 0 || this.freeToPlayOnce)) {
            tmp += 3.0F;
        }

        for (AbstractPower p : player.powers) {
            if (p instanceof StrengthPower) {
                continue;
            }
            if (p instanceof FocusPower)
            {
                tmp += p.amount;
            }

            if (p instanceof BetterOnDamageGiveSubscriber)
            {
                tmp = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(tmp, this.damageTypeForTurn, null);
            }

            tmp = p.atDamageGive(tmp, this.damageTypeForTurn);
        }

        for (AbstractPower p : player.powers)
        {
            if (!(p instanceof StrengthPower))
            {
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn);
            }
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.spellDamage = MathUtils.floor(tmp);
        if (this.spellDamage != baseValue)
        {
            this.isSpellDamageModified = true;
        }
    }
}
