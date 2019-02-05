package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Chen.ChenMod.makeID;

public class Honk extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Honk",
            1,
            CardType.SKILL,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int UPG_COST = 0;
    private final static int DEBUFF = 99;
    private final static int ENEMY_BUFF = 1;

    public Honk()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(ENEMY_BUFF);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //add a honk sound
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!mo.isDeadOrEscaped())
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new StrengthPower(mo, this.magicNumber), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new VulnerablePower(mo, DEBUFF, false), DEBUFF));
            }
        }
    }
}