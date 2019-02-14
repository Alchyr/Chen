package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Chen.ChenMod.makeID;

public class Recuperate extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Recuperate",
            1,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardTarget.SELF,
            AbstractCard.CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int UPG_COST = 0;

    public Recuperate()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPower power : p.powers)
        {
            if (power.type == AbstractPower.PowerType.DEBUFF)
            {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, power));
            }
        }
    }
}