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
            2,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DEBUFF = 1;
    private static final int UPG_DEBUFF = 1;

    public Deceitful()
    {
        super(cardInfo, false);

        setMagic(DEBUFF, UPG_DEBUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DeceitfulPower(p, this.magicNumber), this.magicNumber));
    }
}