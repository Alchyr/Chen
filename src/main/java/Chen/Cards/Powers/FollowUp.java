package Chen.Cards.Powers;

import Chen.Abstracts.BaseCard;
import Chen.Powers.FollowUpPower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class FollowUp extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FollowUp",
            2,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BUFF = 1;
    private static final int UPG_COST = 1;

    public FollowUp()
    {
        super(cardInfo, false);

        setMagic(BUFF);
        setCostUpgrade(UPG_COST);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FollowUpPower(p, this.magicNumber), this.magicNumber));
    }
}