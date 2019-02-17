package Chen.Cards.Skills;

import Chen.Abstracts.ShiftChenCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Chen.ChenMod.makeID;

public class TauntHiss extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TauntHiss",
            0,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DEBUFF = 1;

    public TauntHiss()
    {
        super(cardInfo, true);
        this.setMagic(DEBUFF, DEBUFF);
    }

    @Override
    public void upgrade()
    {
        this.cardTargetA = CardTarget.ALL;
        this.cardTargetB = CardTarget.ALL;
        this.target = CardTarget.ALL;
        super.upgrade();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower applyPower;
        if (upgraded)
        { //all enemies
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if (Form) //human
                {
                    applyPower = new VulnerablePower(mo, this.magicNumber, false);
                }
                else //cat
                {
                    applyPower = new WeakPower(mo, this.magicNumber, false);
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, applyPower, this.magicNumber));

            }
        }
        else
        { //only target
            if (Form) //human
            {
                applyPower = new VulnerablePower(m, this.magicNumber, false);
            }
            else //cat
            {
                applyPower = new WeakPower(m, this.magicNumber, false);
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, applyPower, this.magicNumber));
        }
    }
}