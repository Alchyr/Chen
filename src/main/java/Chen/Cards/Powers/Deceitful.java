package Chen.Cards.Powers;

import Chen.Abstracts.BaseCard;
import Chen.Powers.DeceitfulPower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Deceitful extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Deceitful",
            3,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DEBUFF = 1;
    private static final int UPG_COST = 2;

    public Deceitful()
    {
        super(cardInfo, false);

        setMagic(DEBUFF);
        setCostUpgrade(UPG_COST);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DeceitfulPower(p, this.magicNumber), this.magicNumber));
    }
}